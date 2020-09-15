package com.landleaf.homeauto.center.websocket.service;

import com.landleaf.homeauto.center.websocket.constant.MessageEnum;
import com.landleaf.homeauto.center.websocket.model.MessageModel;
import com.landleaf.homeauto.center.websocket.model.bo.HeartbeatBO;
import com.landleaf.homeauto.center.websocket.model.message.HeartbeatMessage;
import com.landleaf.homeauto.center.websocket.service.base.AbstractMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

/**
 * 心跳消息处理
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Component
public class MessageHandler extends AbstractMessageHandler {

    @Autowired
    private HeartbeatService heartbeatService;

    @Override
    protected void handleMessage(WebSocketSession webSocketSession, MessageModel message) {
        String sessionId = webSocketSession.getId();
        if (Objects.equals(message.getMessageCode(), MessageEnum.HEARTBEAT.code())) {
            heartbeatService.beat(sessionId, message.getMessage());
        }
    }
}
