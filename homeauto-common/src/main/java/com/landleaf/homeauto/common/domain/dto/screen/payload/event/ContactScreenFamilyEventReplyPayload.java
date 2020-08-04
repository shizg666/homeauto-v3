package com.landleaf.homeauto.common.domain.dto.screen.payload.event;

import lombok.Data;

/**
 * MqttScreenFamilyConfigRequestPayload
 *
 * @author wenyilu
 */
@Data
public class ContactScreenFamilyEventReplyPayload {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 成功或错误信息
     */
    private String message;


}
