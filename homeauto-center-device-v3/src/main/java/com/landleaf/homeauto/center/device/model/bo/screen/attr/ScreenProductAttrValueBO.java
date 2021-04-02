package com.landleaf.homeauto.center.device.model.bo.screen.attr;

import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 产品功能基本属性值对象
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenProductFunctionAttrBO", description = "产品功能基本属性值对象")
public class ScreenProductAttrValueBO extends ScreenProductAttrValueBaseBO {
    @ApiModelProperty(value = "属性类别;1:多选，2:值域")
    private Integer type;
    @ApiModelProperty(value = "值类别为多选时，可选值列表")
    private List<ProductAttributeInfoDO> selectValues;
    @ApiModelProperty(value = "值类别为值域时，值域对象")
    private ProductAttributeInfoScope numValue;
}
