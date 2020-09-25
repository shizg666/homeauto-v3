package com.landleaf.homeauto.center.websocket.service;

import com.landleaf.homeauto.center.websocket.feign.FamilyFeignService;
import com.landleaf.homeauto.center.websocket.feign.GateWayRemote;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FamilyFeignService familyFeignService;

    @Autowired
    private GateWayRemote gateWayRemote;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        HttpHeaders headers = serverHttpRequest.getHeaders();
        if (headers.containsKey(CommonConst.AUTHORIZATION)) {
            String authorization = headers.getFirst(CommonConst.AUTHORIZATION);
            Response<Boolean> booleanResponse = gateWayRemote.checkTokenLedge(authorization);
            if (booleanResponse.getResult()) {
                String path = serverHttpRequest.getURI().getPath();
                int index = path.lastIndexOf('/');
                String familyId = path.substring(index + 1);
                if (familyFeignService.familyExist(familyId).getResult()) {
                    map.put("familyId", familyId);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
