package com.landleaf.homeauto.common.domain.dto.screen.callback;

import com.landleaf.homeauto.common.enums.screen.MqttCallBackTypeEnum;
import lombok.Data;

/**
 * @author wenyilu
 * @ClassName mqtt-callback -上下线dto
 **/
@Data
public class ScreenMqttCallBackOnLineDTO {


    /**
     * 客户端id--对应大屏mac
     */
    private String clientid;

    /**
     * 动作
     * {@link MqttCallBackTypeEnum}
     */
    private String action;

}
