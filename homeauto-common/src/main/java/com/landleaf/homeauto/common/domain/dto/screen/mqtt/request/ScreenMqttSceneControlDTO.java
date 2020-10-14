package com.landleaf.homeauto.common.domain.dto.screen.mqtt.request;

import lombok.Data;

/**
 * @ClassName ScreenSceneControlDTO
 * @Description: 场景控制DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenMqttSceneControlDTO extends ScreenMqttBaseDTO {

    /**
     * 场景号
     */
    private String sceneId;

    /**
     * 场景编号
     */
    private String sceneNo;

}
