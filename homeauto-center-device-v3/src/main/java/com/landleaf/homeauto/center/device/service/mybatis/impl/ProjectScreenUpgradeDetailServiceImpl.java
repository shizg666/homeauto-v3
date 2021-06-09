package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.asyn.IFutureService;
import com.landleaf.homeauto.center.device.enums.ScreenUpgradeStatusEnum;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgrade;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeDetail;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeScope;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeDetailPageDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeInfoDetailDTO;
import com.landleaf.homeauto.center.device.model.mapper.ProjectScreenUpgradeDetailMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectScreenUpgradeDetailService;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
public class ProjectScreenUpgradeDetailServiceImpl extends ServiceImpl<ProjectScreenUpgradeDetailMapper, ProjectScreenUpgradeDetail>
        implements IProjectScreenUpgradeDetailService {
    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    @Lazy
    private IFutureService futureService;
    @Override
    public List<ProjectScreenUpgradeDetail> listRecordsByUpgradeId(Long upgradeId) {
        QueryWrapper<ProjectScreenUpgradeDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_upgrade_id", upgradeId);
        return list(queryWrapper);
    }

    @Override
    public void updateDetails4UpdateScope(ProjectScreenUpgrade screenUpgrade, List<HomeAutoFamilyDO> projectFamily, List<ProjectScreenUpgradeScope> existScopes, List<String> updatePaths) {
        List<Long> deleteFamilyIds = Lists.newArrayList();
        List<Long> saveFamilyIds = Lists.newArrayList();
        List<Long> existFamilyIds = null;
        List<Long> updateFamilyIds = null;
        Long upgradeId = screenUpgrade.getId();
        if (!CollectionUtils.isEmpty(projectFamily)) {
            List<ProjectScreenUpgradeDetail> details = listRecordsByUpgradeId(screenUpgrade.getId());
            if (!CollectionUtils.isEmpty(details)) {
                existFamilyIds = details.stream().map(i -> i.getFamilyId()).collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(updatePaths)) {
                updateFamilyIds = projectFamily.stream().filter(i -> {
                    if(StringUtils.isEmpty(i.getPath1())){
                        return false;
                    }
                    Optional<String> any = updatePaths.stream().filter(p -> i.getPath1().contains(p)).findAny();
                    if (any.isPresent()) {
                        return true;
                    }
                    return false;
                }).map(i -> i.getId()).collect(Collectors.toList());
            }
        }
        if (!CollectionUtils.isEmpty(updateFamilyIds) && !CollectionUtils.isEmpty(existFamilyIds)) {
            List<Long> finalUpdateFamilyIds = updateFamilyIds;
            deleteFamilyIds = existFamilyIds.stream().filter(i -> !finalUpdateFamilyIds.contains(i)).collect(Collectors.toList());
            List<Long> finalExistFamilyIds = existFamilyIds;
            saveFamilyIds = updateFamilyIds.stream().filter(i -> !finalExistFamilyIds.contains(i)).collect(Collectors.toList());
        } else if (CollectionUtils.isEmpty(updateFamilyIds) && CollectionUtils.isEmpty(existFamilyIds)) {
            return;
        } else if (CollectionUtils.isEmpty(existFamilyIds)) {
            saveFamilyIds.addAll(updateFamilyIds);
        } else {
            deleteFamilyIds.addAll(existFamilyIds);
        }
        if (!CollectionUtils.isEmpty(deleteFamilyIds)) {
            removeByUpgradeIdAndFamilyIds(upgradeId, deleteFamilyIds);
        }
        if (!CollectionUtils.isEmpty(saveFamilyIds)) {
            List<ProjectScreenUpgradeDetail> detailList = saveFamilyIds.stream().map(familyId -> {
                ProjectScreenUpgradeDetail detail = new ProjectScreenUpgradeDetail();
                detail.setFamilyId(familyId);
                detail.setProjectUpgradeId(upgradeId);
                detail.setVersionCode(screenUpgrade.getVersionCode());
                detail.setStatus(ScreenUpgradeStatusEnum.UN_SUCCESS.getType());
                detail.setProjectId(screenUpgrade.getProjectId());
                detail.setRealestateId(screenUpgrade.getRealestateId());
                detail.setPushTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
                return detail;
            }).collect(Collectors.toList());
            saveBatch(detailList);
            /******************************异步发送通知推送到网关**********************************************/
            futureService.notifyUpgrade(screenUpgrade.getUrl(), detailList);
        }
    }

    @Override
    public Integer countByUpgradeIdAndStatus(Long upgradeId, Integer type) {

        QueryWrapper<ProjectScreenUpgradeDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_upgrade_id", upgradeId);
        queryWrapper.eq("status", type);
        return count(queryWrapper);
    }

    @Override
    public BasePageVO<ProjectScreenUpgradeInfoDetailDTO> pageByCondition(ProjectScreenUpgradeDetailPageDTO requestDTO) {
        Long upgradeId = requestDTO.getUpgradeId();
        Long familyId = requestDTO.getFamilyId();
        Integer status = requestDTO.getStatus();
        PageHelper.startPage(requestDTO.getPageNum(), requestDTO.getPageSize(), true);
        List<ProjectScreenUpgradeInfoDetailDTO> data = Lists.newArrayList();
        QueryWrapper<ProjectScreenUpgradeDetail> queryWrapper = new QueryWrapper<>();

        if (upgradeId != null) {
            queryWrapper.eq("project_upgrade_id", upgradeId);
        }
        if (familyId != null) {
            queryWrapper.eq("family_id", familyId);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("push_time");
        List<ProjectScreenUpgradeDetail> queryResult = list(queryWrapper);
        if (!com.alibaba.nacos.common.utils.CollectionUtils.isEmpty(queryResult)) {
            data.addAll(queryResult.stream().map(i -> {
                return convert2Dto(i);
            }).collect(Collectors.toList()));
        }
        PageInfo pageInfo = new PageInfo(data);
        BasePageVO<ProjectScreenUpgradeInfoDetailDTO> result = new BasePageVO<>();
        BeanUtils.copyProperties(pageInfo, result);
        return result;

    }

    @Override
    public ProjectScreenUpgradeDetail getFamilyCurrentVersion(Long familyId) {
        QueryWrapper<ProjectScreenUpgradeDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id",familyId);
        queryWrapper.orderByDesc("push_time");
        queryWrapper.last("limit 1 offset 0");
        return getOne(queryWrapper);
    }

    @Override
    public void updateResponseSuccess(Long id) {
        ProjectScreenUpgradeDetail updateData = new ProjectScreenUpgradeDetail();
        updateData.setId(id);
        updateData.setResTime(LocalDateTimeUtil.date2LocalDateTime(new Date()));
        updateData.setStatus(ScreenUpgradeStatusEnum.SUCCESS.getType());
        updateData.setResMsg("成功");
        updateById(updateData);
    }

    private ProjectScreenUpgradeInfoDetailDTO convert2Dto(ProjectScreenUpgradeDetail detail) {
        ProjectScreenUpgradeInfoDetailDTO result = new ProjectScreenUpgradeInfoDetailDTO();
        BeanUtils.copyProperties(detail, result);
        HomeAutoFamilyDO familyDO = familyService.getById(detail.getFamilyId());
        result.setBuildingCode(familyDO.getBuildingCode());
        result.setUnitCode(familyDO.getUnitCode());
        result.setDoorplate(familyDO.getDoorplate());
        result.setDetailId(detail.getId());
        return result;
    }

    private void removeByUpgradeIdAndFamilyIds(Long upgradeId, List<Long> deleteFamilyIds) {
        UpdateWrapper<ProjectScreenUpgradeDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("project_upgrade_id", upgradeId);
        if (!CollectionUtils.isEmpty(deleteFamilyIds)) {
            updateWrapper.in("family_id", deleteFamilyIds);
        }
        remove(updateWrapper);

    }

    private void removeByUpgradeId(Long upgradeId) {
        UpdateWrapper<ProjectScreenUpgradeDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("project_upgrade_id", upgradeId);
        remove(updateWrapper);


    }
}
