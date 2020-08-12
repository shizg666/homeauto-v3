package com.landleaf.homeauto.center.websocket.service.redis;

//import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于对WebSocketSession进行相关操作
 *
 * @author Yujiumin
 * @version 2020/8/12
 */
@Component
public class RedisServiceForWebSocketSession {

//    private RedisUtils redisUtils;
//
//    private static final String KEY = "homeauto:websocket:session";
//
//    /**
//     * 将WebSocket会话Id缓存如redis
//     *
//     * @param familyId  家庭ID
//     * @param sessionId Websocket会话ID
//     */
//    public void putSession(String familyId, String sessionId) {
//        redisUtils.addMap(KEY, familyId, sessionId);
//    }
//
//    /**
//     * 通过
//     *
//     * @param familyId
//     * @return
//     */
//    public String getSession(String familyId) {
//        return redisUtils.hget(KEY, familyId).toString();
//    }
//
//    @Autowired
//    public void setRedisUtils(RedisUtils redisUtils) {
//        this.redisUtils = redisUtils;
//    }

}
