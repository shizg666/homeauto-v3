package com.landleaf.homeauto.common.config.auth.token;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 内部服务token拦截器：需要解析获取token的话启用
 */
@Configuration
@ConditionalOnProperty(prefix = "auth.feign.token", name = "enable")
public class TokenFilterConfigruation {

    @Bean
    public FilterRegistrationBean tokenFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("TokenFilter");
        registration.setOrder(4);
        registration.addUrlPatterns("/*");
        TokenFilter tokenFilter = new TokenFilter();
        registration.setFilter(tokenFilter);
        return registration;
    }
}
