package com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload;

import lombok.Data;

/**
 * 大屏执行场景上报DTO
 * @author wenyilu
 */
@Data
public class ScreenMqttScreenSceneSetUploadDTO extends ScreenMqttUploadBaseDTO {

    /**
     * 当前场景号
     */
    private Long sceneId;
}
