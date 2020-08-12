package com.landleaf.homeauto.contact.screen.dto.payload.http.request;

import lombok.Data;

/**
 * 节假日判断请求
 *
 * @author wenyilu
 */
@Data
public class HolidaysCheckRequestPayload {

    /**
     * 判断某一天是否是节假日格式:yyyy-MM-dd
     */
    private String date;


}
