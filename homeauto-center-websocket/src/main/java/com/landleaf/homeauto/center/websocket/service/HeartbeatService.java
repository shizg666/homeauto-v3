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
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private Map<String, WebSocketSession> webSocketSessionMap;

    public void beat(String sessionId, Object object) throws IOException {
        long currentTimeMillis = System.currentTimeMillis();
        String familyId = sessionFamilyMap.get(sessionId);
        HeartbeatMessage heartbeatMessage = JSON.parseObject(Objects.toString(object), HeartbeatMessage.class);
        WebSocketSession webSocketSession = webSocketSessionMap.get(familyId);
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
        HeartbeatMessage heartbeatMessageReplay = new HeartbeatMessage();
        heartbeatMessageReplay.setHeartbeat(HeartbeatConstant.PONG);
        MessageModel messageModel = new MessageModel(MessageEnum.HEARTBEAT, heartbeatMessageReplay);
        MessageUtils.sendMessage(webSocketSession, messageModel);
    }
}
