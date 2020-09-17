package com.landleaf.homeauto.center.websocket.service.base;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.constant.HeartbeatConstant;
import com.landleaf.homeauto.center.websocket.model.MessageModel;
import com.landleaf.homeauto.center.websocket.model.message.HeartbeatMessage;
import com.landleaf.homeauto.center.websocket.service.HeartbeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * WebSocketHandler抽象父类
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Slf4j
public abstract class AbstractMessageHandler implements WebSocketHandler {

    @Autowired
    private Map<String, WebSocketSession> familySessionMap;

    @Autowired
    private Map<String, String> sessionFamilyMap;

    @Autowired
    private HeartbeatService heartbeatService;

    // ------------------------------ 接口方法 ------------------------------

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String familyId = session.getAttributes().get("familyId").toString();
        sessionFamilyMap.put(session.getId(), familyId);
        familySessionMap.put(familyId, session);
        heartbeatService.beat(familyId);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            log.info("收到文本消息");
            this.handleTextMessage(session, (TextMessage) message);
        } else if (message instanceof BinaryMessage) {
            log.info("收到二进制消息");
            this.handleBinaryMessage(session, (BinaryMessage) message);
        } else if (message instanceof PingMessage) {
            log.info("收到心跳消息");
            this.handlePingMessage(session, (PingMessage) message);
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String familyId = sessionFamilyMap.get(session.getId());
        log.error("[{}] 家庭掉线", familyId);
        sessionFamilyMap.remove(session.getId());
        familySessionMap.remove(familyId);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    // ------------------------------ 本类方法 ------------------------------

    /**
     * 文本消息处理
     *
     * @param session WebSocket会话
     * @param message 文本消息
     * @throws Exception 异常
     */
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        MessageModel messageModel = JSON.parseObject(payload, MessageModel.class);
        this.handleTextMessage(session, messageModel);
    }

    /**
     * 处理二进制数据
     *
     * @param session
     * @param message
     * @throws Exception
     */
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        log.info("收到二进制消息");
    }

    /**
     * 处理心跳消息
     *
     * @param session
     * @param message
     * @throws Exception
     */
    protected void handlePingMessage(WebSocketSession session, PingMessage message) throws Exception {
        byte[] buff = new byte[message.getPayloadLength()];
        String sessionId = session.getId();
        message.getPayload().get(buff);
        String heartbeatMessageString = new String(buff, StandardCharsets.UTF_8);
        HeartbeatMessage heartbeatMessage = JSON.parseObject(heartbeatMessageString, HeartbeatMessage.class);
        if (Objects.equals(heartbeatMessage.getHeartbeat(), HeartbeatConstant.PING)) {
            String familyId = sessionFamilyMap.get(sessionId);
            heartbeatService.beat(familyId);
        }
    }

    // ------------------------------ 抽象方法 ------------------------------

    /**
     * 处理文本消息
     *
     * @param webSocketSession
     * @param message
     */
    protected abstract void handleTextMessage(WebSocketSession webSocketSession, MessageModel message);
}
