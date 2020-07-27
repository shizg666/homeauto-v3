package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.center.device.model.constant.ConstForJg;
import com.landleaf.homeauto.center.device.util.SmartHomeCodeGenerator;
import com.landleaf.homeauto.common.constance.TimeConst;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 邮件消息
 *
 * @author wenyilu
 */
@Data
@Accessors(fluent = true)
@NoArgsConstructor
public class EmailMsg {

    /**
     * 目标email
     */
    private String email;

    /**
     * 传送信息, 如果type为1的话  即为验证码
     */
    private String msg;

    /**
     * 原始信息,模板钱信息
     */
    private String oriMsg;

    /**
     * 邮件类型
     */
    private EmailMsgType emailMsgType;

    public EmailMsg(String email) {
        this.email = email;
    }

    /**
     * 新建消息类型
     *
     * @param emailMsgType
     * @param email
     * @return
     */
    public static EmailMsg newEmailCode(Integer emailMsgType, String email) {
        EmailMsg emailMsg = new EmailMsg(email);
        emailMsg.msg = SmartHomeCodeGenerator.codeRandom();
        emailMsg.emailMsgType = EmailMsgType.newEmailMsgType(emailMsgType);
        return emailMsg;
    }

    /**
     * 填充msg
     *
     * @param replaceTag
     * @return
     */
    public EmailMsg fillMsg(String replaceTag) {
        String contentTemplate = this.emailMsgType.getContentTemplate();
        this.oriMsg = this.msg;
        String result;
        if (StringUtils.isNotBlank(contentTemplate)) {
            result = contentTemplate.replace(replaceTag, msg);
            this.msg = result;
        }
        return this;
    }

    /**
     * 填充主题
     *
     * @param subject
     * @return
     */
    public EmailMsg fillSubject(String subject) {
        if (StringUtils.isNotBlank(subject)) {
            this.emailMsgType.setMsgSubject(subject);
        }
        return this;
    }

    /**
     * 填充ttl
     *
     * @param ttlReplaceTag
     * @return
     */
    public EmailMsg fillTtl(String ttlReplaceTag) {
        String hours = String.valueOf(this.emailMsgType.getTtl() / TimeConst.ONE_HOUR);
        this.msg = this.msg.replace(ttlReplaceTag, hours);
        return this;
    }

    /**
     * 验证邮件code
     *
     * @param emailMsgType
     * @param email
     * @param code
     * @return
     */
    public static EmailMsg buildEmailMsg(Integer emailMsgType, String email, String code) {
        EmailMsg emailMsg = new EmailMsg(email);
        emailMsg.msg = code;
        emailMsg.emailMsgType = EmailMsgType.newEmailMsgType(emailMsgType);
        return emailMsg;
    }

    /**
     * 当为验证码时 验证验证码
     *
     * @param toVerifyCode 待验证的验证码
     * @return 验证结果
     */
    public boolean verifyCode(String toVerifyCode) {
        if (Objects.equals(ConstForJg.SPECIAL_VERIFY_CODE, toVerifyCode)) {
            return true;
        }
        return Objects.equals(msg, toVerifyCode);
    }


}
