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
@ApiModel(value="ProductAttributeInfoDTO", description="产品属性值对象")
public class ProductAttributeInfoDTO {


    @NotBlank(message = "属性值名称不能为空")
    @ApiModelProperty(value = "属性值名称")
    private String name;

    @NotBlank(message = "属性值code不能为空")
    @ApiModelProperty(value = "属性值")
    private String code;

    @ApiModelProperty(value = "排序号")
    private Integer sortNo;


    @ApiModelProperty(value = "属性范围信息，特殊多选值有值")
    private AttributeInfoScopeDTO scope;

}
