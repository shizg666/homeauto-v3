package com.landleaf.homeauto.center.websocket.rocketmq.properties;

import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 消费者配置
 *
 * @author Yujiumin
 * @version 2020/9/7
 */
@Configuration
@ConfigurationProperties("rocketmq.consumer")
public class ConsumerConfigurationProperties {

    private int consumeThreadMin = 8;

    private int consumeThreadMax = 16;

    private ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET;

    private MessageModel messageModel = MessageModel.CLUSTERING;

    public int getConsumeThreadMin() {
        return consumeThreadMin;
    }

    public void setConsumeThreadMin(int consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public int getConsumeThreadMax() {
        return consumeThreadMax;
    }

    public void setConsumeThreadMax(int consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public ConsumeFromWhere getConsumeFromWhere() {
        return consumeFromWhere;
    }

    public void setConsumeFromWhere(ConsumeFromWhere consumeFromWhere) {
        this.consumeFromWhere = consumeFromWhere;
    }

    public MessageModel getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(MessageModel messageModel) {
        this.messageModel = messageModel;
    }
}
