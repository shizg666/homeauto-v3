package com.landleaf.homeauto.contact.screen.dto.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wenyilu
 * @ClassName 设备属性
 **/
@Data
public class ContactScreenDeviceAttribute {

    /**
     * 属性code值
     */
    String attrTag;

    /**
     * 属性value值
     */
    String attrValue;

    @ApiModelProperty(value = "属性约束;0:普通属性，1:系统属性，2：关联系统属性")
    private Integer attrConstraint;

}
