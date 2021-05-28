package com.landleaf.homeauto.common.domain.dto.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: SysProductRelatedRuleDTO
 * @description: 系统产品关联规则
 * @author: wenyilu
 * @date: 2021/5/28
 **/
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "SysProductRelatedRuleDTO", description = "系统产品关联规则")
public class SysProductRelatedRuleDeviceDTO {

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty("产品Code")
    private String productCode;

    @ApiModelProperty("品类Code")
    private String categoryCode;

    @ApiModelProperty("设备ID")
    private Long deviceId;
    /**
     * {@link com.landleaf.homeauto.common.enums.FamilySystemFlagEnum}
     */
    private Integer systemFlag;

}
