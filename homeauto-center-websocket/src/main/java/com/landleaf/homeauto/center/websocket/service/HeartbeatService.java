package com.landleaf.homeauto.center.websocket.service;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.constant.HeartbeatConstant;
import com.landleaf.homeauto.center.websocket.constant.MessageEnum;
import com.landleaf.homeauto.center.websocket.model.MessageModel;
import com.landleaf.homeauto.center.websocket.model.message.HeartbeatMessage;
import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/15
 */
@Slf4j
@Service
public class HeartbeatService {

    @Autowired
    private Map<String, Long> heartbeatMap;

    @Autowired
    private Map<String, String> sessionFamilyMap;

    @Autowired
    private Map<String, WebSocketSession> familySessionMap;

    public void beat(String sessionId, HeartbeatMessage heartbeatMessage) throws IOException {
        long currentTimeMillis = System.currentTimeMillis();
        String familyId = sessionFamilyMap.get(sessionId);
        WebSocketSession webSocketSession = familySessionMap.get(familyId);
        if (!Objects.equals(heartbeatMessage.getHeartbeat(), HeartbeatConstant.PING)) {
            // 非正常心跳消息
            if (!Objects.isNull(webSocketSession)) {
                webSocketSession.close(CloseStatus.SESSION_NOT_RELIABLE);
            }
        } else if (heartbeatMap.containsKey(familyId)) {
            heartbeatMap.replace(familyId, currentTimeMillis);
        } else {
            heartbeatMap.put(familyId, currentTimeMillis);
        }
        HeartbeatMessage heartbeatMessageReplay = new HeartbeatMessage(HeartbeatConstant.PONG);
        MessageModel messageModel = new MessageModel(MessageEnum.HEARTBEAT, heartbeatMessageReplay);
        PongMessage pongMessage = new PongMessage(ByteBuffer.wrap(JSON.toJSONBytes(messageModel)));
        webSocketSession.sendMessage(pongMessage);
    }

    /**
     * 发送心跳
     *
     * @param familyId 家庭ID
     * @throws IOException
     */
    public void beat(String familyId) throws IOException {
        WebSocketSession webSocketSession = familySessionMap.get(familyId);
        if (!Objects.isNull(webSocketSession)) {
            String sessionId = webSocketSession.getId();
            HeartbeatMessage heartbeatMessage = new HeartbeatMessage(HeartbeatConstant.PING);
            beat(sessionId, heartbeatMessage);
        }
    }
}
