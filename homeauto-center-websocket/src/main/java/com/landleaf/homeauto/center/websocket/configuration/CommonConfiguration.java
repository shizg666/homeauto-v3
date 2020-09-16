package com.landleaf.homeauto.center.websocket.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
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
    public ConcurrentHashMap<String, Long> familyHeartbeatMap() {
        return new ConcurrentHashMap<>(128);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
