package com.landleaf.homeauto.center.websocket.service;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.model.WebSocketMessage;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import com.landleaf.homeauto.common.domain.websocket.MessageModel;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/21
 */
@Component
public class RedisMessageListener implements org.springframework.data.redis.connection.MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
            String bodyString = new String(message.getBody(), StandardCharsets.UTF_8);
            MessageModel messageModel = JSON.parseObject(bodyString, MessageModel.class);
            String familyId = messageModel.getFamilyId();
            WebSocketSession webSocketSession = WebSocketSessionContext.get(familyId);
            if (!Objects.isNull(webSocketSession)) {
                WebSocketMessage webSocketMessage = new WebSocketMessage();
                webSocketMessage.setMessageCode(messageModel.getMessageCode());
                webSocketMessage.setMessage(messageModel.getMessage());
                webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(webSocketMessage)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
