package com.landleaf.homeauto.common.config.http;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解决httpRequest中body参数只能读取一次问题：重写httpServletRequest
 */
@Configuration
@ConditionalOnProperty(prefix = "homeauto.http", name = "enable")
public class HttpFilterConfigruation {

    @Bean
    public FilterRegistrationBean requestFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("httpServletRequestFilter");
        registration.setOrder(2);
        registration.addUrlPatterns("/*");
        HttpServletRequestFilter httpServletRequestFilter = new HttpServletRequestFilter();
        registration.setFilter(httpServletRequestFilter);
        return registration;
    }
}
