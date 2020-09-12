package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.CategoryModeValueEnum;
import com.landleaf.homeauto.center.device.enums.CategoryWindSpeedEnum;
import com.landleaf.homeauto.center.device.model.bo.HvacSceneConfigActionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfig;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfigAction;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneHvacConfigActionMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneHvacConfigActionService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneHvacConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
@Service
public class FamilySceneHvacConfigActionServiceImpl extends ServiceImpl<FamilySceneHvacConfigActionMapper, FamilySceneHvacConfigAction> implements IFamilySceneHvacConfigActionService {

    @Autowired
    private IFamilySceneHvacConfigService familySceneHvacConfigService;

    @Override
    public HvacSceneConfigActionBO getHvacSceneConfigAction(String sceneId) {
        HvacSceneConfigActionBO hvacSceneConfigActionBO = new HvacSceneConfigActionBO();

        // 场景配置
        QueryWrapper<FamilySceneHvacConfig> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("scene_id", sceneId);
        FamilySceneHvacConfig familySceneHvacConfig = familySceneHvacConfigService.getOne(configQueryWrapper);

        // 场景配置模式
        QueryWrapper<FamilySceneHvacConfigAction> configActionQueryWrapper = new QueryWrapper<>();
        configActionQueryWrapper.eq("hvac_config_id", familySceneHvacConfig.getId());
        FamilySceneHvacConfigAction familySceneHvacConfigAction = getOne(configActionQueryWrapper);


        hvacSceneConfigActionBO.setId(familySceneHvacConfig.getId());
        hvacSceneConfigActionBO.setWorkMode(Objects.requireNonNull(CategoryModeValueEnum.getInstance(familySceneHvacConfigAction.getModeVal())).getName());
        hvacSceneConfigActionBO.setAirSpeed(Objects.requireNonNull(CategoryWindSpeedEnum.getInstance(familySceneHvacConfigAction.getWindVal())).getName());
        hvacSceneConfigActionBO.setWorkTemperature(familySceneHvacConfigAction.getTemperatureVal());
        return hvacSceneConfigActionBO;
    }

    @Override
    public List<String> getListIds(List<String> hvacConfigIds) {
        if (CollectionUtils.isEmpty(hvacConfigIds)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return this.baseMapper.getListIds(hvacConfigIds);
    }

}
