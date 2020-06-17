package com.landleaf.homeauto.common.mqtt;

import com.landleaf.homeauto.common.annotation.MqttTopic;
import com.landleaf.homeauto.common.constance.CommonConst;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 当系统没有定义关于某topic的的处理类时，此任务处理该topic，仅做日志记录
 *
 * @author hebin
 */
@Component
@MqttTopic(topic = "#", wildcard = CommonConst.WildcardConst.LEVEL_WITHOUT, omitted = true)
public class DefaultMessageHandle extends MessageBaseHandle {
    /**
     * 日志句柄
     */
    private static Logger logger = LoggerFactory.getLogger(DefaultMessageHandle.class);

    @Override
    public void handle(String topic, MqttMessage message) {
        // 接受消息，直接记录日志即可
        logger.info("recerive message,topic is {}, qos is {}, message is {}", topic, message.getQos(),
                new String(message.getPayload()));
    }
}
