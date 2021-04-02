package com.landleaf.homeauto.contact.screen.dto.payload.http.response;

import lombok.Data;

/**
 * 节假日判断请求响应
 *
 * @author wenyilu
 */
@Data
public class HolidaysCheckRequestReplyPayload {


    /**
     * 是否节假日
     */
    private Boolean result;

}
