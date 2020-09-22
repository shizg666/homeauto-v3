package com.landleaf.homeauto.contact.screen.handle.mqtt;

import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.mqtt.MessageBaseHandle;
import com.landleaf.homeauto.common.mqtt.annotation.MqttTopic;
import com.landleaf.homeauto.contact.screen.service.MqttConnCheckService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author wenyilu
 */
@MqttTopic(topic = "/check/link/heart/beat", wildcard = CommonConst.WildcardConst.LEVEL_WITHOUT, omitted = false)
public class ConnSubMessageHandle extends MessageBaseHandle {
    @Autowired
    private MqttConnCheckService mqttConnCheckService;

    @Override
    public void handle(String topic, MqttMessage message) {
        mqttConnCheckService.refresh();
    }
}
