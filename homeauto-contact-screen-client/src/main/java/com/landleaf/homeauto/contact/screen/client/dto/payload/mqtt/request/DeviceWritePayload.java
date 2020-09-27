package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.request;

import lombok.Builder;
import lombok.Data;

/**
 * 接收请求payload
 * @author wenyilu
 */
@Data
@Builder
public class DeviceWritePayload {

   private DeviceWritePayloadData data;

}
