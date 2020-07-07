package com.landleaf.homeauto.center.oauth.security.extend.service.impl;

import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppUserDetailsService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ExtendAppUserDetailsServiceImpl implements ExtendAppUserDetailsService {


    @Override
    public UserDetails loadUserByMobile(String mobile) {
        HomeAutoUserDetails user = new HomeAutoUserDetails(AuthorityUtils.commaSeparatedStringToAuthorityList("query3"), "123456", mobile, "app");
        return user;
    }
}
