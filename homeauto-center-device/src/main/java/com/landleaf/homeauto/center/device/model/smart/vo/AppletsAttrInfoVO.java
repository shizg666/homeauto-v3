package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 小程序属性展示
 * </p>
 *
 * @author pilo
 */
@Data
@ApiModel(value="AppletsAttrInfoVO", description="小程序属性展示")
public class AppletsAttrInfoVO {

    @ApiModelProperty(value = "属性编码")
    private String code;
    @ApiModelProperty(value = "值类型(1:枚举，2：数值)")
    private Integer valueType;
    @ApiModelProperty(value = "品类")
    private Object currentValue;
    @ApiModelProperty(value = "协议属性具体值配置 valueType--值类型是1 枚举值")
    private List<AppletsAttrSelectVO> selects;
    @ApiModelProperty(value = "协议属性具体值配置 valueType--值类型是2 数值控制条件")
    private AppletsAttrPrecisionVO precision;


}
