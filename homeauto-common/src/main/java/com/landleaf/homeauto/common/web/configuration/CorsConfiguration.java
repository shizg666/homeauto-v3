package com.landleaf.homeauto.common.web.configuration;

import com.landleaf.homeauto.common.web.filter.CorsFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 跨域过滤器配置类
 * @author: wyl
 **/
@Configuration
@ConditionalOnProperty(prefix = "homeauto.cros", name = "enable")
public class CorsConfiguration {

    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("CorsFilter");
        registration.setOrder(-100);
        registration.addUrlPatterns("/*");
        CorsFilter corsFilter = new CorsFilter();
        registration.setFilter(corsFilter);
        return registration;
    }
}
