package com.landleaf.homeauto.center.websocket.service;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.model.bo.HeartbeatBO;
import com.landleaf.homeauto.center.websocket.model.message.HeartbeatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/15
 */
@Service
public class HeartbeatService {

    private Map<String, HeartbeatBO> heartbeatMap;

    private Map<String, String> sessionFamilyMap;

    public void beat(String sessionId, Object object) {
        String familyId = sessionFamilyMap.get(sessionId);
        HeartbeatMessage heartbeatMessage = JSON.parseObject(Objects.toString(object), HeartbeatMessage.class);
        HeartbeatBO heartbeatBO = new HeartbeatBO();
        heartbeatBO.setTimes(0);
        heartbeatBO.setTimestamp(heartbeatMessage.getTimestamp());
        if (heartbeatMap.containsKey(familyId)) {
            heartbeatMap.replace(familyId, heartbeatBO);
        } else {
            heartbeatMap.put(familyId, heartbeatBO);
        }
    }

    @Autowired
    public void setHeartbeatMap(Map<String, HeartbeatBO> heartbeatMap) {
        this.heartbeatMap = heartbeatMap;
    }

    @Autowired
    public void setSessionFamilyMap(Map<String, String> sessionFamilyMap) {
        this.sessionFamilyMap = sessionFamilyMap;
    }
}
