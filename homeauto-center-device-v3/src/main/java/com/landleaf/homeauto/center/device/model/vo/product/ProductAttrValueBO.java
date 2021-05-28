package com.landleaf.homeauto.center.device.model.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProductAttrValueBO", description="ProductAttrValueBO")
public class ProductAttrValueBO {


    @ApiModelProperty(value = "产品属性值code")
    private String code;

    @ApiModelProperty(value = "产品属性名称")
    private String name;

}
