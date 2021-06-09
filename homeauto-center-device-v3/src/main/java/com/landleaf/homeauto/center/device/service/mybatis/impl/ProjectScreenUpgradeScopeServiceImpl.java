package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.asyn.IFutureService;
import com.landleaf.homeauto.center.device.enums.ScreenUpgradeStatusEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgrade;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeDetail;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeScope;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeSaveDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeUpdateDTO;
import com.landleaf.homeauto.center.device.model.mapper.ProjectScreenUpgradeScopeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectScreenUpgradeDetailService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectScreenUpgradeScopeService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-07
 */
@Service
public class ProjectScreenUpgradeScopeServiceImpl extends ServiceImpl<ProjectScreenUpgradeScopeMapper, ProjectScreenUpgradeScope> implements IProjectScreenUpgradeScopeService {

    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IProjectScreenUpgradeDetailService projectScreenUpgradeDetailService;
    @Autowired
    private IFutureService futureService;

    @Override
    public void saveUpgradeScope(ProjectScreenUpgrade saveData, ProjectScreenUpgradeSaveDTO requestBody) {
        List<String> paths = requestBody.getPaths();
        List<ProjectScreenUpgradeScope> saveScopeDatas = paths.stream().map(i -> {
            ProjectScreenUpgradeScope saveScopeData = new ProjectScreenUpgradeScope();
            saveScopeData.setProjectUpgradeId(saveData.getId());
            saveScopeData.setPath(i);
            return saveScopeData;
        }).collect(Collectors.toList());
        saveBatch(saveScopeDatas);

        // 推送
        List<Long> familyIds = Lists.newArrayList();
        /*******************************获取基础数据**************************************************/
        //根据path获取工程ID
        if (!CollectionUtils.isEmpty(paths)) {
            List<Long> tmpFamilyIds = familyService.getListIdByPathsAndType(paths, 1);
            if (!CollectionUtils.isEmpty(tmpFamilyIds)) {
                familyIds.addAll(tmpFamilyIds);
            }
        }
        if (CollectionUtils.isEmpty(familyIds)) {
            throw new BusinessException(ErrorCodeEnumConst.UPGRADE_VERSION_PROJECT_NOT_FOUND_ERROR);
        }
        /**********************************************组装保存对象******************************/
        List<ProjectScreenUpgradeDetail> details = Lists.newArrayList();
        familyIds.stream().distinct();
        // 获取家庭所属项目楼盘

        for (Long familyId : familyIds) {
            ProjectScreenUpgradeDetail detail = new ProjectScreenUpgradeDetail();
            detail.setFamilyId(familyId);
            detail.setProjectUpgradeId(saveData.getId());
            detail.setVersionCode(saveData.getVersionCode());
            detail.setStatus(ScreenUpgradeStatusEnum.UN_SUCCESS.getType());
            detail.setProjectId(saveData.getProjectId());
            detail.setRealestateId(saveData.getRealestateId());
            detail.setPushTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
            details.add(detail);
        }
        if (!CollectionUtils.isEmpty(details)) {
            /******************************保存更新详情记录**********************************************/
            projectScreenUpgradeDetailService.saveBatch(details);
            /******************************异步发送通知推送到网关**********************************************/
            futureService.notifyUpgrade(saveData.getUrl(), details);
        }


    }

    @Override
    public void updateUpgradeScope(ProjectScreenUpgrade screenUpgrade, ProjectScreenUpgradeUpdateDTO requestBody){
        List<HomeAutoFamilyDO> projectFamily = familyService.getFamilyByProject(screenUpgrade.getProjectId());
        List<ProjectScreenUpgradeScope> existScopes = getByUpgradeId(requestBody.getId());
        // 检验修改后的家庭数只能大于修改前的家庭数
        checkFamily(projectFamily, existScopes, requestBody.getPaths());
        // 移除旧的
        removeByUpgradeId(requestBody.getId());
        // 新增范围
        saveBatch(requestBody.getPaths().stream().map(i->{
            ProjectScreenUpgradeScope saveScopeData = new ProjectScreenUpgradeScope();
            saveScopeData.setProjectUpgradeId(requestBody.getId());
            saveScopeData.setPath(i);
            return saveScopeData;
        }).collect(Collectors.toList()));
        // 详情删除家庭中已经移除的,新增本次有的
        projectScreenUpgradeDetailService.updateDetails4UpdateScope(screenUpgrade,projectFamily,existScopes,requestBody.getPaths());
    }

    public void removeByUpgradeId(Long upgradeId) {
        UpdateWrapper<ProjectScreenUpgradeScope> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("project_upgrade_id", upgradeId);
        remove(updateWrapper);
    }



    public List<ProjectScreenUpgradeScope> getByUpgradeId(Long upgradeId) {
        QueryWrapper<ProjectScreenUpgradeScope> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_upgrade_id", upgradeId);
        return list(queryWrapper);

    }

    /**
     * 修改时校验修改后family是否范围大于前family
     *
     * @param projectFamily
     * @param existScopes
     * @param updatePaths
     */
    private void checkFamily(List<HomeAutoFamilyDO> projectFamily, List<ProjectScreenUpgradeScope> existScopes, List<String> updatePaths) {
        if (CollectionUtils.isEmpty(projectFamily)) {
            return;
        }
        List<String> existPaths = null;
        if (!CollectionUtils.isEmpty(existScopes)) {
            existPaths = existScopes.stream().map(i -> i.getPath()).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(existPaths)) {
            return;
        }
        List<String> finalExistPaths = existPaths;
        List<Long> existFamilyIds = projectFamily.stream().filter(i -> {
            if(StringUtils.isEmpty(i.getPath1())){
                return false;
            }
            Optional<String> any = finalExistPaths.stream().filter(p -> i.getPath1().contains(p)).findAny();
            if (any.isPresent()) {
                return true;
            }
            return false;
        }).map(i -> i.getId()).collect(Collectors.toList());
        List<Long> updateFamilyIds = projectFamily.stream().filter(i -> {
            if(StringUtils.isEmpty(i.getPath1())){
                return false;
            }
            Optional<String> any = updatePaths.stream().filter(p -> i.getPath1().contains(p)).findAny();
            if (any.isPresent()) {
                return true;
            }
            return false;
        }).map(i -> i.getId()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(existFamilyIds)) {
            return;
        }
        if (CollectionUtils.isEmpty(updateFamilyIds)) {
            throw new BusinessException(ErrorCodeEnumConst.UPGRADE_UPDATE_FAMILY_ONLY_ADD_ERROR);
        }
        boolean present = existFamilyIds.stream().filter(i -> !updateFamilyIds.contains(i)).findAny().isPresent();
        if (present) {
            throw new BusinessException(ErrorCodeEnumConst.UPGRADE_UPDATE_FAMILY_ONLY_ADD_ERROR);
        }

    }
}
