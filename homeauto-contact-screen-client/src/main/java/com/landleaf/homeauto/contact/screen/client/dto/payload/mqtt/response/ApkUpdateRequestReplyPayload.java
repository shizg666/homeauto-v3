package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response;

import lombok.Data;

/**
 * apk请求响应payload
 *
 * @author wenyilu
 */
@Data
public class ApkUpdateRequestReplyPayload {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;


}
