package com.landleaf.homeauto.center.websocket.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.landleaf.homeauto.center.websocket.rocketmq.annotation.Consumer;
import com.landleaf.homeauto.center.websocket.rocketmq.consumer.MessageHandler;
import com.landleaf.homeauto.center.websocket.rocketmq.exception.RocketMqException;
import com.landleaf.homeauto.center.websocket.rocketmq.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 消息分发器
 *
 * @author Yujiumin
 * @version 2020/9/7
 */
class MessageDispatcher implements MessageListenerConcurrently {

    private Logger log = LoggerFactory.getLogger(MessageDispatcher.class);

    private Map<String, Map<List<String>, MessageHandler>> messageHandlerMap;

    private MessageDispatcher() {
        messageHandlerMap = new LinkedHashMap<>();
    }

    public MessageDispatcher(List<MessageHandler> messageHandlerList) {
        this();
        for (MessageHandler messageHandler : messageHandlerList) {
            Consumer consumerAnnotation = messageHandler.getClass().getAnnotation(Consumer.class);
            String topic = consumerAnnotation.topic();
            List<String> tagList = Arrays.asList(consumerAnnotation.tags());
            boolean consumeAnyTagFlag = false;
            if (tagList.contains("*")) {
                tagList = Collections.singletonList("*");
                consumeAnyTagFlag = true;
            }
            if (messageHandlerMap.containsKey(topic)) {
                // 已经有了消费该topic的消费者
                Map<List<String>, MessageHandler> listMessageHandlerMap = messageHandlerMap.get(topic);
                if (listMessageHandlerMap.containsKey(Collections.singletonList("*")) && consumeAnyTagFlag) {
                    // 已经有了消费所有tag的消费者 && 该消费者要消费所有的tag
                    throw new RocketMqException("%s下已有消费任何TAG的消费者", topic);
                } else if (consumeAnyTagFlag) {
                    // 没有消费任何tag的消费者 && 该消费者要消费所有tag
                    listMessageHandlerMap.clear();
                    listMessageHandlerMap.put(tagList, messageHandler);
                } else {
                    // 没有消费所有tag的消费者 && 该消费者不消费所有的tag
                    listMessageHandlerMap.put(tagList, messageHandler);
                }
            } else {
                // 将消费该topic的消费者添加进去
                messageHandlerMap.put(topic, CollectionUtils.map(true, tagList, messageHandler));
            }
        }
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        Map<String, List<MessageExt>> messageExtMap = list.stream().collect(Collectors.groupingBy(MessageExt::getTopic));
        for (String topic : messageExtMap.keySet()) {
            List<MessageExt> messageExtList = messageExtMap.get(topic);
            Map<List<String>, MessageHandler> listMessageHandlerMap = messageHandlerMap.get(topic);
            if (!CollectionUtils.isEmpty(listMessageHandlerMap)) {
                for (MessageExt messageExt : messageExtList) {
                    String tags = messageExt.getTags();
                    if (!Objects.isNull(tags)) {
                        List<String> messageTagList = Arrays.asList(tags.split("\\|\\|"));
                        if (Objects.equals(tags, "*")) {
                            MessageHandler messageHandler = listMessageHandlerMap.get(CollectionUtils.list(true, "*"));
                            if (Objects.isNull(messageHandler)) {
//                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                            }
                            return messageHandler.consumeMessage(messageExt);
                        } else {
                            for (List<String> messageHandlerTagList : listMessageHandlerMap.keySet()) {
                                boolean consumeAllTag = messageHandlerTagList.contains("*");
                                boolean tagWithIntersection = !CollectionUtils.isEmpty(CollectionUtils.intersection(messageTagList, messageHandlerTagList));
                                if (consumeAllTag || tagWithIntersection) {
                                    MessageHandler messageHandler = listMessageHandlerMap.get(messageHandlerTagList);
                                    return messageHandler.consumeMessage(messageExt);
                                }
                            }
                        }
                    } else {
                        // 不消费
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }
            } else {
                log.info("不存在消费[Topic:{}]的消费者", topic);
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
