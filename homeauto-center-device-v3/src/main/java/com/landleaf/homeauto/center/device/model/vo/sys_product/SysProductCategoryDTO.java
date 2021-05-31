package com.landleaf.homeauto.center.device.model.vo.sys_product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@ApiModel(value="SysProductCategoryDTO", description="系统产品表")
public class SysProductCategoryDTO implements Serializable {

    private static final long serialVersionUID = -6171846364589523989L;
    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "品类数量 0 1个 1多个")
    private Integer categoryNum;

    @ApiModelProperty(value = "系统品类功能属性")
    List<SysCategoryAttributeDTO> attributesFunc;

    @ApiModelProperty(value = "系统品类基本属性")
    List<SysCategoryAttributeDTO> attributesBase;


}
