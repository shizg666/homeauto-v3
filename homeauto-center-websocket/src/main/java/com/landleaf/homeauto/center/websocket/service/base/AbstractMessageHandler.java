package com.landleaf.homeauto.center.websocket.service.base;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.model.MessageModel;
import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;

/**
 * WebSocketHandler抽象父类
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Component
public abstract class AbstractMessageHandler extends AbstractWebSocketHandler {

    private Map<String, WebSocketSession> familySessionMap;

    private Map<String, String> sessionIdFamilyMap;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String familyId = session.getAttributes().get("familyId").toString();
        sessionIdFamilyMap.put(session.getId(), familyId);
        familySessionMap.put(familyId, session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String familyId = sessionIdFamilyMap.get(session.getId());
        sessionIdFamilyMap.remove(session.getId());
        familySessionMap.remove(familyId);
    }

    /**
     * 文本消息处理
     *
     * @param session WebSocket会话
     * @param message 文本消息
     * @throws Exception 异常
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        MessageModel messageModel = JSON.parseObject(payload, MessageModel.class);
        handleMessage(session, messageModel);
    }

    /**
     * @param webSocketSession
     * @param message
     */
    protected abstract void handleMessage(WebSocketSession webSocketSession, MessageModel message);

    @Autowired
    public void setFamilySessionMap(Map<String, WebSocketSession> familySessionMap) {
        this.familySessionMap = familySessionMap;
    }

    @Autowired
    public void setSessionIdFamilyMap(Map<String, String> sessionIdFamilyMap) {
        this.sessionIdFamilyMap = sessionIdFamilyMap;
    }
}
