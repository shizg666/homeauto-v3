package com.landleaf.homeauto.center.device.filter.sys;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleAttrDTO;
import com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleDTO;
import com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleDeviceDTO;
import com.landleaf.homeauto.common.enums.FamilyDeviceAttrConstraintEnum;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: SysProductRelatedFilter
 * @description: 系统产品关联规则处理filter
 * @author: wenyilu
 * @date: 2021/5/28
 **/
@Component
public class SysProductRelatedFilter {
    @Autowired
    private ConfigCacheProvider configCacheProvider;

    /**
     * @param: houseTemplateId 户型
     * @param: productCode     产品
     * @param: attrCode        属性
     * @param: systemFlag      设备类型
     * @param: deviceSn        设备号
     * @description: 根据系统（子设备）属性获取户型中关系系统设备(排除自身)
     * @return: java.util.List<com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleDeviceDTO>
     * @author: wyl
     * @date: 2021/5/28
     */
    public List<SysProductRelatedRuleDeviceDTO> filterRelatedDevices(Long houseTemplateId, String attrCode,
                                                                     Integer systemFlag, String deviceSn) {
        List<SysProductRelatedRuleDeviceDTO> result = Lists.newArrayList();
        SysProductRelatedRuleDTO systemRelated = configCacheProvider.getHouseTemplateSystemRelated(houseTemplateId);

        if (Objects.isNull(systemRelated)) {
            return result;
        }
        List<SysProductRelatedRuleAttrDTO> sysAttrs = systemRelated.getSysAttrs();
        SysProductRelatedRuleAttrDTO ruleAttrDTO = sysAttrs.stream().filter(i -> StringUtils.equals(i.getAttrCode(), attrCode)).findFirst().get();
        List<SysProductRelatedRuleDeviceDTO> relatedDevices = ruleAttrDTO.getRelatedDevices();

        if (Objects.isNull(ruleAttrDTO)) {
            // 系统属性都没有
            return result;
        }

        switch (systemFlag) {
            case 1:
                //系统子设备
                result.add(SysProductRelatedRuleDeviceDTO.builder().categoryCode(systemRelated.getSysCategoryCode())
                        .productCode(systemRelated.getSysProductCode()).deviceId(systemRelated.getSysDeviceId())
                        .deviceSn(systemRelated.getSysDeviceSn()).systemFlag(FamilySystemFlagEnum.SYS_DEVICE.getType()).build());
                if (!CollectionUtils.isEmpty(relatedDevices)) {
                    result.addAll(relatedDevices.stream().filter(i -> !StringUtils.equals(i.getDeviceSn(), deviceSn)).collect(Collectors.toList()));
                }
                break;
            case 2:
                //系统设备
                if (!CollectionUtils.isEmpty(relatedDevices)) {
                    result.addAll(relatedDevices);
                }
            default:
                break;
        }
        return result;

    }


    public Integer checkAttrConstraint(Long houseTemplateId, String attrCode,Integer systemFlag, String deviceSn) {
        Integer result = FamilyDeviceAttrConstraintEnum.NORMAL_ATTR.getType();
        switch (systemFlag) {
            case 0:
                result = FamilyDeviceAttrConstraintEnum.NORMAL_ATTR.getType();
            case 1:
                //系统子设备
                SysProductRelatedRuleDTO systemRelated = configCacheProvider.getHouseTemplateSystemRelated(houseTemplateId);
                if (!Objects.isNull(systemRelated)) {
                    List<SysProductRelatedRuleAttrDTO> sysAttrs = systemRelated.getSysAttrs();
                    SysProductRelatedRuleAttrDTO ruleAttrDTO = sysAttrs.stream().filter(i -> StringUtils.equals(i.getAttrCode(), attrCode)).findFirst().get();
                    List<SysProductRelatedRuleDeviceDTO> relatedDevices = ruleAttrDTO.getRelatedDevices();
                    if (!Objects.isNull(ruleAttrDTO) && !CollectionUtils.isEmpty(relatedDevices)) {
                        long count = relatedDevices.stream().filter(i -> StringUtils.equals(i.getDeviceSn(), deviceSn)).count();
                        if (count > 0) {
                            result = FamilyDeviceAttrConstraintEnum.RELATED_SYSTEM_ATTR.getType();
                            break;
                        }
                    }
                }
                result = FamilyDeviceAttrConstraintEnum.NORMAL_ATTR.getType();
                break;
            case 2:
                //系统设备
                result = FamilyDeviceAttrConstraintEnum.SYSTEM_ATTR.getType();
            default:
                break;
        }
        return result;

    }
}
