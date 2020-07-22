package com.landleaf.homeauto.center.device.eventbus.subscriber;

import com.google.common.eventbus.Subscribe;
import com.landleaf.homeauto.center.device.eventbus.converter.JsmsSendHistoryConverter;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForSmartHomeCode;
import com.landleaf.homeauto.center.device.eventbus.event.SendCodeEvent;
import com.landleaf.homeauto.center.device.eventbus.publisher.SendCodeEventPublisher;
import com.landleaf.homeauto.center.device.service.mybatis.IJSmsSendHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送短信监听者
 */
@Component
public class SendCodeEventSubscriber {

    @Autowired
    private RedisServiceForSmartHomeCode redisServiceForSmartHomeCode;

    @Autowired
    private IJSmsSendHistoryService jsmsSendHistoryService;

    @Autowired
    public SendCodeEventSubscriber(SendCodeEventPublisher sendCodeEventPublisher) {
        sendCodeEventPublisher.register(this);
    }

    @Subscribe
    public void handleSendCodeEventByRedis(SendCodeEvent event) {
        if (event.redisFlag()) {
            redisServiceForSmartHomeCode.hsetCodeByMobile(
                    event.shSmsMsg().smsMsgType().getMsgType(),
                    event.shSmsMsg().mobile(),
                    event.shSmsMsg().code(),
                    (long) event.shSmsMsg().smsMsgType().getTtl());
        }
    }

    @Subscribe
    public void handleSendCodeEventByDb(SendCodeEvent event) {
        if (event.dbFlag()) {
            jsmsSendHistoryService.save(JsmsSendHistoryConverter.serializeFromEvent(event));
        }
    }
}
