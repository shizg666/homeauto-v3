package com.landleaf.homeauto.center.oauth.security.extend.adapter;


import com.landleaf.homeauto.center.oauth.security.extend.filter.ExtendAppAuthorizeFilter;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeFailureHandler;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeSuccessHandler;
import com.landleaf.homeauto.center.oauth.security.extend.provider.ExtendAppAuthorizeProvider;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * app登录配置类
 *
 * @author wenyilu
 */
public class ExtendAppSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private ExtendAppUserDetailsService extendAppUserDetailsService;

    private ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler;

    private ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler;

    public ExtendAppSecurityConfigurerAdapter extendAuthorizeSuccessHandler(ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler) {
        this.extendAuthorizeSuccessHandler = extendAuthorizeSuccessHandler;
        return this;
    }

    public ExtendAppSecurityConfigurerAdapter extendAuthorizeFailureHandler(ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler) {
        this.extendAuthorizeFailureHandler = extendAuthorizeFailureHandler;
        return this;
    }

    public ExtendAppSecurityConfigurerAdapter extendAppSecurityConfigurerAdapter(ExtendAppUserDetailsService extendAppUserDetailsService) {
        this.extendAppUserDetailsService = extendAppUserDetailsService;
        return this;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {


        ExtendAppAuthorizeFilter authenticationFilter = new ExtendAppAuthorizeFilter();
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(extendAuthorizeSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(extendAuthorizeFailureHandler);

        ExtendAppAuthorizeProvider authenticationProvider = new ExtendAppAuthorizeProvider();
        authenticationProvider.appUserDetailsService(extendAppUserDetailsService);
        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
