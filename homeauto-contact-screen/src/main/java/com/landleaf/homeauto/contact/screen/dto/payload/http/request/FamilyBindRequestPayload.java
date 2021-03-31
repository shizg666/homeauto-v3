package com.landleaf.homeauto.contact.screen.dto.payload.http.request;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenFamilyBind;
import lombok.Data;

/**
 * 大屏绑定screenMac
 *
 * @author wenyilu
 */
@Data
public class FamilyBindRequestPayload {

    /**
     * 判断某一天是否是节假日格式:yyyy-MM-dd
     */
    private ContactScreenFamilyBind request;


}
