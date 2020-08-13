package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload;

import lombok.Data;

/**
 * 执行场景payload
 *
 * @author wenyilu
 */
@Data
public class FamilySceneStatusChangeRequestPayload {

    /**
     * 当前场景号
     */
    private String currentSceneId;


}
