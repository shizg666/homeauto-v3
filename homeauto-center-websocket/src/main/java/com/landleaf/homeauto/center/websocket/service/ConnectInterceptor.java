package com.landleaf.homeauto.center.websocket.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.landleaf.homeauto.common.constant.CommonConst;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 连接拦截器
 *
 * @author Yujiumin
 * @version 2020/8/12
 */
@Component
public class ConnectInterceptor implements HandshakeInterceptor {

    private static final String SECRET = "LANDLEAF-HOMEAUTO";

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        HttpHeaders headers = serverHttpRequest.getHeaders();
        String path = serverHttpRequest.getURI().getPath();
        int index = path.lastIndexOf('/');
        String familyId = path.substring(index + 1);
//        if (headers.containsKey(CommonConst.AUTHORIZATION)) {
//            String authorization = headers.getFirst(CommonConst.AUTHORIZATION);
//            String s = DigestUtil.md5Hex(SECRET);
//            if (DigestUtil.md5Hex(SECRET).equalsIgnoreCase(authorization)) {
//                map.put("familyId", familyId);
//                return true;
//            }
//        }
//        return false;
        map.put("familyId", familyId);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
