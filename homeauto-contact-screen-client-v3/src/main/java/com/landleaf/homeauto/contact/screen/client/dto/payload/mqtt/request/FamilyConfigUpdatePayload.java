package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.request;

import lombok.Builder;
import lombok.Data;

/**
 * 数据更新通知payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class FamilyConfigUpdatePayload {

    private FamilyConfigUpdatePayloadData data;


}
