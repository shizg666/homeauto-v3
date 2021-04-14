package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 属性值字典表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProductAttributeInfoVO", description="产品属性值VO")
public class ProductAttributeInfoVO {


    @ApiModelProperty(value = "属性值名称")
    private String name;

    @ApiModelProperty(value = "属性值")
    private String code;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

}
