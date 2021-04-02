package com.landleaf.homeauto.center.device.model.smart.bo;

import com.landleaf.homeauto.center.device.model.constant.DeviceNatureEnum;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 产品属性业务对象
 *
 * @author Yujiumin
 * @version 2020/10/23
 */
@Data
@ApiModel("产品属性业务对象")
public class ProductAttributeBO {

    @ApiModelProperty("产品ID")
    private String productId;

    @ApiModelProperty("产品编码")
    private String productCode;

    @ApiModelProperty("产品属性ID")
    private Long productAttributeId;

    @ApiModelProperty("产品属性编码")
    private String productAttributeCode;

    @ApiModelProperty("产品属性名称")
    private String productAttributeName;

    @ApiModelProperty("产品属性类型(单选|多选|特殊多选|值域)")
    private AttributeTypeEnum attributeType;

    @ApiModelProperty("产品属性值范围")
    private ProductAttributeValueScopeBO productAttributeValueScopeBO;

    @ApiModelProperty("产品属性可选值列表(多选|特殊多选时才有值)")
    private List<ProductAttributeInfoBO> productAttributeInfoBOList;


}
