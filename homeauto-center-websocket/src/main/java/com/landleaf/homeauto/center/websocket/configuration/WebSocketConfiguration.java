package com.landleaf.homeauto.center.websocket.configuration;

import com.landleaf.homeauto.center.websocket.service.websocket.ConnectInterceptor;
import com.landleaf.homeauto.center.websocket.service.websocket.EchoMessageService;
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

    private EchoMessageService echoMessageService;

    private ConnectInterceptor connectInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(echoMessageService, "/echo/*")
                .addInterceptors(connectInterceptor)
                .setAllowedOrigins("*");
    }

    @Autowired
    public void setEchoMessageService(EchoMessageService echoMessageService) {
        this.echoMessageService = echoMessageService;
    }

    @Autowired
    public void setConnectInterceptor(ConnectInterceptor connectInterceptor) {
        this.connectInterceptor = connectInterceptor;
    }
}
