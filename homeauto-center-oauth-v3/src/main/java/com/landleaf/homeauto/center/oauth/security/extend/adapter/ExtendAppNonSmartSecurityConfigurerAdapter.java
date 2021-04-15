package com.landleaf.homeauto.center.oauth.security.extend.adapter;


import com.landleaf.homeauto.center.oauth.security.extend.filter.ExtendAppNonSmartAuthorizeFilter;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeFailureHandler;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeSuccessHandler;
import com.landleaf.homeauto.center.oauth.security.extend.provider.ExtendAppNonSmartAuthorizeProvider;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppNonSmartUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * app-nonSmart登录配置类
 *
 * @author wenyilu
 */
public class ExtendAppNonSmartSecurityConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private ExtendAppNonSmartUserDetailsService extendAppNonSmartUserDetailsService;

    private ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler;

    private ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler;

    public ExtendAppNonSmartSecurityConfigurerAdapter extendAuthorizeSuccessHandler(ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler) {
        this.extendAuthorizeSuccessHandler = extendAuthorizeSuccessHandler;
        return this;
    }

    public ExtendAppNonSmartSecurityConfigurerAdapter extendAuthorizeFailureHandler(ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler) {
        this.extendAuthorizeFailureHandler = extendAuthorizeFailureHandler;
        return this;
    }

    public ExtendAppNonSmartSecurityConfigurerAdapter extendAppNonSmartUserDetailsService(ExtendAppNonSmartUserDetailsService extendAppNonSmartUserDetailsService) {
        this.extendAppNonSmartUserDetailsService = extendAppNonSmartUserDetailsService;
        return this;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {


        ExtendAppNonSmartAuthorizeFilter authenticationFilter = new ExtendAppNonSmartAuthorizeFilter();
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(extendAuthorizeSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(extendAuthorizeFailureHandler);

        ExtendAppNonSmartAuthorizeProvider authenticationProvider = new ExtendAppNonSmartAuthorizeProvider();
        authenticationProvider.extendAppNonSmartUserDetailsService(extendAppNonSmartUserDetailsService);
        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
