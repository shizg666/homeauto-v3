package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.request;

import lombok.Builder;
import lombok.Data;

/**
 * 执行场景payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class FamilySceneSetPayloadData {

    /**
     * 场景号
     */
    private String sceneId;


}
