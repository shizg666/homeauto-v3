package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import com.landleaf.homeauto.common.enums.screen.MqttCallBackTypeEnum;
import lombok.Data;

/**
 * 大屏apk版本检查
 * @author wenyilu
 */
@Data
public class ScreenHttpMqttCallBackDTO extends ScreenHttpRequestDTO {


    /**
     * 动作
     * {@link MqttCallBackTypeEnum}
     */
    private String action;

}
