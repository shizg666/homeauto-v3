package com.landleaf.homeauto.center.websocket.service;

import com.landleaf.homeauto.center.websocket.feign.FamilyFeignService;
import io.micrometer.core.lang.NonNullApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 连接拦截器
 *
 * @author Yujiumin
 * @version 2020/8/12
 */
@Component
@NonNullApi
public class ConnectInterceptor implements HandshakeInterceptor {

    @Autowired
    private FamilyFeignService familyFeignService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        String path = serverHttpRequest.getURI().getPath();
        String[] pathSplit = path.split("/");
        if (!Objects.equals(pathSplit.length, 4)) {
            return false;
        }
        String familyId = pathSplit[3];
        map.put("familyId", familyId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
