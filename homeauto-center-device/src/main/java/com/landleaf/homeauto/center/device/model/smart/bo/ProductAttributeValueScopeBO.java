package com.landleaf.homeauto.center.device.model.smart.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 产品属性值域范围业务对象
 *
 * @author Yujiumin
 * @version 2020/10/22
 */
@Data
@ApiModel("产品属性值域范围业务对象")
public class ProductAttributeValueScopeBO {

    @ApiModelProperty("当前值")
    private String currentValue;

    @ApiModelProperty("属性值的最小值")
    private String minValue;

    @ApiModelProperty("属性值的最大值")
    private String maxValue;
}
