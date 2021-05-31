package com.landleaf.homeauto.center.device.model.vo.sys_product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 系统产品表
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysProductDetailVO", description="系统产品详情展示对象")
public class SysProductDetailVO {


    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "系统产品code")
    private String code;

    @ApiModelProperty(value = "系统产品名称")
    private String name;

    @ApiModelProperty(value = "系统产品类型")
    private Integer type;

    @ApiModelProperty(value = "关联的品类")
    List<SysProductCategoryVO> categorys;

    @ApiModelProperty(value = "系统产品功能属性")
    List<SysProductAttributeVO> attributesFunc;

    @ApiModelProperty(value = "系统产品基本属性")
    List<SysProductAttributeVO> attributesBase;

}
