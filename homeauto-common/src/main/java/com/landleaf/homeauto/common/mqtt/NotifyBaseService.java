package com.landleaf.homeauto.common.mqtt;

import com.landleaf.homeauto.common.constance.QosEnumConst;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lokiy
 * @date 2019/9/6 17:22
 * @description:
 */
@Slf4j
@Data
public class NotifyBaseService {

    private MqttFactory mqttFactory;

    public void notify(String topic, String message, QosEnumConst qosEnumConst) {
        log.info("发送同步通知==》topic:{},message:{}", topic, message);
        Client client = mqttFactory.getClient(false);
        client.pubTopic(topic, message, qosEnumConst);
        log.info("发送同步通知==》成功。topic:{},message:{}", topic, message);

    }

}
