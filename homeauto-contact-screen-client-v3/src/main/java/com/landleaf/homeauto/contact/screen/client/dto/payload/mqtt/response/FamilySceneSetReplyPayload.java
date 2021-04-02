package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response;

import lombok.Data;

/**
 * 执行场景返回payload
 *
 * @author wenyilu
 */
@Data
public class FamilySceneSetReplyPayload {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 成功或错误信息
     */
    private String message;


}
