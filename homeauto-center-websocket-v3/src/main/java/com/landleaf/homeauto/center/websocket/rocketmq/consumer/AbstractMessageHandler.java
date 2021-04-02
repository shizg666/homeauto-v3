package com.landleaf.homeauto.center.websocket.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageConst;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
public abstract class AbstractMessageHandler implements MessageHandler {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(MessageExt messageExt) {
        String[] keys = null;
        if (!Objects.isNull(messageExt.getKeys())) {
            keys = messageExt.getKeys().split(MessageConst.KEY_SEPARATOR);
        }
        byte[] messageBytes = messageExt.getBody();
        String messageString = new String(messageBytes, StandardCharsets.UTF_8);
        return consumeMessage(keys, messageString);
    }

    /**
     * 消费数据
     *
     * @param keys    关键字
     * @param message 消息
     * @return 消费结构
     */
    public abstract ConsumeConcurrentlyStatus consumeMessage(String[] keys, String message);

}
