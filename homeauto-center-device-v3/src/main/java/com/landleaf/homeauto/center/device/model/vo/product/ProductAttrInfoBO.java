package com.landleaf.homeauto.center.device.model.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
@ApiModel(value="ProductAttrInfoBO", description="ProductAttrInfoBO")
public class ProductAttrInfoBO {

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品属性code")
    private String code;

    @ApiModelProperty(value = "产品属性名称")
    private String name;

    @ApiModelProperty(value = "产品属性值列表")
    List<ProductAttrValueBO> values;

}
