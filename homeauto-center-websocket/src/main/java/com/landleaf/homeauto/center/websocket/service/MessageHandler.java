package com.landleaf.homeauto.center.websocket.service;

import com.landleaf.homeauto.center.websocket.model.MessageModel;
import com.landleaf.homeauto.center.websocket.service.base.AbstractMessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 心跳消息处理
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Component
public class MessageHandler extends AbstractMessageHandler {

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, MessageModel message) {

    }
}
