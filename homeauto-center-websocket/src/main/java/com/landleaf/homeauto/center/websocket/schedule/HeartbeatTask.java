package com.landleaf.homeauto.center.websocket.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
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

    private static final Integer MAX_HEARTBEAT_TIME = 1000 * 60;

    @Autowired
    private Map<String, WebSocketSession> webSocketSessionMap;

//    @Scheduled(fixedDelay = 15 * 1000)
    public void check() throws IOException {
        for (String familyId : webSocketSessionMap.keySet()) {
            WebSocketSession webSocketSession = webSocketSessionMap.get(familyId);
            if (familyHeartbeatMap.containsKey(familyId)) {
                long time = System.currentTimeMillis() - familyHeartbeatMap.get(familyId);
                boolean isHeartbeat = 0 < time && time < MAX_HEARTBEAT_TIME;
                if (isHeartbeat) {
                    continue;
                }
            }
            webSocketSession.close(CloseStatus.SESSION_NOT_RELIABLE);
        }
    }

}
