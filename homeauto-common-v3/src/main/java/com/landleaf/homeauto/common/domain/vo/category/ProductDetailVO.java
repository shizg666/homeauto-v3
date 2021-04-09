package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.category.ProtocolEnum;
import com.landleaf.homeauto.common.util.StringUtil;
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


    @ApiModelProperty(value = "产品主键id")
    private String id;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "产品编码")
    private String code;

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "产品型号")
    private String model;

    @ApiModelProperty(value = "品牌code")
    private String brandCode;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

//    @ApiModelProperty(value = "产品图片")
//    private String icon;
//
//    @ApiModelProperty(value = "icon2")
//    private String icon2;

    @ApiModelProperty(value = "性质: 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "性质: 只读，控制")
    private String natureStr;

    @ApiModelProperty(value = "协议 ,号分隔 ps 1,2")
    private String protocol;

    @ApiModelProperty(value = "协议字符串")
    private String protocolStr;

    public void setProtocol(String protocol) {
        this.protocol = protocol;
        if (StringUtil.isEmpty(protocol)){
            return;
        }
        String[] strArray = protocol.split(",");
        if (strArray.length == 0){
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strArray) {
            sb.append(ProtocolEnum.getInstByType(s) != null? ProtocolEnum.getInstByType(s).getName():"").append(",");
        }
        this.protocolStr = sb.toString().substring(0,sb.toString().length()-1);
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = CategoryTypeEnum.getInstByType(categoryCode) == null?"":CategoryTypeEnum.getInstByType(categoryCode).name;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
        this.natureStr = AttributeNatureEnum.getInstByType(nature) != null? AttributeNatureEnum.getInstByType(nature).getName():"";
    }

    @ApiModelProperty(value = "属性")
    private List<ProductAttributeVO> attributes;

    @ApiModelProperty(value = "故障属性")
    private List<ProductAttributeErrorVO> attributesErrors;


}
