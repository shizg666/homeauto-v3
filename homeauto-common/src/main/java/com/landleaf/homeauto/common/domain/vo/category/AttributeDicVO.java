package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="AttributeDicVO", description="AttributeDicVO")
public class AttributeDicVO {

    @ApiModelProperty(value = "属性id")
    private String id;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "性质字符串")
    private String natureStr;

    public void setNature(Integer nature) {
        this.nature = nature;
        this.natureStr = AttributeNatureEnum.getInstByType(nature) != null?AttributeNatureEnum.getInstByType(nature).getName():"";
    }
}
