package com.landleaf.homeauto.center.oauth.security.extend.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 邮箱验证码获取Service
 *
 * @author wenyilu
 */
public interface ExtendWebUserDetailsService {


    /**
     * 根据手机或邮箱账号获取用户信息
     * @param account
     * @return
     */
    UserDetails loadUserByEmailOrPhone(String account);
}
