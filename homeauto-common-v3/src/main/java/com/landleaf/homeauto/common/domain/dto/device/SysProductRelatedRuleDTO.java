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
@ApiModel(value = "SysProductRelatedRuleDTO",description = "系统产品关联规则")
public class SysProductRelatedRuleDTO {


    @ApiModelProperty(value = "楼盘")
    private Long realestateId;
    @ApiModelProperty(value = "项目")
    private Long projectId;
    @ApiModelProperty(value = "户型")
    private Long houseTemplateId;
    @ApiModelProperty(value = "系统产品编码")
    private String sysProductCode;
    @ApiModelProperty(value = "系统设备sn")
    private String sysDeviceSn;
    @ApiModelProperty("品类Code")
    private String sysCategoryCode;
    @ApiModelProperty("设备ID")
    private Long sysDeviceId;
    @ApiModelProperty(value = "系统属性集")
    private List<SysProductRelatedRuleAttrDTO> sysAttrs;

}
