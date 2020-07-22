package com.landleaf.homeauto.center.device.web.callback;

import com.landleaf.homeauto.common.config.http.StreamUtils;
import com.landleaf.homeauto.common.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName MqttWebHookCallbackController
 * @Description: mqtt 回调钩子
 * @Author wyl
 * @Date 2020/7/22
 * @Version V1.0
 **/
@RestController
@RequestMapping("/callback/mqtt")
public class MqttWebHookCallbackController extends BaseController {

    @RequestMapping(value = "/web_hook", method = {RequestMethod.GET, RequestMethod.POST})
    public void webHookCallbacke(HttpServletRequest request) {
        try {
            byte[] body = StreamUtils.getByteByStream(request.getInputStream());
            String data = new String(body, "utf-8");
            System.out.println(data);
        } catch (Exception e) {
            System.out.println("错误信息");
        }
    }
}
