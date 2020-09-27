package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload;

import lombok.Builder;
import lombok.Data;

/**
 * 大屏上报设备状态payload
 * @author wenyilu
 */
@Data
@Builder
public class DeviceStatusUpdateRequestPayload {

  private DeviceStatusUpdateRequestData data;


  public DeviceStatusUpdateRequestPayload() {
  }

  public DeviceStatusUpdateRequestPayload(DeviceStatusUpdateRequestData data) {
    this.data = data;
  }
}
