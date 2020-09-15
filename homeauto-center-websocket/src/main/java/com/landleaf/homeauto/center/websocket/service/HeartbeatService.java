package com.landleaf.homeauto.center.websocket.service;

import com.alibaba.fastjson.JSON;
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
        String familyId = sessionFamilyMap.get(sessionId);
        HeartbeatMessage heartbeatMessage = JSON.parseObject(Objects.toString(object), HeartbeatMessage.class);
        if (System.currentTimeMillis() < heartbeatMessage.getTimestamp()) {
            // 穿越来的? 干掉!
            log.info("当前北京时间: {}", getDateString(new Date()));
            log.error("WebSocket客户端心跳时间: {}", getDateString(new Date(heartbeatMessage.getTimestamp())));
            log.error("{} 心跳来自未来,干掉!!!", familyId);
            webSocketSessionMap.get(familyId).close(CloseStatus.SESSION_NOT_RELIABLE);
            return;
        }
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

    private String getDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
