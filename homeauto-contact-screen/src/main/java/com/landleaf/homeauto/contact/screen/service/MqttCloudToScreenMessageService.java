package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttBaseDTO;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import org.springframework.stereotype.Component;

/**
 * 内部服务向下消息的处理器
 *
 * @author wenyilu
 */
@Component
public interface MqttCloudToScreenMessageService  {


    ContactScreenDomain buildMessage(ScreenMqttBaseDTO requestDto, String operateName);

    void addTask(ContactScreenDomain messageDomain);
}
