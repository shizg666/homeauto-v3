package com.landleaf.homeauto.center.websocket.service.base;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.model.AppMessage;
import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

/**
 * WebSocketHandler抽象父类
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Slf4j
public abstract class AbstractMessageHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String familyId = session.getAttributes().get("familyId").toString();
        WebSocketSessionContext.put(familyId, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage webSocketMessage) throws Exception {
        if (webSocketMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) webSocketMessage;
            String payload = textMessage.getPayload();
            AppMessage appMessageModel = JSON.parseObject(payload, AppMessage.class);
            this.handleTextMessage(session, appMessageModel);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
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
     * @param appMessage       内部消息
     */
    protected void handleTextMessage(WebSocketSession webSocketSession, AppMessage appMessage) {

    }
}
