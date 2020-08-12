package com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload;

import lombok.Data;

/**
 * @ClassName ScreenSceneStatusUploadDTO
 * @Description: 场景状态上报DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttSceneStatusUploadDTO extends ScreenMqttUploadBaseDTO {

    /**
     * 当前场景号
     */
    private String currentSceneId;
}
