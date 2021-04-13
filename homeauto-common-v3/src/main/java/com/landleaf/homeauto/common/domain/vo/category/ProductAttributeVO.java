package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@ApiModel(value="ProductAttributeVO", description="产品属性VO")
public class ProductAttributeVO {

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "属性值字符串")
    private String desc;

    @ApiModelProperty(value = "属性类别; 1多选，2 值域")
    private Integer type;

    @ApiModelProperty(value = "属性可选值 type =1")
    private List<AttributeInfoDicDTO> infos;

    @ApiModelProperty(value = "属性范围信息 type =2")
    private ProductAttributeScopeDTO scope;


}
