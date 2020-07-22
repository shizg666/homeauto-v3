package com.landleaf.homeauto.center.device.eventbus.converter;

import com.landleaf.homeauto.center.device.eventbus.event.EmailMsgEvent;
import com.landleaf.homeauto.common.domain.po.device.email.EmailSendHistory;

/**
 * EmailSendHistory转换类
 *
 * @author wenyilu
 */
public class EmailSendHistoryConverter {

    public static EmailSendHistory serializeFromEvent(EmailMsgEvent emailMsgEvent) {
        EmailSendHistory emailSendHistory = new EmailSendHistory();
        emailSendHistory.setTemplateId(emailMsgEvent
                .emailMsg()
                .emailMsgType()
                .getTemplateId());
        emailSendHistory.setEmail(emailMsgEvent.emailMsg().email());
        emailSendHistory.setSubject(emailMsgEvent
                .emailMsg()
                .emailMsgType()
                .getMsgSubject());
        emailSendHistory.setContent(emailMsgEvent.emailMsg().msg());
        return emailSendHistory;
    }
}
