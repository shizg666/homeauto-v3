package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 属性字典表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="AttributeDicPageVO", description="属性字点表page")
public class AttributeDicPageVO {


    @ApiModelProperty(value = "属性主键id")
    private Long id;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;


    @ApiModelProperty(value = "性质 只读，控制")
    private String natureStr;

    public void setNature(Integer nature) {
        this.nature = nature;
        this.natureStr = AttributeNatureEnum.getInstByType(nature).getName();
    }
}
