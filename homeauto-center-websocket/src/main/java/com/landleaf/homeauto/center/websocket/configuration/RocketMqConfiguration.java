package com.landleaf.homeauto.center.websocket.configuration;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yujiumin
 * @version 2020/8/12
 */
@Configuration
public class RocketMqConfiguration {

    private static final String NAME_SERVER_ADDR = "";

    private static final String GROUP_NAME = "";

    private static final String TOPIC = "";

    private MessageListenerConcurrently messageListenerConcurrently;

    @Bean
    public void defaultMqPushConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP_NAME);
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(TOPIC, messageSelector());
        consumer.registerMessageListener(messageListenerConcurrently);
//        consumer.start();
    }

    @Autowired
    public void setMessageListenerConcurrently(MessageListenerConcurrently messageListenerConcurrently) {
        this.messageListenerConcurrently = messageListenerConcurrently;
    }

    @Bean
    public MessageSelector messageSelector() {
        return MessageSelector.byTag(null);
    }
}
