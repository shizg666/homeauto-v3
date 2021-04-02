package com.landleaf.homeauto.center.device.model.bo.screen.attr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 产品故障码故障属性值对象
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenProductErrorCodeAttrValueBO", description = "产品故障码故障属性值对象")
public class ScreenProductErrorCodeAttrValueBO extends ScreenProductErrorAttrValueBaseBO {

    @ApiModelProperty(value = "故障值")
    private String val;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

}
