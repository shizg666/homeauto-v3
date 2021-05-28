package com.landleaf.homeauto.common.domain.dto.screen;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 设备属性
 **/
@Data
public class ScreenDeviceAttributeDTO {

    /**
     * 属性code值
     */
    String code;

    /**
     * 属性value值
     */
    String value;

    @ApiModelProperty(value = "属性约束;0:普通属性，1:系统属性，2：关联系统属性")
    private Integer attrConstraint;

}
