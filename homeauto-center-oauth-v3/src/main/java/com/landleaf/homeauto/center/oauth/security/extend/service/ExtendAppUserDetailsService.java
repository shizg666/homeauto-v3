package com.landleaf.homeauto.center.oauth.security.extend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface ExtendAppUserDetailsService {


    /**
     * 根据手机号码获取用户信息
     *
     * @param mobile
     * @return
     */
    UserDetails loadUserByMobile(String mobile);
}
