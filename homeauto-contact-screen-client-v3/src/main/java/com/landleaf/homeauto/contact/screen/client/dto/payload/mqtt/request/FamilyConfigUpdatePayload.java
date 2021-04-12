package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据更新通知payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class FamilyConfigUpdatePayload {

    private FamilyConfigUpdatePayloadData data;

    public FamilyConfigUpdatePayload() {
    }

    public FamilyConfigUpdatePayload(FamilyConfigUpdatePayloadData data) {
        this.data = data;
    }
}
