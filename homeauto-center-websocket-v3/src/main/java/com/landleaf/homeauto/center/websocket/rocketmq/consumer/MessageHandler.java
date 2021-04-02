package com.landleaf.homeauto.center.websocket.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
public interface MessageHandler {

    /**
     * 消费消息
     *
     * @param messageExt 消息
     * @return 消费结果
     */
    ConsumeConcurrentlyStatus consumeMessage(MessageExt messageExt);

}
