package com.landleaf.homeauto.center.websocket.feign;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 网关服务
 *
 * @author Yujiumin
 * @version 2020/9/4
 */
@FeignClient(ServerNameConst.HOMEAUTO_CENTER_GATEWAY)
public interface GateWayRemote {

    @GetMapping("/token/extract")
    public Response<Boolean> checkTokenLedge(@RequestParam String token);



}
