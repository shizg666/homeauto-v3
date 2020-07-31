package com.landleaf.homeauto.common.domain.dto.screen.payload;

import lombok.Data;

/**
 * 接收请求payload
 */
@Data
public class MqttScreenOnlineDevicePayload {

    /**
     * 设备状态。online：上线。offline：离线
     */
    private String status;

    /**
     * 设备号
     */
    private String deviceSn;


}
