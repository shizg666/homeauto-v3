package com.landleaf.homeauto.center.websocket.configuration;

import com.landleaf.homeauto.center.websocket.model.bo.HeartbeatBO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yujiumin
 * @version 2020/8/12
 */
@Configuration
public class CommonConfiguration {

    /**
     * KEY: familyId
     * VALUE: WebSocketSession
     *
     * @return ConcurrentHashMap<String, WebSocketSession>
     */
    @Bean
    public ConcurrentHashMap<String, WebSocketSession> webSocketSessionMap() {
        return new ConcurrentHashMap<>(128);
    }

    /**
     * KEY: sessionId
     * VALUE: familyId
     *
     * @return ConcurrentHashMap<String, String>
     */
    @Bean
    public ConcurrentHashMap<String, String> familySessionMap() {
        return new ConcurrentHashMap<>(128);
    }

    /**
     * KEY: familyId
     * VALUE: 时间戳
     *
     * @return ConcurrentHashMap<String, Long>
     */
    @Bean
    public ConcurrentHashMap<String, HeartbeatBO> familyHeartbeatMap() {
        return new ConcurrentHashMap<>(128);
    }
}
