package com.landleaf.homeauto.center.device.model.bo.screen.attr.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName ScreenProductAttrBaseBO
 * @Description: TODO
 * @Author wyl
 * @Date 2021/3/31
 * @Version V1.0
 **/
@Data
public class ScreenSysProductAttrValueBaseBO {
    @ApiModelProperty(value = "属性編碼")
    private String attrCode;
    @ApiModelProperty(value = "属性名称")
    private String attrName;
    @ApiModelProperty(value = "产品编码")
    private String productCode;
}
