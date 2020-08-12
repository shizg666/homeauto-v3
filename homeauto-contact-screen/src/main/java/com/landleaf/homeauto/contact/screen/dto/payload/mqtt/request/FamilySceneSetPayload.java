package com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request;

import lombok.Builder;
import lombok.Data;

/**
 * 执行场景payload
 *
 * @author wenyilu
 */
@Data
@Builder
public class FamilySceneSetPayload {

    /**
     * 场景号
     */
    private String sceneId;


}
