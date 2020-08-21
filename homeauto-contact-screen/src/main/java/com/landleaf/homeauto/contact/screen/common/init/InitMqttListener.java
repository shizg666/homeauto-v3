package com.landleaf.homeauto.contact.screen.common.init;

import com.landleaf.homeauto.common.mqtt.Client;
import com.landleaf.homeauto.common.mqtt.MessageBaseHandle;
import com.landleaf.homeauto.common.mqtt.MqttFactory;
import com.landleaf.homeauto.common.mqtt.annotation.MqttTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wenyilu
 */
@Component
@Slf4j
public class InitMqttListener implements CommandLineRunner {

    @Autowired
    private MqttFactory mqttFactory;

    @Resource
    private List<MessageBaseHandle> list;

    @Override
    public void run(String... args) throws Exception {
        // mqtt监听，使用sync类型的client即可
        Client client = mqttFactory.getClient(false);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(i -> {
                MqttTopic mt = null;
                if (null != (mt = i.getClass().getAnnotation(MqttTopic.class))) {
                    if (!mt.omitted()) {
                        client.subTopic(mt.topic());
                        log.info("订阅topic:{}", mt.topic());
                    }
                }
            });
        }
    }
}
