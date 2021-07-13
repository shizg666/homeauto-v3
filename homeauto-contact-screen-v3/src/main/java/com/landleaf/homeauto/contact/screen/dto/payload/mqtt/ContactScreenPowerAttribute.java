package com.landleaf.homeauto.contact.screen.dto.payload.mqtt;

import lombok.Data;

/**
 * @ClassName功率属性
 **/
@Data
public class ContactScreenPowerAttribute {


    String attrTag;//类似glcPower\glvPower

    /**
     * 属性value值
     */
    String attrValue;
    /**
     * 时间戳
     */
    Long powerTime;

}
