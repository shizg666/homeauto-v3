package com.landleaf.homeauto.center.device.common.msg.converter;

import com.landleaf.homeauto.center.device.domain.msg.email.EmailMsgEvent;
import com.landleaf.homeauto.common.domain.po.device.email.EmailSendHistory;

/**
 * EmailSendHistory转换类
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
