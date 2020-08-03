package com.landleaf.homeauto.center.device.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;

/**
 * WebSocket配置
 *
 * @author Yujiumin
 * @version 2020/8/3
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends WebSocketMessageBrokerConfigurationSupport {

    @Override
    protected void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("").withSockJS();
    }

    @Override
    protected void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("");
    }
}
