package com.landleaf.homeauto.center.device.bean;

import com.landleaf.homeauto.center.device.model.MailSenderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 邮件配置
 *
 * @author Yujiumin
 * @version 2020/07/23
 */
@Configuration
public class EmailConfig {

    @Value("#{homeAutoEmailProperties.address}")
    public String emailAddress;

    @Value("#{homeAutoEmailProperties.pwd}")
    public String emailPassword;


    @Bean
    public MailSenderInfo mailSenderInfo() {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.qiye.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName(emailAddress);
        mailInfo.setPassword(emailPassword);
        mailInfo.setFromAddress(emailAddress);
        return mailInfo;
    }

}
