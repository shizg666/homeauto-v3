package com.landleaf.homeauto.center.device.model.dto.protocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 协议属性精度配置
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProtocolAttrPrecisionDTO", description="协议属性精度配置")
public class ProtocolAttrPrecisionDTO {


    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "计算系数")
    private String calculationFactor;

    @ApiModelProperty(value = "精度")
    private Integer precision;

    @ApiModelProperty(value = "步长")
    private String step;

    @ApiModelProperty(value = "最大值")
    private String max;

    @ApiModelProperty(value = "最小值")
    private String min;

    @ApiModelProperty(value = "属性主键id")
    private String attrId;


}
