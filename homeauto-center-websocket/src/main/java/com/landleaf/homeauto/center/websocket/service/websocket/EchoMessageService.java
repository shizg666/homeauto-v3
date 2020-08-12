package com.landleaf.homeauto.center.websocket.service.websocket;

import com.landleaf.homeauto.center.websocket.service.websocket.base.AbstractWebSocketHandler;
import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Echo消息处理
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Component
public class EchoMessageService extends AbstractWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        MessageUtils.sendTextMessage(session, payload);
    }
}
