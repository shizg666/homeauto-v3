package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload;

import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
