package com.landleaf.homeauto.center.websocket.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.websocket.rocketmq.util.CollectionUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.yeauty.pojo.Session;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/9/18
 */
@Slf4j
public class WebSocketSessionContext {

    private static final Map<String, Map<String, Session>> FAMILY_SESSIONS_MAP = new ConcurrentHashMap<>();

    /**
     * 添加家庭会话
     *
     * @param familyId
     * @param channelId
     * @param session
     */
    public static void put(String familyId, String channelId, Session session) {
        if (FAMILY_SESSIONS_MAP.containsKey(familyId)) {
            FAMILY_SESSIONS_MAP.get(familyId).put(channelId, session);
        } else {
            Map<String, Session> sessionMap = Maps.newHashMap();
            sessionMap.put(channelId, session);
            FAMILY_SESSIONS_MAP.put(familyId, sessionMap);
        }
    }

    /**
     * 获取家庭会话
     *
     * @param familyId 家庭ID
     */
    public static Map<String, Session> get(String familyId) {
        return FAMILY_SESSIONS_MAP.get(familyId);
    }

    /**
     * 获取家庭会话
     *
     * @param familyId
     * @param channelId
     * @return
     */
    public static Session get(String familyId, String channelId) {
        Map<String, Session> sessionMap = FAMILY_SESSIONS_MAP.get(familyId);
        if (sessionMap == null) {
            return null;
        }
        return sessionMap.get(channelId);
    }

    /**
     * 移除会话
     *
     * @param familyId
     * @param session
     */
    public static void remove(String familyId, Session session) {

        if (StringUtil.isEmpty(familyId)) {
            return;
        }
        Map<String, Session> sessionMap = get(familyId);
        if (sessionMap == null || sessionMap.size() == 0) {
            return;
        }
        sessionMap.remove(session.id().asLongText());
        try {
            if(sessionMap.size()<=0){
                FAMILY_SESSIONS_MAP.remove(familyId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getFamilyIdList() {
        return FAMILY_SESSIONS_MAP.keySet();
    }


    /**
     * 清楚家庭已关闭的连接
     *
     * @param familyId
     */
    public static void clearLink(String familyId) {
        try {
            Map<String, Session> sessionMap = FAMILY_SESSIONS_MAP.get(familyId);
            if (CollectionUtils.isEmpty(sessionMap)) {
                return;
            }
            List<Session> sessions = Lists.newArrayList();
            sessionMap.forEach((i, v) -> {
                sessions.add(v);
            });

            List<Session> openLinks = sessions.stream().filter(i -> {
                Channel channel = i.channel();
                return channel.isOpen()&&channel.isActive();
            }).collect(Collectors.toList());
            List<Session> closeLinks = sessions.stream().filter(i -> {
                Channel channel = i.channel();
                return !channel.isOpen()||!channel.isActive();
            }).collect(Collectors.toList());
            sessions.clear();
            sessions.addAll(openLinks);
            if (!CollectionUtils.isEmpty(sessions)) {
                Map<String, Session> newSessionMap = sessions.stream().collect(Collectors.toMap(k -> {
                    return k.id().asLongText();
                }, v -> {
                    return v;
                }));
                sessionMap.clear();
                sessionMap.putAll(newSessionMap);

            }
            if (!CollectionUtils.isEmpty(closeLinks)) {
                for (Session closeLink : closeLinks) {
                    try {
                        closeLink.close();
                    } catch (Exception e) {
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
