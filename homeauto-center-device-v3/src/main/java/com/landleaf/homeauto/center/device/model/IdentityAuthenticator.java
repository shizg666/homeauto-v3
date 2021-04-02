package com.landleaf.homeauto.center.device.model;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 此类相当于登录校验，确保该邮箱有发送邮件的权利
 *
 * @author Administrator
 */
public class IdentityAuthenticator extends Authenticator {

    private String userName = null;

    private String password = null;

    public IdentityAuthenticator() {

    }

    public IdentityAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }

}
