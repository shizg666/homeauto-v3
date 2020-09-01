package com.landleaf.homeauto.contact.screen.controller.callback;

import com.landleaf.homeauto.common.util.StreamUtils;
import com.landleaf.homeauto.common.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName MqttWebHookCallbackController
 * @Description: mqtt 回调钩子
 * @Author wyl
 * @Date 2020/7/22
 * @Version V1.0
 **/
@RestController
@RequestMapping("/contact-screen/callback/mqtt")
@Slf4j
public class MqttWebHookCallbackController extends BaseController {


    @RequestMapping(value = "/web_hook", method = {RequestMethod.GET, RequestMethod.POST})
    public void webHookCallback(HttpServletRequest request) {
        try {
            byte[] body = StreamUtils.getByteByStream(request.getInputStream());
            String data = new String(body, StandardCharsets.UTF_8);
           log.info("[mqtt回调消息],{}",data);
        } catch (Exception e) {
            System.out.println("错误信息");
        }
    }
}
