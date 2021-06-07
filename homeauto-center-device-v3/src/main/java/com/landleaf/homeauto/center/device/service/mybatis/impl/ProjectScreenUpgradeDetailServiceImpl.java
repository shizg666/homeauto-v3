package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.ScreenApkUpdateStatusEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgrade;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeDetail;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeScope;
import com.landleaf.homeauto.center.device.model.mapper.ProjectScreenUpgradeDetailMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectScreenUpgradeDetailService;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-07
 */
@Service
public class ProjectScreenUpgradeDetailServiceImpl extends ServiceImpl<ProjectScreenUpgradeDetailMapper, ProjectScreenUpgradeDetail>
        implements IProjectScreenUpgradeDetailService {

    @Override
    public List<ProjectScreenUpgradeDetail> listRecordsByUpgradeId(Long upgradeId) {
        QueryWrapper<ProjectScreenUpgradeDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_upgrade_id",upgradeId);
        return list(queryWrapper);
    }

    @Override
    public void updateDetails4UpdateScope(ProjectScreenUpgrade screenUpgrade, List<HomeAutoFamilyDO> projectFamily, List<ProjectScreenUpgradeScope> existScopes, List<String> updatePaths) {
        List<Long> deleteFamilyIds = Lists.newArrayList();
        List<Long> saveFamilyIds = Lists.newArrayList();
        List<Long> existFamilyIds =null;
        List<Long> updateFamilyIds=null;
        Long upgradeId = screenUpgrade.getId();
        if (!CollectionUtils.isEmpty(projectFamily)) {
            List<ProjectScreenUpgradeDetail> details = listRecordsByUpgradeId(screenUpgrade.getId());
            if(!CollectionUtils.isEmpty(details)){
                existFamilyIds= details.stream().map(i ->i.getFamilyId()).collect(Collectors.toList());
            }
            if(!CollectionUtils.isEmpty(updatePaths)){
                updateFamilyIds= projectFamily.stream().filter(i -> {
                    Optional<String> any = updatePaths.stream().filter(p -> i.getPath1().contains(p)).findAny();
                    if (any.isPresent()) {
                        return true;
                    }
                    return false;
                }).map(i -> i.getId()).collect(Collectors.toList());
            }
        }
        if(!CollectionUtils.isEmpty(updateFamilyIds)&&!CollectionUtils.isEmpty(existFamilyIds)){
            List<Long> finalUpdateFamilyIds = updateFamilyIds;
            deleteFamilyIds=existFamilyIds.stream().filter(i->!finalUpdateFamilyIds.contains(i)).collect(Collectors.toList());
            List<Long> finalExistFamilyIds = existFamilyIds;
            saveFamilyIds = updateFamilyIds.stream().filter(i -> !finalExistFamilyIds.contains(i)).collect(Collectors.toList());
        }else if(CollectionUtils.isEmpty(updateFamilyIds)&&CollectionUtils.isEmpty(existFamilyIds)){
            return;
        }else if(CollectionUtils.isEmpty(existFamilyIds)){
            saveFamilyIds.addAll(updateFamilyIds);
        }else {
            deleteFamilyIds.addAll(existFamilyIds);
        }
        if(!CollectionUtils.isEmpty(deleteFamilyIds)){
            removeByUpgradeIdAndFamilyIds(upgradeId,deleteFamilyIds);
        }
        if(!CollectionUtils.isEmpty(saveFamilyIds)){
            saveBatch(saveFamilyIds.stream().map(familyId->{
                ProjectScreenUpgradeDetail detail = new ProjectScreenUpgradeDetail();
                detail.setFamilyId(familyId);
                detail.setProjectUpgradeId(upgradeId);
                detail.setVersionCode(screenUpgrade.getVersionCode());
                detail.setStatus(ScreenApkUpdateStatusEnum.UPDATING.getType());
                detail.setProjectId(screenUpgrade.getProjectId());
                detail.setRealestateId(screenUpgrade.getRealestateId());
                detail.setPushTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
                return detail;
            }).collect(Collectors.toList()));
        }
    }

    private void removeByUpgradeIdAndFamilyIds(Long upgradeId, List<Long> deleteFamilyIds) {
        UpdateWrapper<ProjectScreenUpgradeDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("project_upgrade_id",upgradeId);
        if(!CollectionUtils.isEmpty(deleteFamilyIds)){
            updateWrapper.in("family_id",deleteFamilyIds);
        }
        remove(updateWrapper);

    }

    private void removeByUpgradeId(Long upgradeId) {
        UpdateWrapper<ProjectScreenUpgradeDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("project_upgrade_id",upgradeId);
        remove(updateWrapper);


    }
}
