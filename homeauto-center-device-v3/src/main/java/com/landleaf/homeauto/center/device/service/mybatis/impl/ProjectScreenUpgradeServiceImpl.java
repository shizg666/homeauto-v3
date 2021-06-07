package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgrade;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeInfoDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradePageDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeSaveDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ProjectScreenUpgradeUpdateDTO;
import com.landleaf.homeauto.center.device.model.mapper.ProjectScreenUpgradeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectScreenUpgradeScopeService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectScreenUpgradeService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUpgrade(ProjectScreenUpgradeSaveDTO requestBody) {
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
        // 校验家庭数只能增不能减
        projectScreenUpgradeScopeService.updateUpgradeScope(screenUpgrade, requestBody);
    }

    @Override
    public ProjectScreenUpgradeInfoDTO getInfoById(String id) {


        return null;
    }

    @Override
    public BasePageVO<ProjectScreenUpgradeInfoDTO> pageList(ProjectScreenUpgradePageDTO requestBody) {

        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        List<ProjectScreenUpgradeInfoDTO> data = Lists.newArrayList();
        QueryWrapper<ProjectScreenUpgrade> queryWrapper = new QueryWrapper<>();
        Long realestateId = requestBody.getRealestateId();
        Long projectId = requestBody.getProjectId();
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
//        List<HomeAutoScreenApkDO> queryResult = list(queryWrapper);
//        if (!CollectionUtils.isEmpty(queryResult)) {
//            data.addAll(queryResult.stream().map(i -> {
//                ScreenApkResDTO tmp = new ScreenApkResDTO();
//                BeanUtils.copyProperties(i, tmp);
//                return tmp;
//            }).collect(Collectors.toList()));
//        }
//        PageInfo pageInfo = new PageInfo(data);
//        BasePageVO<ScreenApkResDTO> result = new BasePageVO<>();
//        BeanUtils.copyProperties(pageInfo, result);
//        return result;


        return null;
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

}
