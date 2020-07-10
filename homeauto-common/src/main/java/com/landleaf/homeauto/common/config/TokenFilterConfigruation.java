package com.landleaf.homeauto.common.config;

import com.landleaf.homeauto.common.filter.TokenFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "homeauto.token", name = "enable")
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
