package com.landleaf.homeauto.center.device.model.bo.screen.attr;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 产品属性
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenProductAttrBO", description = "产品属性")
public class ScreenProductAttrBO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性編碼")
    private String attrCode;
    @ApiModelProperty(value = "属性值对象")
    private ScreenProductAttrValueBO attrValue;
    @ApiModelProperty(value = "属性值对象")
    private ScreenProductErrorAttrValueBO errorAttrValue;


}
