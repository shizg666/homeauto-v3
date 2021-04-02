package com.landleaf.homeauto.contact.screen.client.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 大屏通过mqtt与云端交互实体
 * @author wenyilu
 */
@Data
@Builder
public class ContactScreenMqttRequest<T> {
    /**
     * 公共参数
     */
    private ContactScreenHeader header;
    /**
     * 特定参数
     */
    private T payload;
}
