package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.category.ProtocolEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 品类表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProductDetailVO", description="产品详情")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailVO {


    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "产品编码")
    private String code;

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

    @ApiModelProperty(value = "品类id")
    private String categoryId;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "产品型号")
    private String model;

    @ApiModelProperty(value = "产品图标")
    private String icon;

    @ApiModelProperty(value = "品牌code")
    private String brandCode;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "协议主键id")
    private String protocolId;

    @ApiModelProperty(value = "协议Str")
    private String protocolStr;

    @ApiModelProperty(value = "属性")
    private List<ProductAttributeVO> attributes;

    @ApiModelProperty(value = "故障属性")
    private List<ProductAttributeErrorVO> attributesErrors;

    @ApiModelProperty(value = "性质: 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "是否是暖通")
    private Integer hvacFlag;

    @ApiModelProperty(value = "性质: 只读，控制")
    private String natureStr;

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = CategoryTypeEnum.getInstByType(categoryCode) != null? CategoryTypeEnum.getInstByType(categoryCode).getName():"";
    }
}
