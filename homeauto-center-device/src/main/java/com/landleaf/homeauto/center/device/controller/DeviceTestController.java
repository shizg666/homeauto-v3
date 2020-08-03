package com.landleaf.homeauto.center.device.controller;

import com.google.common.collect.Maps;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 测试oauth2访问权限
 *
 * @author wenyilu
 * @since 2020-06-18
 */
@RestController
@SuppressWarnings("all")
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

}
