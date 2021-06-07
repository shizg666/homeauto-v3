package com.landleaf.homeauto.center.device.model.bo.screen.attr.sys;

import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfoScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 产品功能基本属性值对象
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenSysProductAttrValueBO", description = "系统产品功能基本属性值对象")
public class ScreenSysProductAttrValueBO extends ScreenSysProductAttrValueBaseBO {
    @ApiModelProperty(value = "属性类别;1:多选，2:值域")
    private Integer type;
    @ApiModelProperty(value = "值类别为多选时，可选值列表")
    private List<SysProductAttributeInfo> selectValues;
    @ApiModelProperty(value = "值类别为值域时，值域对象")
    private SysProductAttributeInfoScope numValue;
}
