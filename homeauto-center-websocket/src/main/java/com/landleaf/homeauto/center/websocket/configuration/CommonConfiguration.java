package com.landleaf.homeauto.center.websocket.configuration;

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

    @Bean("webSocketSessionHashMap")
    public ConcurrentHashMap<String, WebSocketSession> webSocketSessionConcurrentHashMap() {
        return new ConcurrentHashMap<>(128);
    }

}
