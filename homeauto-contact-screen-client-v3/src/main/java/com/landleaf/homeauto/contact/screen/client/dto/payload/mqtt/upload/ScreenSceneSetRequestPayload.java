package com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload;

import lombok.Data;

/**
 * 大屏执行场景payload
 *
 * @author wenyilu
 */
@Data
public class ScreenSceneSetRequestPayload {

    private ScreenSceneSetRequestData data;

    public ScreenSceneSetRequestPayload(ScreenSceneSetRequestData data) {
        this.data = data;
    }

    public ScreenSceneSetRequestPayload() {
    }
}
