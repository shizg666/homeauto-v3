package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request;

import lombok.Builder;
import lombok.Data;

/**
 * 读取设备状态payload
 * @author wenyilu
 */
@Data
@Builder
public class DeviceStatusReadRequestPayload {

    /**
     * 设备号
     */
    private String deviceSn;
    /**
     * 产品编码
     */
    private String productCode;


}
