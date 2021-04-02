package com.landleaf.homeauto.center.device.model;

import com.landleaf.homeauto.center.device.bean.MsgTemplateConfig;
import com.landleaf.homeauto.common.domain.po.device.MsgTemplate;
import com.landleaf.homeauto.common.enums.email.EmailMsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 邮件类型
 *
 * @author Lokiy
 * @date 2019/8/29 10:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailMsgType {

    /**
     * 模板id
     */
    private String templateId;

    /**
     * email邮件类型
     */
    private Integer emailMsgTypeVal;


    /**
     * 消息主题
     */
    private String msgSubject;

    /**
     * 内容模板
     */
    private String contentTemplate;

    /**
     * 过期时间
     */
    private Integer ttl;

    /**
     * 根据msgTemplate得出具体消息类型
     *
     * @param msgTemplate
     */

    public EmailMsgType(MsgTemplate msgTemplate) {
        this.templateId = msgTemplate.getId();
        this.msgSubject = msgTemplate.getMsgSubject();
        this.emailMsgTypeVal = msgTemplate.getMsgType();
        this.contentTemplate = msgTemplate.getMsgContent();
        this.ttl = msgTemplate.getTtl();
    }

    /**
     * 根据传入类型获取msg模板
     *
     * @param emailMsgType
     * @return
     */
    public static EmailMsgType newEmailMsgType(Integer emailMsgType) {
        MsgTemplate msgTemplate = MsgTemplateConfig.EMAIL_MSG_HOLDER.get(emailMsgType);
        if (msgTemplate == null) {
            return defaultEmailMsgType();
        }
        return new EmailMsgType(msgTemplate);
    }

    /**
     * 默认消息类型
     *
     * @return
     */
    private static EmailMsgType defaultEmailMsgType() {
        EmailMsgType emailMsgType = new EmailMsgType();
        emailMsgType.setTemplateId("0");
        emailMsgType.setMsgSubject("【户式化智能平台】提示信息");
        emailMsgType.setEmailMsgTypeVal(EmailMsgTypeEnum.EMAIL_MSG.getType());
        emailMsgType.setTtl(-1);
        return emailMsgType;
    }
}
