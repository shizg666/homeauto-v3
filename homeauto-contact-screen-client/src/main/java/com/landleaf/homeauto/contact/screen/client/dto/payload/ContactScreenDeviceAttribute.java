package com.landleaf.homeauto.contact.screen.client.dto.payload;

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

}
