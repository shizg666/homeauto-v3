package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateSceneActionConfig;
import com.landleaf.homeauto.center.device.model.mapper.TemplateSceneActionConfigMapper;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttrInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateSceneService;
import com.landleaf.homeauto.center.device.service.mybatis.ITemplateSceneActionConfigService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-04-07
 */
@Service
public class TemplateSceneActionConfigServiceImpl extends ServiceImpl<TemplateSceneActionConfigMapper, TemplateSceneActionConfig> implements ITemplateSceneActionConfigService {
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IHouseTemplateSceneService iHouseTemplateSceneService;

    @Override
    public List<TemplateSceneActionConfig> getActionsByTemplateId(Long houseTemplateId) {
        QueryWrapper<TemplateSceneActionConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_id",houseTemplateId);
        return list(queryWrapper);
    }
    @Override
    public void addSeceneAction(HouseSceneInfoDTO requestObject) {
        addcheck(requestObject);
        List<TemplateSceneActionConfig> attributes = Lists.newArrayList();
        TemplateDeviceDO deviceDO = iHouseTemplateDeviceService.getById(requestObject.getDeviceId());
        requestObject.getActions().forEach(o -> {
            TemplateSceneActionConfig actionConfig = BeanUtil.mapperBean(requestObject, TemplateSceneActionConfig.class);
            actionConfig.setAttributeCode(o.getAttributeCode());
            actionConfig.setAttributeVal(o.getVal());
            actionConfig.setDeviceSn(deviceDO.getSn());
            actionConfig.setProductCode(deviceDO.getProductCode());
            attributes.add(actionConfig);
        });
        saveBatch(attributes);
    }

    @Override
    public void updateSecneAction(HouseSceneInfoDTO requestObject) {
        HouseSceneDeleteDTO sceneDeleteDTO =HouseSceneDeleteDTO.builder().sceneId(requestObject.getSceneId()).deviceId(requestObject.getDeviceId()).build();
        this.deleteSecneAction(sceneDeleteDTO);
        this.addSeceneAction(requestObject);
    }

    @Override
    public void deleteSecneAction(HouseSceneDeleteDTO sceneDeleteDTO) {
        remove(new LambdaQueryWrapper<TemplateSceneActionConfig>().eq(TemplateSceneActionConfig::getSceneId,sceneDeleteDTO.getSceneId()).eq(TemplateSceneActionConfig::getDeviceId,sceneDeleteDTO.getDeviceId()));
    }

    @Override
    public void deleteSecneActionBySeneId(Long sceneId) {
        remove(new LambdaQueryWrapper<TemplateSceneActionConfig>().eq(TemplateSceneActionConfig::getSceneId,sceneId));
    }

    @Override
    public HouseSceneDeviceConfigVO getDeviceAction(SceneAcionQueryVO requestObject) {
        HouseSceneDeviceConfigVO result = new HouseSceneDeviceConfigVO();
        result.setDeviceId(requestObject.getDeviceId());
        List<DeviceAttrInfoDTO> deviceAttrs = iHouseTemplateDeviceService.getDeviceAttrsInfo(requestObject.getDeviceId());
        result.setActions(deviceAttrs);
        if (CollectionUtils.isEmpty(deviceAttrs)){
            return result;
        }
        List<SceneDeviceAcrionConfigDTO> actions = iHouseTemplateSceneService.getSceneDeviceAction(requestObject);
        Map<String,String> actionMap = actions.stream().collect(Collectors.toMap(SceneDeviceAcrionConfigDTO::getCode,SceneDeviceAcrionConfigDTO::getAttributeVal));
        deviceAttrs.forEach(data->{
            data.setSelectVal(actionMap.get(data.getCode()));
        });
        return result;
    }

    private void addcheck(HouseSceneInfoDTO requestObject) {
        int count = count(new LambdaQueryWrapper<TemplateSceneActionConfig>().eq(TemplateSceneActionConfig::getSceneId,requestObject.getSceneId()).eq(TemplateSceneActionConfig::getDeviceId,requestObject.getDeviceId()).last("limit 1"));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_ALREADY_EXISTS.getCode()), "该设备已关联");
        }
        List<HouseSceneActionConfigDTO> attrs = requestObject.getActions();
        if (CollectionUtils.isEmpty(attrs)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备没关联相应属性");
        }
    }
}
