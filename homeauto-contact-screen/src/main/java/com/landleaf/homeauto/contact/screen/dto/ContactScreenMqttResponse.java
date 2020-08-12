package com.landleaf.homeauto.contact.screen.dto;

import lombok.Data;

/**
 * 大屏通过mqtt与云端交互实体
 *
 * @author wenyilu
 */
@Data
public class ContactScreenMqttResponse<T> {
    /**
     * 公共参数
     */
    private ContactScreenHeader header;
    /**
     * 特定参数
     */
    private T payload;
}
