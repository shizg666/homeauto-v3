package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.asyn.IFutureService;
import com.landleaf.homeauto.center.device.enums.ScreenUpgradeStatusEnum;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgrade;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeDetail;
import com.landleaf.homeauto.center.device.model.dto.screenapk.*;
import com.landleaf.homeauto.center.device.model.mapper.ProjectScreenUpgradeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpApkVersionCheckResponseDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.enums.screen.UpgradeTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class ProjectScreenUpgradeServiceImpl extends ServiceImpl<ProjectScreenUpgradeMapper, ProjectScreenUpgrade> implements IProjectScreenUpgradeService {

    @Autowired
    private IProjectScreenUpgradeScopeService projectScreenUpgradeScopeService;
    @Autowired
    private IProjectScreenUpgradeDetailService projectScreenUpgradeDetailService;
    @Autowired
    private IHomeAutoProjectService projectService;
    @Autowired
    private IHomeAutoRealestateService realestateService;
    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IFutureService futureService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUpgrade(ProjectScreenUpgradeSaveDTO requestBody) {
        List<String> paths = requestBody.getPaths();
        requestBody.setPaths(rebuildPaths(requestBody.getRealestateId(),requestBody.getProjectId(),paths));
        checkVersion(requestBody.getVersionCode(), requestBody.getProjectId(), null);
        ProjectScreenUpgrade saveData = new ProjectScreenUpgrade();
        BeanUtils.copyProperties(requestBody, saveData);
        saveData.setUploadTime(LocalDateTime.now());
        save(saveData);
        // 附带保存推送范围
        projectScreenUpgradeScopeService.saveUpgradeScope(saveData, requestBody);

    }

    @Override
    public void updateUpgrade(ProjectScreenUpgradeUpdateDTO requestBody) {

        Long id = requestBody.getId();
        ProjectScreenUpgrade screenUpgrade = getById(id);
        if (screenUpgrade == null) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_DATA_EXIST);
        }
        List<String> paths = requestBody.getPaths();
        requestBody.setPaths(rebuildPaths(screenUpgrade.getRealestateId(),screenUpgrade.getProjectId(),paths));
        // 校验家庭数只能增不能减
        projectScreenUpgradeScopeService.updateUpgradeScope(screenUpgrade, requestBody);
    }

    @Override
    public BasePageVO<ProjectScreenUpgradeInfoDetailDTO> getInfoDetail(ProjectScreenUpgradeDetailPageDTO requestDTO) {
        requestDTO.setPaths(rebuildPaths(requestDTO.getRealestateId(),requestDTO.getProjectId(),requestDTO.getPaths()));
       return projectScreenUpgradeDetailService.pageByCondition(requestDTO);
    }

    @Override
    public BasePageVO<ProjectScreenUpgradeInfoDTO> pageList(ProjectScreenUpgradePageDTO requestBody) {

        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        List<ProjectScreenUpgradeInfoDTO> data = Lists.newArrayList();
        QueryWrapper<ProjectScreenUpgrade> queryWrapper = new QueryWrapper<>();
        Long realestateId = requestBody.getRealestateId();
        Long projectId = requestBody.getProjectId();

        if(projectId!=null){
            queryWrapper.eq("project_id",projectId);
        }
        if(realestateId!=null){
            queryWrapper.eq("realestate_id",realestateId);
        }
        List<String> uploadTimeRange = requestBody.getVersionTime();
        String startTime = null;
        String endTime = null;
        if (!CollectionUtils.isEmpty(uploadTimeRange) && uploadTimeRange.size() == 2) {
            startTime = uploadTimeRange.get(0);
            endTime = uploadTimeRange.get(1);
            queryWrapper.apply("upload_time>= TO_TIMESTAMP('" + startTime + "','yyyy-mm-dd hh24:mi:ss')");
            queryWrapper.apply("upload_time<= TO_TIMESTAMP('" + endTime + "','yyyy-mm-dd hh24:mi:ss')");
        }
        queryWrapper.orderByDesc("upload_time");
        List<ProjectScreenUpgrade> queryResult = list(queryWrapper);
        if (!CollectionUtils.isEmpty(queryResult)) {
            data.addAll(queryResult.stream().map(i -> {
              return   convert2Dto(i);
            }).collect(Collectors.toList()));
        }
        PageInfo pageInfo = new PageInfo(data);
        BasePageVO<ProjectScreenUpgradeInfoDTO> result = new BasePageVO<>();
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Override
    public void upgrade(Long detailId) {
        ProjectScreenUpgradeDetail detail = projectScreenUpgradeDetailService.getById(detailId);
        ProjectScreenUpgrade screenUpgrade = getById(detail.getProjectUpgradeId());
        futureService.notifyUpgrade(screenUpgrade.getUrl(), Arrays.asList(detail));
    }

    @Override
    public ScreenHttpApkVersionCheckResponseDTO apkVersionCheck(AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO) {

        String version = adapterHttpApkVersionCheckDTO.getVersion();
        Long familyId = adapterHttpApkVersionCheckDTO.getFamilyId();
        if (org.apache.commons.lang3.StringUtils.isEmpty(version) || Objects.isNull(familyId)) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR);
        }

        ScreenHttpApkVersionCheckResponseDTO result = new ScreenHttpApkVersionCheckResponseDTO();
        result.setVersion(version);
        result.setUpdateFlag(false);
        result.setUpgradeType(null);
        ProjectScreenUpgradeDetail current = projectScreenUpgradeDetailService.getFamilyCurrentVersion(familyId);
        if (current == null) {
            return result;
        }
        ProjectScreenUpgrade screenUpgrade = getById(current.getProjectUpgradeId());
        if(screenUpgrade==null){
            return result;
        }

        if (!org.apache.commons.lang3.StringUtils.equals(version, current.getVersionCode())) {
            result.setUpdateFlag(true);
            result.setVersion(current.getVersionCode());
            result.setUrl(screenUpgrade.getUrl());
            result.setUpgradeType(screenUpgrade.getUpgradeType());
            return result;
        }
        //更新为成功
        projectScreenUpgradeDetailService.updateResponseSuccess(current.getId());
        return result;
    }

    private ProjectScreenUpgradeInfoDTO convert2Dto(ProjectScreenUpgrade screenUpgrade) {
        ProjectScreenUpgradeInfoDTO result = new ProjectScreenUpgradeInfoDTO();
        BeanUtils.copyProperties(screenUpgrade,result);
        List<HomeAutoProject> projects = projectService.list();
        List<HomeAutoRealestate> realestates = realestateService.list();
        Map<Long, HomeAutoProject> projectMap = projects.stream().collect(Collectors.toMap(BaseEntity2::getId, i -> i));
        Map<Long, HomeAutoRealestate> realestateMap = realestates.stream().collect(Collectors.toMap(BaseEntity2::getId, i -> i));
        result.setProjectName(projectMap.get(result.getProjectId()).getName());
        result.setRealestateName(realestateMap.get(result.getRealestateId()).getName());
        result.setUpgradeTypeName(UpgradeTypeEnum.getStatusByType(result.getUpgradeType()).getName());
        result.setPaths(projectScreenUpgradeScopeService.getByUpgradeId(screenUpgrade.getId()).stream().map(i->i.getPath()).collect(Collectors.toList()));
        Integer projectFamilyCount=familyService.countByProject(screenUpgrade.getProjectId());
        Integer successCount=projectScreenUpgradeDetailService.countByUpgradeIdAndStatus(screenUpgrade.getId(), ScreenUpgradeStatusEnum.SUCCESS.getType());
        result.setNoUpgradeCount(projectFamilyCount-successCount);
        result.setUpgradeCount(successCount);
        return result;
    }

    /**
     * 校验版本号是否唯一
     *
     * @param versionCode
     * @param projectId
     * @param upgradeId
     */
    private void checkVersion(String versionCode, Long projectId, Long upgradeId) {
        QueryWrapper<ProjectScreenUpgrade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version_code", versionCode);
        queryWrapper.eq("project_id", projectId);
        if (upgradeId != null) {
            queryWrapper.ne("id", upgradeId);
        }
        int count = count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCodeEnumConst.UPGRADE_VERSION_EXIST_ERROR);
        }
    }

    List<String> rebuildPaths(Long realestateId,Long projectId,List<String> originPaths){
        if(!org.springframework.util.CollectionUtils.isEmpty(originPaths)){
            if(!Objects.isNull(projectId)&&!Objects.isNull(realestateId)){
                throw new BusinessException(ErrorCodeEnumConst.UPGRADE_DETAIL_CONDITION_PROJECT_REQUIRE_ERROR);
            }
            return originPaths.stream().map(i -> String.valueOf(realestateId).concat("/").concat(String.valueOf(projectId).concat("/").concat(i))).collect(Collectors.toList());
        }
        return null;
    }

}
