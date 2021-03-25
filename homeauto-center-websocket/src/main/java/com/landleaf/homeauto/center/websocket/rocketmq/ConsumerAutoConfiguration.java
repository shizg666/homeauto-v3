package com.landleaf.homeauto.center.websocket.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.landleaf.homeauto.center.websocket.rocketmq.annotation.Consumer;
import com.landleaf.homeauto.center.websocket.rocketmq.consumer.MessageHandler;
import com.landleaf.homeauto.center.websocket.rocketmq.exception.RocketMqException;
import com.landleaf.homeauto.center.websocket.rocketmq.properties.ConsumerConfigurationProperties;
import com.landleaf.homeauto.center.websocket.rocketmq.properties.RocketMqConfigurationProperties;
import com.landleaf.homeauto.center.websocket.rocketmq.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Configuration
public class ConsumerAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(ConsumerAutoConfiguration.class);

    @Autowired
    private ConsumerConfigurationProperties consumerConfigurationProperties;

    @Autowired
    private RocketMqConfigurationProperties rocketMqConfigurationProperties;

    @Autowired
    private List<MessageHandler> messageHandlerList;

    private Map<String, List<MessageHandler>> groupConsumerMap;

    private List<DefaultMQPushConsumer> pushConsumerList;

    public ConsumerAutoConfiguration() {
        groupConsumerMap = new LinkedHashMap<>();
        pushConsumerList = new LinkedList<>();
    }

    @PostConstruct
    public void init() {
        // 分组
        if (!CollectionUtils.isEmpty(messageHandlerList)) {
            for (MessageHandler messageHandler : messageHandlerList) {
                Consumer consumerAnnotation = messageHandler.getClass().getAnnotation(Consumer.class);
                if (Objects.isNull(consumerAnnotation)) {
                    String messageHandlerName = messageHandler.getClass().getName();
                    String consumerAnnotationName = Consumer.class.getName();
                    logger.warn("[{}] 类上没有使用 [{}] 注解标注", messageHandlerName, consumerAnnotationName);
                    continue;
                }
                String group = consumerAnnotation.group();
                if (groupConsumerMap.containsKey(group)) {
                    groupConsumerMap.get(group).add(messageHandler);
                } else {
                    groupConsumerMap.put(group, CollectionUtils.list(true, messageHandler));
                }
            }

            // 按组创建消费者
            for (String group : groupConsumerMap.keySet()) {
                // 过滤掉不需要存在的handler
                List<MessageHandler> messageHandlerList = groupConsumerMap.get(group);
                Map<String, Map<List<String>, MessageHandler>> messageHandlerMap = new LinkedHashMap<>();
                for (MessageHandler messageHandler : messageHandlerList) {
                    Consumer consumerAnnotation = messageHandler.getClass().getAnnotation(Consumer.class);
                    String topic = consumerAnnotation.topic();
                    List<String> tags = Arrays.asList(consumerAnnotation.tags());
                    if (messageHandlerMap.containsKey(topic)) {
                        Map<List<String>, MessageHandler> listMessageHandlerMap = messageHandlerMap.get(topic);
                        if (tags.contains("*")) {
                            tags = Collections.singletonList("*");
                            if (listMessageHandlerMap.containsKey(tags)) {
                                throw new RocketMqException("Topic[%s]下已经存在消费Tag[*]的消费者了", topic);
                            }
                            listMessageHandlerMap.clear();
                            listMessageHandlerMap.put(tags, messageHandler);
                        } else if (!listMessageHandlerMap.containsKey(Collections.singletonList("*"))) {
                            // 查是否有重复消费的tag
                            for (List<String> tagList : listMessageHandlerMap.keySet()) {
                                if (!CollectionUtils.isEmpty(CollectionUtils.intersection(tags, tagList))) {
                                    MessageHandler existHandler = listMessageHandlerMap.get(tagList);
                                    throw new RocketMqException("%s与%s消费tag重复", messageHandler.getClass().getName(), existHandler.getClass().getName());
                                }
                            }
                            listMessageHandlerMap.put(tags, messageHandler);
                        }
                    } else {
                        messageHandlerMap.put(topic, CollectionUtils.map(true, tags, messageHandler));
                    }
                }

                try {
                    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
                    consumer.setInstanceName("CONSUMER_" + group.toUpperCase());
                    consumer.setNamesrvAddr(rocketMqConfigurationProperties.getNameServerAddr());
                    consumer.setMessageModel(consumerConfigurationProperties.getMessageModel());
                    consumer.setConsumeFromWhere(consumerConfigurationProperties.getConsumeFromWhere());
                    consumer.setConsumeThreadMin(consumerConfigurationProperties.getConsumeThreadMin());
                    consumer.setConsumeThreadMax(consumerConfigurationProperties.getConsumeThreadMax());
                    consumer.registerMessageListener(new MessageDispatcher(messageHandlerList));
                    //设置广播消费
                    consumer.setMessageModel(MessageModel.BROADCASTING);
                    for (String topic : messageHandlerMap.keySet()) {
                        Map<List<String>, MessageHandler> listMessageHandlerMap = messageHandlerMap.get(topic);
                        List<String> topicTag = handleTopicTag(listMessageHandlerMap.keySet());
                        consumer.subscribe(topic, String.join("||", topicTag));
                        logger.info("消费者订阅 => Group:[{}], Topic:[{}], Tags:{}", group, topic, topicTag);
                    }

                    consumer.start();
                    pushConsumerList.add(consumer);
                    logger.info("消费者启动成功 => NameServer地址:[{}], Group:[{}]", rocketMqConfigurationProperties.getNameServerAddr(), group);
                } catch (MQClientException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @PreDestroy
    public void destroy() {
        for (DefaultMQPushConsumer consumer : pushConsumerList) {
            if (!Objects.isNull(consumer)) {
                consumer.shutdown();
            }
        }
    }

    private List<String> handleTopicTag(Set<List<String>> tags) {
        List<String> allTagList = new LinkedList<>();
        for (List<String> tagList : tags) {
            allTagList.addAll(tagList);
        }
        return allTagList;
    }

}
