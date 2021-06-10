package com.landleaf.homeauto.center.oauth.security.origin.service;

import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 自定义获取用户信息处理类
 * 权限刷新只能重新定义,后关联token，原始的不可修改，是个坑
 *
 * @ClassName AuthUserDetailsService
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HomeAutoUserDetails user = new HomeAutoUserDetails(AuthorityUtils.commaSeparatedStringToAuthorityList("query"), "123456", username, "app","123");
        return user;
    }
}

