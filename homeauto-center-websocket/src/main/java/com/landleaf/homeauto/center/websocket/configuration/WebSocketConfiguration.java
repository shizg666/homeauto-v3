package com.landleaf.homeauto.center.websocket.configuration;

import com.landleaf.homeauto.center.websocket.service.ConnectInterceptor;
import com.landleaf.homeauto.center.websocket.service.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置
 *
 * @author Yujiumin
 * @version 2020/8/7
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Autowired
    private ConnectInterceptor connectInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        WebSocketHandlerRegistration webSocketHandlerRegistration = webSocketHandlerRegistry.addHandler(webSocketHandler, "/connect/*");
        webSocketHandlerRegistration.addInterceptors(connectInterceptor);
        webSocketHandlerRegistration.setAllowedOrigins("*");
    }

}
