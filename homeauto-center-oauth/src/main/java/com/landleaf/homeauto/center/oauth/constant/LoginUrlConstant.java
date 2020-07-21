package com.landleaf.homeauto.center.oauth.constant;

/**
 * 不同登录地址常量
 */
public interface LoginUrlConstant {
    /**
     * origin：用户名密码模式登录
     */
    String LOGIN_ORIGIN_URL = "/login/origin";
    /**
     * app：用户名密码模式登录
     */
    String LOGIN_APP_URL = "/login/app";
    /**
     * app：登出
     */
    String LOGOUT_APP_URL = "/logout/app";
    /**
     * web：用户名密码模式登录
     */
    String LOGIN_WEB_URL = "/login/web";
    /**
     * web：登出
     */
    String LOGOUT_WEB_URL = "/logout/web";
    /**
     * wechat：微信小程序登录
     */
    String LOGIN_WECHAT_URL = "/login/wechat";
}
