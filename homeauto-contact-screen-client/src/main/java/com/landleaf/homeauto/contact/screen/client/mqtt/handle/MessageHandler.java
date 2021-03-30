package com.landleaf.homeauto.contact.screen.client.mqtt.handle;

import com.landleaf.homeauto.mqtt.container.MessageAcceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author pilo
 */
@Slf4j
@Component
public class MessageHandler implements MessageAcceptor {
    @Override
    public void accept(String topic, byte[] message) {
        try {
            log.info("消息分发处理:topic-{},message-{}",topic,new String(message,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
