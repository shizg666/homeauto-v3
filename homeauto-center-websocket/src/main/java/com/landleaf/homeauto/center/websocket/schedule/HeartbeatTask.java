package com.landleaf.homeauto.center.websocket.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

/**
 * @author Yujiumin
 * @version 2020/9/15
 */
@Component
public class HeartbeatTask {

    @Autowired
    private Map<String, Long> familyHeartbeatMap;

    private static final Integer MAX_HEARTBEAT_TIME = 1000 * 60 * 3;

    @Autowired
    private Map<String, WebSocketSession> webSocketSessionMap;

    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 1000 * 30)
    public void check() throws IOException {
        for (String familyId : webSocketSessionMap.keySet()) {
            WebSocketSession webSocketSession = webSocketSessionMap.get(familyId);
            boolean isHeartbeat = familyHeartbeatMap.containsKey(familyId) && System.currentTimeMillis() - familyHeartbeatMap.get(familyId) < MAX_HEARTBEAT_TIME;
            if (!isHeartbeat) {
                webSocketSession.close();
            }
        }
    }

}
