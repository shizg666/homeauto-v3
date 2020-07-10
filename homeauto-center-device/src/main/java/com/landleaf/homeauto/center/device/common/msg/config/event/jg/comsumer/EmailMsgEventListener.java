package com.landleaf.homeauto.center.device.common.msg.config.event.jg.comsumer;

import com.google.common.eventbus.Subscribe;
import com.landleaf.homeauto.center.device.common.msg.cache.EmailMsgRedisService;
import com.landleaf.homeauto.center.device.common.msg.converter.EmailSendHistoryConverter;
import com.landleaf.homeauto.center.device.domain.msg.email.EmailMsgEvent;
import com.landleaf.homeauto.center.device.common.msg.config.event.jg.producer.EmailMsgEventPublisher;
import com.landleaf.homeauto.center.device.service.msg.IEmailSendHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class EmailMsgEventListener {

    @Autowired
    private EmailMsgRedisService emailMsgRedisService;

    @Autowired
    private IEmailSendHistoryService emailSendHistoryService;

    @Autowired
    public EmailMsgEventListener(EmailMsgEventPublisher emailMsgEventPublisher) {
        emailMsgEventPublisher.register(this);
    }


    /**
     * redis处理发送验证码
     *
     * @param emailMsgEvent
     */
    @Subscribe
    public void handleEmailSendCodeEventByRedis(EmailMsgEvent emailMsgEvent) {
        //只要ttl不是-1的  就存redis
        if (emailMsgEvent.emailMsg().emailMsgType().getTtl() > 0) {
            emailMsgRedisService.hsetCodeByEmail(
                    emailMsgEvent.emailMsg().emailMsgType().getEmailMsgTypeVal(),
                    emailMsgEvent.emailMsg().email(),
                    emailMsgEvent.emailMsg().oriMsg(),
                    Long.valueOf(emailMsgEvent.emailMsg().emailMsgType().getTtl()));
        }
    }

    @Subscribe
    public void handleEmailSendCodeEventByDb(EmailMsgEvent emailMsgEvent) {
        emailSendHistoryService.save(EmailSendHistoryConverter
                .serializeFromEvent(emailMsgEvent));
    }
}
