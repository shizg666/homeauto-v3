package com.landleaf.homeauto.contact.screen.dto.payload;

import lombok.Data;

/**
 * @author wenyilu
 * @ClassName故障属性
 **/
@Data
public class ContactScreenHVACAttribute {

    /**
     * 类型值 新风机、空调、PLC
     */
    String attrType;

    /**
     * 属性value值
     */
    String attrValue;

    /**
     * 位置
     */
    String location;

}
