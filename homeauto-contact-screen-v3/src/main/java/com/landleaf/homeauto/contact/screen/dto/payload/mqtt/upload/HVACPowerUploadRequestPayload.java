package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.upload
        ;

import lombok.Builder;
import lombok.Data;

/**
 * 大屏上报功率payload
 */
@Data
@Builder
public class HVACPowerUploadRequestPayload {

  private HVACPowerRequestData data;

  public HVACPowerUploadRequestPayload() {
  }

  public HVACPowerUploadRequestPayload(HVACPowerRequestData data) {
    this.data = data;
  }
}
