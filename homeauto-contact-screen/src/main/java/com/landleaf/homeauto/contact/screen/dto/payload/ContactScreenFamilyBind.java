package com.landleaf.homeauto.contact.screen.dto.payload;

import lombok.Data;

/**
 * @ClassName 大屏绑定消息体
 **/
@Data
public class ContactScreenFamilyBind {

    /**
     * "familyCode": "a10101-01010101",
     */
    private String familyCode;
    private String screenMac;


}
