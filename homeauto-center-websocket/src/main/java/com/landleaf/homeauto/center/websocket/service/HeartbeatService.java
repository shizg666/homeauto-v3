package com.landleaf.homeauto.center.websocket.service;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.constant.MessageEnum;
import com.landleaf.homeauto.center.websocket.model.MessageModel;
import com.landleaf.homeauto.center.websocket.model.message.HeartbeatMessage;
import com.landleaf.homeauto.center.websocket.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/15
 */
@Service
public class HeartbeatService {

    @Autowired
    private Map<String, Long> heartbeatMap;

    @Autowired
    private Map<String, String> sessionFamilyMap;

    @Autowired
    private Map<String, WebSocketSession> webSocketSessionMap;

    public void beat(String sessionId, Object object) {
        String familyId = sessionFamilyMap.get(sessionId);
        HeartbeatMessage heartbeatMessage = JSON.parseObject(Objects.toString(object), HeartbeatMessage.class);
        if (heartbeatMap.containsKey(familyId)) {
            heartbeatMap.replace(familyId, heartbeatMessage.getTimestamp());
        } else {
            heartbeatMap.put(familyId, heartbeatMessage.getTimestamp());
        }
        HeartbeatMessage heartbeatMessageReplay = new HeartbeatMessage();
        heartbeatMessageReplay.setTimestamp(System.currentTimeMillis());
        MessageModel messageModel = new MessageModel(MessageEnum.HEARTBEAT, heartbeatMessageReplay);
        MessageUtils.sendMessage(webSocketSessionMap.get(familyId), messageModel);
    }
}
