package com.landleaf.homeauto.contact.screen.client.mqtt.subscribe;

import com.landleaf.homeauto.common.mqtt.Client;
import com.landleaf.homeauto.common.mqtt.MessageBaseHandle;
import com.landleaf.homeauto.common.mqtt.annotation.MqttTopic;
import com.landleaf.homeauto.mqtt.api.client.RsocketClientSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName HomeautoSubscribeTopic
 * @Description: TODO
 * @Author wyl
 * @Date 2021/3/30
 * @Version V1.0
 **/
@Slf4j
@Component
@ConditionalOnProperty(prefix = "homeauto.v3.mqtt.client",name = "enable", havingValue = "true")
public class HomeautoSubscribeTopic {
    @Resource
    private List<MessageBaseHandle> list;
    @Autowired
    private RsocketClientSession initClient;
    @PostConstruct
    private void subTopic(){
        // mqtt监听，使用sync类型的client即可
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(i -> {
                MqttTopic mt = null;
                if (null != (mt = i.getClass().getAnnotation(MqttTopic.class))) {
                    if (!mt.omitted()) {
                        initClient.sub(mt.topic());
                        log.info("订阅topic:{}", mt.topic());
                    }
                }
            });
        }
    }

}
