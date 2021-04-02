package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt;

import lombok.Builder;
import lombok.Data;

/**
 * 通用返回payload
 * @author wenyilu
 */
@Data
@Builder
public class CommonResponsePayload {

    /**
     * 状态码
     */
    private int code;
    /**
     * 信息
     */
    private String message;


}
