package com.landleaf.homeauto.center.websocket.service.rocketmq;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;

/**
 * @author Yujiumin
 * @version 2020/8/12
 */
@Component
public class ConsumeService implements MessageListenerConcurrently {

    private Map<String, WebSocketSession> webSocketSessionMap;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        return null;
    }

    @Autowired
    public void setWebSocketSessionMap(Map<String, WebSocketSession> webSocketSessionMap) {
        this.webSocketSessionMap = webSocketSessionMap;
    }
}
