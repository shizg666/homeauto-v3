package com.landleaf.homeauto.center.device.model.bo.screen.attr.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 产品属性
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenSysProductAttrBO", description = "系統产品属性")
public class ScreenSysProductAttrBO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "属性編碼")
    private String attrCode;
    @ApiModelProperty(value = "属性值对象")
    private ScreenSysProductAttrValueBO attrValue;

}
