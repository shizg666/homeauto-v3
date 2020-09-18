package com.landleaf.homeauto.center.websocket.service.base;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.model.MessageModel;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import io.micrometer.core.lang.NonNullApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.util.Map;

/**
 * WebSocketHandler抽象父类
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Slf4j
@NonNullApi
public abstract class AbstractMessageHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String familyId = session.getAttributes().get("familyId").toString();
        WebSocketSessionContext.put(familyId, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String payload = textMessage.getPayload();
            MessageModel messageModel = JSON.parseObject(payload, MessageModel.class);
            this.handleTextMessage(session, messageModel);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        WebSocketSessionContext.remove(session);
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSessionContext.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 处理文本消息
     *
     * @param webSocketSession websocket会话
     * @param message          内部消息
     */
    protected void handleTextMessage(WebSocketSession webSocketSession, MessageModel message) {

    }
}
