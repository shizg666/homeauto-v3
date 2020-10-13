package com.landleaf.homeauto.center.websocket.model;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.websocket.rocketmq.util.CollectionUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yujiumin
 * @version 2020/9/18
 */
public class WebSocketSessionContext {

    private static final Map<String, List<WebSocketSession>> FAMILY_SESSIONS_MAP = new ConcurrentHashMap<>();

    /**
     * 添加家庭会话
     *
     * @param familyId         家庭ID
     * @param webSocketSession {@link WebSocketSession}
     */
    public static void put(String familyId, WebSocketSession webSocketSession) {
        if (FAMILY_SESSIONS_MAP.containsKey(familyId)) {
            // 因为没有心跳,控制下数量,每个家庭不能超过20个连接.防止未感知清除
            List<WebSocketSession> webSocketSessions = FAMILY_SESSIONS_MAP.get(familyId);
            if(webSocketSessions.size()>=20){
                webSocketSessions.remove(0);
            }
            webSocketSessions.add(webSocketSession);
        } else {
            FAMILY_SESSIONS_MAP.put(familyId, CollectionUtil.list(true, webSocketSession));
        }
    }

    /**
     * 获取家庭会话
     *
     * @param familyId 家庭ID
     * @return {@link WebSocketSession}
     */
    public static List<WebSocketSession> get(String familyId) {
        return FAMILY_SESSIONS_MAP.get(familyId);
    }

    /**
     * 移除会话
     *
     * @param webSocketSession 会话
     */
    public static void remove(WebSocketSession webSocketSession) {
        String familyId = webSocketSession.getAttributes().get("familyId").toString();
        List<WebSocketSession> webSocketSessionList = get(familyId);
        if(CollectionUtils.isEmpty(webSocketSessionList)){
            return;
        }
        for (int i = 0; i < webSocketSessionList.size(); i++) {
            WebSocketSession session = webSocketSessionList.get(i);
            String sessionId = session.getId();
            if (Objects.equals(sessionId, webSocketSession.getId())) {
                webSocketSessionList.remove(session);
            }
        }
    }

    public static Set<String> getFamilyIdList() {
        return FAMILY_SESSIONS_MAP.keySet();
    }

}
