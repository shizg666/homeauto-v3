package com.landleaf.homeauto.common.domain.dto.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: SysProductRelatedRuleDTO
 * @description: 系统产品关联规则
 * @author: wenyilu
 * @date: 2021/5/28
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SysProductRelatedRuleAttrDTO",description = "系统产品关联规则")
public class SysProductRelatedRuleAttrDTO {
    @ApiModelProperty(value = "属性")
    private String attrCode;
    @ApiModelProperty(value = "关联设备集")
    private List<SysProductRelatedRuleDeviceDTO> relatedDevices;

}
