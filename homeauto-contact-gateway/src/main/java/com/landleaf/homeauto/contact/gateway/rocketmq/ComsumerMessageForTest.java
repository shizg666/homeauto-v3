package com.landleaf.homeauto.contact.gateway.rocketmq;

import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.common.annotation.RocketMQConsumeService;
import com.landleaf.homeauto.common.domain.message.TestMessage;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.AbstractMQMsgProcessor;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeResult;
import com.landleaf.homeauto.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 门禁客户端返回数据消费处理
 */
@RocketMQConsumeService(topic = "test", tags = "test")
@Component
public class ComsumerMessageForTest extends AbstractMQMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComsumerMessageForTest.class);

    @Override
    protected MQConsumeResult consumeMessage(String tag, List<String> keys, MessageExt message) {
        LOGGER.info("消费消息msgid:{}", message.getMsgId());
        try {
            String msgBody = new String(message.getBody(), "utf-8");
            LOGGER.info("收到消息[tag:{}];[消息:{}]", tag, msgBody);
            //解析消息
            TestMessage kngihtMessage = JsonUtil.jsonToBean(msgBody,TestMessage.class);
            LOGGER.info("消费消息实体{}", kngihtMessage.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //本程序异常，无需通知MQ重复下发消息
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }


        MQConsumeResult result = new MQConsumeResult();
        result.setSuccess(true);
        return result;
    }

}
