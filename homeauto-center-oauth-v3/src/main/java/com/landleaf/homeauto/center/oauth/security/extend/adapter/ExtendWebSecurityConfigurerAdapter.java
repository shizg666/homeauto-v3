package com.landleaf.homeauto.center.oauth.security.extend.adapter;


import com.landleaf.homeauto.center.oauth.security.extend.ExtendAuthenticationEntryPoint;
import com.landleaf.homeauto.center.oauth.security.extend.filter.ExtendWebAuthorizeFilter;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeFailureHandler;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeSuccessHandler;
import com.landleaf.homeauto.center.oauth.security.extend.provider.ExtendWebAuthorizeProvider;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWebUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * web登录配置类
 *
 * @author wenyilu
 */
public class ExtendWebSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private ExtendWebUserDetailsService extendWebUserDetailsService;

    private ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler;

    private ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler;

    private ExtendAuthenticationEntryPoint extendAuthenticationEntryPoint;

    public ExtendWebSecurityConfigurerAdapter extendAuthorizeSuccessHandler(ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler) {
        this.extendAuthorizeSuccessHandler = extendAuthorizeSuccessHandler;
        return this;
    }

    public ExtendWebSecurityConfigurerAdapter extendAuthorizeFailureHandler(ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler) {
        this.extendAuthorizeFailureHandler = extendAuthorizeFailureHandler;
        return this;
    }

    public ExtendWebSecurityConfigurerAdapter extendWebUserDetailsService(ExtendWebUserDetailsService extendWebUserDetailsService) {
        this.extendWebUserDetailsService = extendWebUserDetailsService;
        return this;
    }
    public ExtendWebSecurityConfigurerAdapter extendAuthenticationEntryPoint(ExtendAuthenticationEntryPoint extendAuthenticationEntryPoint) {
        this.extendAuthenticationEntryPoint = extendAuthenticationEntryPoint;
        return this;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {


        ExtendWebAuthorizeFilter authenticationFilter = new ExtendWebAuthorizeFilter();
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(extendAuthorizeSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(extendAuthorizeFailureHandler);

        ExtendWebAuthorizeProvider authenticationProvider = new ExtendWebAuthorizeProvider();
        authenticationProvider.extendWebUserDetailsService(extendWebUserDetailsService);
        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling().authenticationEntryPoint(extendAuthenticationEntryPoint);
    }

}
