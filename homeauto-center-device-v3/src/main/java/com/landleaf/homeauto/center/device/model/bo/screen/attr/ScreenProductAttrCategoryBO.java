package com.landleaf.homeauto.center.device.model.bo.screen.attr;

import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 产品属性分类外层对象
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenProductAttrCategoryBO", description = "产品属性分类外层对象")
public class ScreenProductAttrCategoryBO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "功能类别 1:功能 2:基本 3:故障")
    @TableField("function_type")
    private Integer functionType;
    @ApiModelProperty(value = "非系统产品属性（普通设备、系统子设备）")
    private ScreenProductAttrBO attrBO;
    @ApiModelProperty(value = "系统产品属性（普通设备、系统子设备）")
    private ScreenSysProductAttrBO sysAttrBO;



}
