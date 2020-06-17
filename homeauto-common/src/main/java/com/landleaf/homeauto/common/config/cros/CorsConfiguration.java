package com.landleaf.homeauto.common.config.cros;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 跨域过滤器配置类:须注意加载顺序必须在第一位
 * @author wenyilu
 */
@Configuration
public class CorsConfiguration {

    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("CorsFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        CorsFilter corsFilter = new CorsFilter();
        registration.setFilter(corsFilter);
        return registration;
    }
}
