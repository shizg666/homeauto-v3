package com.landleaf.homeauto.common.domain.dto.screen.payload.event;

import lombok.Data;

/**
 * 大屏在线事件
 * @author wenyilu
 */
@Data
public class ContactScreenOnlineEventPayload {

    /**
     * 设备状态。online：上线。offline：离线
     */
    private String status;

    /**
     * 设备clientId
     */
    private String clientId;


}
