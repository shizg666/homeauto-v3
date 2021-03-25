package com.landleaf.homeauto.center.oauth.security.extend.adapter;


import com.landleaf.homeauto.center.oauth.security.extend.ExtendAuthenticationEntryPoint;
import com.landleaf.homeauto.center.oauth.security.extend.filter.ExtendWechatAuthorizeFilter;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeFailureHandler;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeSuccessHandler;
import com.landleaf.homeauto.center.oauth.security.extend.provider.ExtendWechatAuthorizeProvider;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWechatUserDetailsService;
import com.landleaf.homeauto.center.oauth.util.WechatUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 微信登录配置类
 *
 * @author wenyilu
 */
public class ExtendWechatSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private ExtendWechatUserDetailsService extendWechatUserDetailsService;

    private ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler;

    private ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler;

    private ExtendAuthenticationEntryPoint extendAuthenticationEntryPoint;
    private WechatUtil wechatUtil;

    public ExtendWechatSecurityConfigurerAdapter extendAuthorizeSuccessHandler(ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler) {
        this.extendAuthorizeSuccessHandler = extendAuthorizeSuccessHandler;
        return this;
    }

    public ExtendWechatSecurityConfigurerAdapter extendAuthorizeFailureHandler(ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler) {
        this.extendAuthorizeFailureHandler = extendAuthorizeFailureHandler;
        return this;
    }

    public ExtendWechatSecurityConfigurerAdapter extendWechatUserDetailsService(ExtendWechatUserDetailsService extendWechatUserDetailsService) {
        this.extendWechatUserDetailsService = extendWechatUserDetailsService;
        return this;
    }
    public ExtendWechatSecurityConfigurerAdapter extendAuthenticationEntryPoint(ExtendAuthenticationEntryPoint extendAuthenticationEntryPoint) {
        this.extendAuthenticationEntryPoint = extendAuthenticationEntryPoint;
        return this;
    }
    public ExtendWechatSecurityConfigurerAdapter wechatUtil(WechatUtil wechatUtil) {
        this.wechatUtil = wechatUtil;
        return this;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {


        ExtendWechatAuthorizeFilter authenticationFilter = new ExtendWechatAuthorizeFilter();
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(extendAuthorizeSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(extendAuthorizeFailureHandler);

        ExtendWechatAuthorizeProvider authenticationProvider = new ExtendWechatAuthorizeProvider();
        authenticationProvider.wechatUserDetailsService(extendWechatUserDetailsService);
        authenticationProvider.wechatUtil(wechatUtil);
        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
