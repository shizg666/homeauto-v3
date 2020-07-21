package com.landleaf.homeauto.center.oauth.security.extend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface ExtendWechatUserDetailsService {

    UserDetails loadUserByOpenId(String openid, String sessionKey);
}
