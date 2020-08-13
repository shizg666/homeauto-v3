package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.response;

import lombok.Data;

/**
 * 数据更新通知响应payload
 *
 * @author wenyilu
 */
@Data
public class FamilyConfigUpdateReplyPayload {


    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;


}
