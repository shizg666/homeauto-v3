package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload;

import lombok.Builder;
import lombok.Data;

/**
 * 大屏上报故障payload
 * @author wenyilu
 */
@Data
@Builder
public class HVACFaultUploadRequestPayload {

  private HVACFaultUploadRequestData data;

  public HVACFaultUploadRequestPayload() {
  }

  public HVACFaultUploadRequestPayload(HVACFaultUploadRequestData data) {
    this.data = data;
  }
}
