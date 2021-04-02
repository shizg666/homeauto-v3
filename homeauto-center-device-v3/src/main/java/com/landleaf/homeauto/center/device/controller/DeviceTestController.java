package com.landleaf.homeauto.center.device.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.remote.WebSocketRemote;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.DeviceStatusDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 测试oauth2访问权限
 *
 * @author wenyilu
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/device")
public class DeviceTestController extends BaseController {

    @PostMapping("/test/visit")
    public Response<Map<String, Object>> visit() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("name", "白名单访问");
        return returnSuccess(data);
    }

    @PostMapping("/test1/visit")
    public Response<Map<String, Object>> visit1() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("name", "登录访问");
        return returnSuccess(data);
    }

    @PostMapping("/test2/visit")
    public Response<Map<String, Object>> visit2() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("name", "有query2权限访问");
        return returnSuccess(data);
    }

    @PostMapping("/test3/visit")
    public Response<Map<String, Object>> visit3() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("name", "有query2权限访问");
        return returnSuccess(data);
    }

    @Autowired
    private UserRemote userRemote;


    @GetMapping("/test/feign")
    public void testFeign() {
        List<String> userIds = Lists.newArrayList();
        userIds.add("7600d97a03d34f1c9a6cfc6735143ef9");
        Response<List<HomeAutoCustomerDTO>> listByIds = userRemote.getListByIds(userIds);
        return;
    }
    @Autowired
    private WebSocketRemote webSocketRemote;
    @GetMapping("/test/websocket")
    public void testWebsocket() {
        DeviceStatusDTO deviceStatusDTO= new DeviceStatusDTO();
        deviceStatusDTO.setCategory("123");
        webSocketRemote.push(deviceStatusDTO);
        return;
    }
}
