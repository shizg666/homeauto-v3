package com.landleaf.homeauto.center.websocket.configuration;

import com.landleaf.homeauto.center.websocket.handler.EchoMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket配置
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    public EchoMessageHandler echoMessageHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(echoMessageHandler, "/echo")
                .addInterceptors(handshakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Autowired
    public void setEchoMessageHandler(EchoMessageHandler echoMessageHandler) {
        this.echoMessageHandler = echoMessageHandler;
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new HttpSessionHandshakeInterceptor();
    }

    @Bean("webSocketSessionHashMap")
    public ConcurrentHashMap<String, WebSocketSession> webSocketSessionConcurrentHashMap() {
        return new ConcurrentHashMap<>(128);
    }
}
