package com.landleaf.homeauto.common.mqtt;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import lombok.Data;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wenyilu
 */
@Data
public class SyncSendUtil {

    private MqttConfigProperty mqttConfigProperty;

    private static int MAXSIZE = 16;

    private static Map<Integer, SyncSendClient> sendFactory = new ConcurrentHashMap<Integer, SyncSendClient>(MAXSIZE, 1);

    private int index = 0;

    Environment environment;

    public void init() {
        if (StringUtils.isEmpty(mqttConfigProperty.getServerUrl())) {
            return;
        }
        for (int i = 0; i < MAXSIZE; i++) {
            SyncSendClient client = new SyncSendClient();
            String applicationName = environment.getProperty("spring.application.name");
            String active = environment.getProperty("spring.profiles.active");
            client.setSpecialClientId(String.format("%s:%s:%s",applicationName,active,IdGeneratorUtil.getUUID32()));
            client.setMqttConfigProperty(mqttConfigProperty);
            client.init();
            sendFactory.put(i, client);
        }
    }


    public void pubTopic(String topic, String message, QosEnumConst qos) {
        sendFactory.get(index++ % 16).pubTopic(topic, message, qos);
    }
}
