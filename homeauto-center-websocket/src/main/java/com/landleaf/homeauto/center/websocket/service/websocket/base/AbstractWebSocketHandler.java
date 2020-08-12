package com.landleaf.homeauto.center.websocket.service.websocket.base;

import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Collection;
import java.util.Map;

/**
 * WebSocketHandler抽象父类
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Component
public abstract class AbstractWebSocketHandler extends org.springframework.web.socket.handler.AbstractWebSocketHandler {

    private Map<String, WebSocketSession> webSocketSessionMap;

    private HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionMap.put(session.getId(), session);
        Collection<String> attributeNames = httpSessionHandshakeInterceptor.getAttributeNames();
        MessageUtils.sendTextMessage(session, "连接成功, session_id为:" + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    /**
     * 文本消息处理
     *
     * @param session WebSocket会话
     * @param message 文本消息
     * @throws Exception 异常
     */
    @Override
    protected abstract void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception;

    @Autowired
    public void setWebSocketSessionMap(Map<String, WebSocketSession> webSocketSessionMap) {
        this.webSocketSessionMap = webSocketSessionMap;
    }

    @Autowired
    public void setHttpSessionHandshakeInterceptor(HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor) {
        this.httpSessionHandshakeInterceptor = httpSessionHandshakeInterceptor;
    }
}
