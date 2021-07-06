package com.landleaf.homeauto.center.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Properties;

/**
 * 邮件发送者信息
 *
 * @author wenyilu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailSenderInfo {

    /**
     * 发送邮件的服务器的IP(或主机地址)
     */
    private String mailServerHost;

    /**
     * 发送邮件的服务器的端口
     */
    private String mailServerPort = "25";

    /**
     * 发件人邮箱地址
     */
    private String fromAddress;

    /**
     * 收件人邮箱地址
     */
    private String toAddress;

    /**
     * 登陆邮件发送服务器的用户名
     */
    private String userName;

    /**
     * 登陆邮件发送服务器的密码
     */
    private String password;

    /**
     * 是否需要身份验证
     */
    private boolean validate = false;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件的文本内容
     */
    private String content;

    /**
     * 邮件附件的文件名
     */
    private String[] attachFileNames;


    public Properties getProperties() {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Properties p = new Properties();
//        p.put("mail.smtp.host", this.mailServerHost);
//        p.put("mail.smtp.port", this.mailServerPort);
//        p.put("mail.smtp.auth", validate ? "true" : "false");
        //邮箱的发送服务器地址
        p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        p.setProperty("mail.smtp.socketFactory.fallback", "false");
        //邮箱发送服务器端口,这里设置为465端口
        p.put("mail.smtp.host", "smtp.qiye.163.com");
        p.setProperty("mail.smtp.port", "994");
        p.setProperty("mail.smtp.socketFactory.port", "994");
        p.put("mail.smtp.auth", "true");
        return p;
    }

}
