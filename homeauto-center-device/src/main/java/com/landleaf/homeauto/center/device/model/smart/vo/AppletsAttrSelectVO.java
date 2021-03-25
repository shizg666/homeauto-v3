package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 属性为枚举时，展示可选值
 * </p>
 * @author pilo
 */
@Data
@ApiModel(value="AppletsAttrSelectVO", description="属性为枚举时，展示可选值")
public class AppletsAttrSelectVO  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性值名称")
    private String name;

    @ApiModelProperty(value = "属性值")
    private String value;


}
