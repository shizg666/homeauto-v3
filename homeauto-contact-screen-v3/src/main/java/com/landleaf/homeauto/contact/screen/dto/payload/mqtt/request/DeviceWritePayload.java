package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 接收请求payload
 * @author wenyilu
 */
@Data
@Builder
public class DeviceWritePayload {

   private DeviceWritePayloadData data;

}
