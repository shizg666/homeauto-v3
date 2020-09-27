package com.landleaf.homeauto.center.websocket.model;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yujiumin
 * @version 2020/9/18
 */
public class WebSocketSessionContext {

    private static final Map<String, String> SESSION_FAMILY_MAP = new ConcurrentHashMap<>();

    private static final Map<String, WebSocketSession> FAMILY_SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 添加家庭会话
     *
     * @param familyId         家庭ID
     * @param webSocketSession {@link WebSocketSession}
     */
    public static void put(String familyId, WebSocketSession webSocketSession) {
        String sessionId = webSocketSession.getId();
        SESSION_FAMILY_MAP.put(sessionId, familyId);
        FAMILY_SESSION_MAP.put(familyId, webSocketSession);
    }

    /**
     * 获取家庭会话
     *
     * @param familyId 家庭ID
     * @return {@link WebSocketSession}
     */
    public static WebSocketSession get(String familyId) {
        return FAMILY_SESSION_MAP.get(familyId);
    }

    /**
     * 移除会话
     *
     * @param webSocketSession 会话
     */
    public static void remove(WebSocketSession webSocketSession) {
        String sessionId = webSocketSession.getId();
        String familyId = SESSION_FAMILY_MAP.get(sessionId);
        if (!Objects.isNull(familyId)) {
            SESSION_FAMILY_MAP.remove(sessionId);
            FAMILY_SESSION_MAP.remove(familyId);
        }
    }

    public static Set<String> getFamilyIdList() {
        return FAMILY_SESSION_MAP.keySet();
    }

}
