package com.landleaf.homeauto.common.config.auth.login;

import com.landleaf.homeauto.common.config.auth.login.filter.OauthFilter;
import com.landleaf.homeauto.common.config.auth.login.token.TokenStore;
import com.landleaf.homeauto.common.constance.CommonConst;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录认证
 */
@Configuration
@ConditionalOnProperty(prefix = "homeauto.auth.login", name = "enable")
public class LoginConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    /**
     * 登出路径
     */
    @Value("${auth.login.logout.path}")
    private String logoutPath;
    /**
     * 白名单路径
     */
    @Value("${auth.login.excluded.paths}")
    private String excludePaths;

    /**
     * token失效时间
     */
    @Value("${auth.login.refresh-token.expired:86400}")
    private long refreshTokenExpire;


    @Bean
    public FilterRegistrationBean authFilterRegistration(TokenStore tokenStore) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("AuthFilter");
        registration.setOrder(3);
        registration.addUrlPatterns("/*");
        OauthFilter authFilter = new OauthFilter(tokenStore);
        authFilter.setApplicationContext(applicationContext);
        registration.setFilter(authFilter);
        registration.addInitParameter(CommonConst.AUTH_LOGOUT, logoutPath);
        registration.addInitParameter(CommonConst.AUTH_EXCLUDED_PATHS, excludePaths);
        registration.addInitParameter(CommonConst.REFRESH_TOKEN_EXPIRE, String.valueOf(refreshTokenExpire));
        return registration;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
