package com.landleaf.homeauto.center.device.eventbus.subscriber;

import com.google.common.eventbus.Subscribe;
import com.landleaf.homeauto.center.device.eventbus.event.EmailMsgEvent;
import com.landleaf.homeauto.center.device.eventbus.publisher.EmailMsgEventPublisher;
import com.landleaf.homeauto.center.device.eventbus.converter.EmailSendHistoryConverter;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForEmailMessage;
import com.landleaf.homeauto.center.device.service.mybatis.IEmailSendHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class EmailMsgEventSubscriber {

    private RedisServiceForEmailMessage redisServiceForEmailMessage;

    private IEmailSendHistoryService emailSendHistoryService;

    @Autowired
    public EmailMsgEventSubscriber(RedisServiceForEmailMessage redisServiceForEmailMessage, IEmailSendHistoryService emailSendHistoryService, EmailMsgEventPublisher emailMsgEventPublisher) {
        this.redisServiceForEmailMessage = redisServiceForEmailMessage;
        this.emailSendHistoryService = emailSendHistoryService;
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
            redisServiceForEmailMessage.hsetCodeByEmail(
                    emailMsgEvent.emailMsg().emailMsgType().getEmailMsgTypeVal(),
                    emailMsgEvent.emailMsg().email(),
                    emailMsgEvent.emailMsg().oriMsg(),
                    Long.valueOf(emailMsgEvent.emailMsg().emailMsgType().getTtl()));
        }
    }

    @Subscribe
    public void handleEmailSendCodeEventByDb(EmailMsgEvent emailMsgEvent) {
        emailSendHistoryService.save(EmailSendHistoryConverter.serializeFromEvent(emailMsgEvent));
    }
}
