package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request;

import lombok.Builder;
import lombok.Data;

/**
 * 读取设备状态payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class DeviceStatusReadRequestPayload {

    private DeviceStatusReadRequestPayloadData data;


}
