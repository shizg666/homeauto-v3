package com.landleaf.homeauto.center.websocket.model;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.websocket.rocketmq.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/9/18
 */
@Slf4j
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
            List<WebSocketSession> webSocketSessions = FAMILY_SESSIONS_MAP.get(familyId);
           //清楚掉已有的已关闭的连接

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


    /**
     * 清楚已关闭的连接
     * @param familyId
     */
    public static void clearLink(String familyId){
        try {
            List<WebSocketSession> webSocketSessions = FAMILY_SESSIONS_MAP.get(familyId);
            if(CollectionUtils.isEmpty(webSocketSessions)){
                return;
            }
            List<WebSocketSession> openLinks = webSocketSessions.stream().filter(i -> i.isOpen()).collect(Collectors.toList());
            List<WebSocketSession> closeLinks = webSocketSessions.stream().filter(i -> !i.isOpen()).collect(Collectors.toList());
            webSocketSessions.clear();
            webSocketSessions.addAll(openLinks);
            if(!CollectionUtils.isEmpty(closeLinks)){
                for (WebSocketSession closeLink : closeLinks) {
                    InetSocketAddress remoteAddress = closeLink.getRemoteAddress();
                    log.info("连接已断开,我要干掉你了。地址:{},familyId:{},sessionId:{}", JSON.toJSONString(remoteAddress),familyId,closeLink.getId());
                    try {
                        closeLink.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
            }
        } catch (Exception e) {
            log.error("清理连接异常,装作不知道....");
        }

    }

}
