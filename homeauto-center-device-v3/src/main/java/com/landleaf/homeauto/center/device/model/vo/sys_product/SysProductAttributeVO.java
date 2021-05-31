package com.landleaf.homeauto.center.device.model.vo.sys_product;

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
@ApiModel(value="SysProductAttributeVO", description="SysProductAttributeVO")
public class SysProductAttributeVO {

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "属性值字符串")
    private String desc;

    @ApiModelProperty(value = "属性类别;多选，值域")
    private Integer type;

    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "属性功能类型;2基本，1功能")
    private Integer functionType;

//    @ApiModelProperty(value = "属性关联的 品类code 多个以 ，分隔")
//    private String categoryList;
@ApiModelProperty(value = "属性关联的 品类code")
    private String categoryStrList;

    @ApiModelProperty(value = "属性范围信息，属性是 值域类型（2）有值")
    private SysProductAttributeScopeDTO scope;

    @ApiModelProperty(value = "属性可选值, 属性是 多选（1）类型 有值")
    private List<SysProductAttributeInfoDTO> infos;


}
