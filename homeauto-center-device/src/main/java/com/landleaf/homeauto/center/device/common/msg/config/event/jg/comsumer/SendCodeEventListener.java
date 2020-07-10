package com.landleaf.homeauto.center.device.common.msg.config.event.jg.comsumer;

import com.google.common.eventbus.Subscribe;
import com.landleaf.homeauto.center.device.common.msg.cache.ShCodeRedisService;
import com.landleaf.homeauto.center.device.common.msg.converter.JsmsSendHistoryConverter;
import com.landleaf.homeauto.center.device.domain.msg.jg.SendCodeEvent;
import com.landleaf.homeauto.center.device.common.msg.config.event.jg.producer.SendCodeEventPublisher;
import com.landleaf.homeauto.center.device.service.msg.IJsmsSendHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送短信监听者
 */
@Component
public class SendCodeEventListener {

    @Autowired
    private ShCodeRedisService shCodeRedisService;

    @Autowired
    private IJsmsSendHistoryService jsmsSendHistoryService;

    @Autowired
    public SendCodeEventListener(SendCodeEventPublisher sendCodeEventPublisher) {
        sendCodeEventPublisher.register(this);
    }

    @Subscribe
    public void handleSendCodeEventByRedis(SendCodeEvent event) {
        if (event.redisFlag()) {
            shCodeRedisService.hsetCodeByMobile(
                    event.shSmsMsg().smsMsgType().getMsgType(),
                    event.shSmsMsg().mobile(),
                    event.shSmsMsg().code(),
                    (long) event.shSmsMsg().smsMsgType().getTtl());
        }
    }

    @Subscribe
    public void handleSendCodeEventByDb(SendCodeEvent event) {
        if (event.dbFlag()) {
            jsmsSendHistoryService.save(JsmsSendHistoryConverter
                    .serializeFromEvent(event));
        }
    }
}
