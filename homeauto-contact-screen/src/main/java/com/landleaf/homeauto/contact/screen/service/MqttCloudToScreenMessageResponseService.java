package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttResponseBaseDTO;

/**
 * 内部服务向下消息响应处理器
 *
 * @author wenyilu
 */
public interface MqttCloudToScreenMessageResponseService {


    void response(ScreenMqttResponseBaseDTO screenResponseBaseDTO, String outerMessageId, String operateName);
}
