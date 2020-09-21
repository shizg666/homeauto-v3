package com.landleaf.homeauto.center.websocket.configuration;

import com.landleaf.homeauto.center.websocket.service.ConnectInterceptor;
import com.landleaf.homeauto.common.constant.RedisChannelConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

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

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Autowired
    private MessageListener messageListener;

    @PostConstruct
    public void init() {
        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
        redisConnection.subscribe(messageListener, RedisChannelConst.WEBSOCKET_CHANNEL.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        WebSocketHandlerRegistration webSocketHandlerRegistration = webSocketHandlerRegistry.addHandler(webSocketHandler, "/endpoint/*");
        webSocketHandlerRegistration.addInterceptors(connectInterceptor);
        webSocketHandlerRegistration.setAllowedOrigins("*");
    }

}
