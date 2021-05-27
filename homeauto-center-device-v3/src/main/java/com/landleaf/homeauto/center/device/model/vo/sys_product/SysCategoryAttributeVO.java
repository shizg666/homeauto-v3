package com.landleaf.homeauto.center.device.model.vo.sys_product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysCategoryAttributeVO", description="")
public class SysCategoryAttributeVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "属性值字符串")
    private String desc;

    @ApiModelProperty(value = "属性类别;多选，值域")
    private Integer type;

    @ApiModelProperty(value = "属性类型 1控制 2只读")
    private Integer nature;

    @ApiModelProperty(value = "功能类型 1基本属性 2 功能属性")
    private Integer functionType;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "属性可选值, 属性是 多选（1）类型 有值")
    private List<SysProductAttributeInfoDTO> infos;


}
