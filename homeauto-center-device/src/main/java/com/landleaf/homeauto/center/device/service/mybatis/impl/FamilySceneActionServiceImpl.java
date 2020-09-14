package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneActionDO;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneActionMapper;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttributionVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneActionService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeService;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneBO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneHvacAtionBO;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 场景关联设备动作表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilySceneActionServiceImpl extends ServiceImpl<FamilySceneActionMapper, FamilySceneActionDO> implements IFamilySceneActionService {

    private IProductAttributeService productAttributeService;

    private IProductAttributeInfoService productAttributeInfoService;

    @Override
    public List<DeviceAttributionVO> getDeviceActionAttributionByDeviceSn(String deviceSn) {
        QueryWrapper<FamilySceneActionDO> familySceneActionQueryWrapper = new QueryWrapper<>();
        familySceneActionQueryWrapper.eq("device_sn", deviceSn);
        List<FamilySceneActionDO> familySceneActionPoList = list(familySceneActionQueryWrapper);
        List<DeviceAttributionVO> deviceAttributionVOList = new LinkedList<>();
        for (FamilySceneActionDO familySceneActionDO : familySceneActionPoList) {
            DeviceAttributionVO deviceAttributionVO = new DeviceAttributionVO();
            String productAttributeId = familySceneActionDO.getProductAttributeId();
            ProductAttributeDO productAttributeDO = productAttributeService.getProductAttributeById(productAttributeId);
            deviceAttributionVO.setAttrName(productAttributeDO.getCode());
            if (Objects.equals(AttributeTypeEnum.getInstByType(productAttributeDO.getType()), AttributeTypeEnum.RANGE)) {
                // 如果属性值类型是值域类型,则直接返回值
                deviceAttributionVO.setAttrValue(familySceneActionDO.getVal());
            } else {
                // 否则去查产品属性值表的具体含义
                ProductAttributeInfoDO productAttributeInfoPo = productAttributeInfoService.getProductAttributeInfoByAttrIdAndCode(productAttributeId, familySceneActionDO.getVal());
                deviceAttributionVO.setAttrValue(productAttributeInfoPo.getName());
            }
            deviceAttributionVOList.add(deviceAttributionVO);
        }
        return deviceAttributionVOList;
    }

    @Override
    public Map<String, String> getDeviceActionAttributionOnMapByDeviceSn(String deviceSn) {
        QueryWrapper<FamilySceneActionDO> familySceneActionQueryWrapper = new QueryWrapper<>();
        familySceneActionQueryWrapper.eq("device_sn", deviceSn);
        List<FamilySceneActionDO> familySceneActionPoList = list(familySceneActionQueryWrapper);
        Map<String, String> attributionMap = new LinkedHashMap<>();
        for (FamilySceneActionDO familySceneActionDO : familySceneActionPoList) {
            String productAttributeId = familySceneActionDO.getProductAttributeId();
            ProductAttributeDO productAttributeDO = productAttributeService.getProductAttributeById(productAttributeId);
            String attrName = productAttributeDO.getCode();
            String attrValue;
            if (Objects.equals(AttributeTypeEnum.getInstByType(productAttributeDO.getType()), AttributeTypeEnum.RANGE)) {
                // 如果属性值类型是值域类型,则直接返回值
                attrValue = familySceneActionDO.getVal();
                if (Objects.equals(attrName, "setting_temperature")) {
                    attrValue += "°C";
                }
            } else {
                // 否则去查产品属性值表的具体含义
                ProductAttributeInfoDO productAttributeInfoPo = productAttributeInfoService.getProductAttributeInfoByAttrIdAndCode(productAttributeId, familySceneActionDO.getVal());
                attrValue = productAttributeInfoPo.getName();
            }
            attributionMap.put(attrName, attrValue);
        }
        return attributionMap;
    }

    @Override
    public List<SyncSceneBO> getListSyncSceneDTO(String familyId) {
        return this.baseMapper.getListSyncSceneDTO(familyId);
    }

    @Override
    public List<SyncSceneHvacAtionBO> getListSceneHvacAction(String familyId) {
        return this.baseMapper.getListSceneHvacAction(familyId);
    }

    @Override
    public List<String> getListDeviceSn(String familyId) {
        return this.baseMapper.getListDeviceSn(familyId);
    }

    @Autowired
    public void setProductAttributeService(IProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @Autowired
    public void setProductAttributeInfoService(IProductAttributeInfoService productAttributeInfoService) {
        this.productAttributeInfoService = productAttributeInfoService;
    }
}
