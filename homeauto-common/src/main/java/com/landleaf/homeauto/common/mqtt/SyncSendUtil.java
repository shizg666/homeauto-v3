package com.landleaf.homeauto.common.mqtt;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.landleaf.homeauto.common.constance.QosEnumConst;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SyncSendUtil {

    @Resource
    private MqttConfig config;

    private static int MAXSIZE = 16;

    private static Map<Integer, SyncSendClient> sendFactory = new ConcurrentHashMap<Integer, SyncSendClient>(MAXSIZE, 1);

    private int index = 0;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(config.getUrl())) {
            return;
        }
        for (int i = 0; i < MAXSIZE; i++) {
            SyncSendClient client = new SyncSendClient();
            client.init(IdGeneratorUtil.getUUID32(), config.getUrl());
            sendFactory.put(i, client);
        }
    }


    public void pubTopic(String topic, String message, QosEnumConst qos) {
        sendFactory.get(index++ % 16).pubTopic(topic, message, qos);
    }
}
