package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.MailSenderInfo;
import com.landleaf.homeauto.center.device.model.IdentityAuthenticator;
import com.landleaf.homeauto.center.device.model.domain.EmailMsg;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

/**
 * 邮件服务
 *
 * @author wenyilu
 */
@Component
public class SimpleMailService {

    private static MailSenderInfo mailSenderInfo;

    /**
     * 统一发送邮件
     *
     * @param mailSenderInfo 邮件信息
     */
    private static void sendEmail(MailSenderInfo mailSenderInfo, boolean isHtml) {
        IdentityAuthenticator authenticator = null;
        if (mailSenderInfo.isValidate()) {
            authenticator = new IdentityAuthenticator(mailSenderInfo.getUserName(), mailSenderInfo.getPassword());
        }
        Session sendMailSession = Session.getDefaultInstance(mailSenderInfo.getProperties(), authenticator);
        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            mailMessage.setFrom(new InternetAddress(mailSenderInfo.getFromAddress()));
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mailSenderInfo.getToAddress()));
            mailMessage.setSubject(mailSenderInfo.getSubject());
            mailMessage.setSentDate(new Date());
            if (isHtml) {
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(mailSenderInfo.getContent(), "text/html; charset=utf-8");

                MimeMultipart mimeMultipart = new MimeMultipart();
                mimeMultipart.addBodyPart(bodyPart);

                mailMessage.setContent(mimeMultipart);
            } else {
                mailMessage.setText(mailSenderInfo.getContent());
            }
            Transport.send(mailMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 发送文本格式的邮件
     *
     * @param mailSenderInfo 邮件信息
     */
    public static void sendTextEmail(MailSenderInfo mailSenderInfo) {
        sendEmail(mailSenderInfo, false);
    }

    /**
     * 发送HTML格式邮件
     *
     * @param mailSenderInfo 邮件信息
     */
    private static void sendHtmlEmail(MailSenderInfo mailSenderInfo) {
        sendEmail(mailSenderInfo, true);
    }

    /**
     * 发送HTML邮件
     *
     * @param toAddress 目标地址
     * @param subject   主题信息
     * @param content   邮件内容
     * @param sendEmail 发送方地址
     * @param password  发送方邮箱密码
     */
    public static void sendHtmlEmail(String toAddress, String subject, String content, String sendEmail, String password) {
        try {
            mailSenderInfo.setUserName(sendEmail);
            mailSenderInfo.setPassword(password);
            mailSenderInfo.setFromAddress(sendEmail);
            mailSenderInfo.setToAddress(toAddress);
            mailSenderInfo.setSubject(subject);
            mailSenderInfo.setContent(content);
            sendHtmlEmail(mailSenderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCodeEnumConst.ERROR_CODE_MC_EMAIL_SEND_ERROR);
        }
    }

    /**
     * 发送HTML邮件
     *
     * @param toAddress 目标地址
     * @param subject   主题信息
     * @param content   邮件内容
     */
    public static void sendHtmlEmail(String toAddress, String subject, String content) {
        try {
            mailSenderInfo.setToAddress(toAddress);
            mailSenderInfo.setSubject(subject);
            mailSenderInfo.setContent(content);
            sendHtmlEmail(mailSenderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCodeEnumConst.ERROR_CODE_MC_EMAIL_SEND_ERROR);
        }
    }

    /**
     * 发送HTML邮件
     *
     * @param emailMsg 邮件信息
     */
    public static void sendHtmlEmail(EmailMsg emailMsg) {
        String emailAddress = emailMsg.email();
        String subject = emailMsg.emailMsgType().getMsgSubject();
        String msg = emailMsg.msg();
        sendHtmlEmail(emailAddress, subject, msg);
    }

    @Autowired
    public void setMailSenderInfo(MailSenderInfo mailSenderInfo) {
        SimpleMailService.mailSenderInfo = mailSenderInfo;
    }
}
