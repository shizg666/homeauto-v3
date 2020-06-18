package com.landleaf.homeauto.common.rocketmq.consumer.processor;

import com.alibaba.rocketmq.common.message.MessageConst;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 所有消息处理继承该类
 */
public abstract class AbstractMQMsgProcessor implements MQMsgProcessor {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMQMsgProcessor.class);

    @Override
    public MQConsumeResult handle(String topic, String tag, List<MessageExt> msgs) {
        MQConsumeResult mqConsumeResult = new MQConsumeResult();
        for (MessageExt messageExt : msgs) {
            mqConsumeResult = this.consumeMessage(tag, messageExt.getKeys() == null ? null : Arrays.asList(messageExt.getKeys().split(MessageConst.KEY_SEPARATOR)), messageExt);
        }
        return mqConsumeResult;
    }

    /**
     * 消息某条消息
     *
     * @param tag        标签
     * @param keys       消息关键字
     * @param messageExt
     */
    protected abstract MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt messageExt);

}
