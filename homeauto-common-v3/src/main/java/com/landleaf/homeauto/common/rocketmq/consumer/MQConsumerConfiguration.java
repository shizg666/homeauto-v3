package com.landleaf.homeauto.common.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.exception.RocketMQException;
import com.landleaf.homeauto.common.rocketmq.consumer.processor.MQConsumeMsgListenerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/**
 * 消费者Bean配置
 * .<br/>
 */
@ConditionalOnProperty(prefix = "homeauto.rocketmq.consumer", name = "enable")
@Configuration
public class MQConsumerConfiguration {
    public static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerConfiguration.class);

    @Autowired(required = false)
    private MQConsumerConfig mqConsumerConfig;
    @Autowired(required = false)
    private MQConsumeMsgListenerProcessor mqMessageListenerProcessor;

    @Bean
    public DefaultMQPushConsumer getDefaultMQPushConsumer() throws RocketMQException {
        String groupName = mqConsumerConfig.getGroupName();
        String namesrvAddr = mqConsumerConfig.getNamesrvAddr();
        String topics = mqConsumerConfig.getTopics();
        int consumeThreadMax = mqConsumerConfig.getConsumeThreadMax();
        int consumeThreadMin = mqConsumerConfig.getConsumeThreadMin();
        int consumeMessageBatchMaxSize = mqConsumerConfig.getConsumeMessageBatchMaxSize();

        if (StringUtils.isEmpty(groupName)) {
            throw new RocketMQException(ErrorCodeEnumConst.ROCKETMQ_CONSUME_FAIL);
        }
        if (StringUtils.isEmpty(namesrvAddr)) {

            throw new RocketMQException(ErrorCodeEnumConst.ROCKETMQ_CONSUME_FAIL);
        }
        if (StringUtils.isEmpty(topics)) {
            throw new RocketMQException(ErrorCodeEnumConst.ROCKETMQ_CONSUME_FAIL);
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.registerMessageListener(mqMessageListenerProcessor);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        //设置广播消费
//        subscriber.setMessageModel(MessageModel.BROADCASTING);
        //设置集群消费
        consumer.setMessageModel(MessageModel.CLUSTERING);
        if(!StringUtils.isEmpty(mqConsumerConfig.getInstanceName())){
            consumer.setInstanceName(mqConsumerConfig.getInstanceName());
        }
        /**
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(mqConsumerConfig.getConsumeMessageBatchMaxSize());
        try {
            /**
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
            String[] topicTagsArr = topics.split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("-");
                consumer.subscribe(topicTag[0], topicTag[1]);
            }
            consumer.start();
            LOGGER.info("subscriber is start !!! groupName:{},topics:{},namesrvAddr:{}", groupName, topics, namesrvAddr);
        } catch (MQClientException e) {
            LOGGER.error("subscriber is start !!! groupName:{},topics:{},namesrvAddr:{}", groupName, topics, namesrvAddr, e);
            throw new BusinessException();

        }
        return consumer;
    }
}