package com.landleaf.homeauto.contact.screen.config;

import com.landleaf.homeauto.contact.screen.filter.ContactScreenContextFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wenyilu
 */
@Configuration
@ConditionalOnProperty(prefix = "homeauto.contact-screen.context", name = "enable")
public class ContactScreenContextConfigruation {

    @Bean
    public FilterRegistrationBean<ContactScreenContextFilter> contextFilterFilterRegistrationBean() {
        FilterRegistrationBean<ContactScreenContextFilter> registration = new FilterRegistrationBean<>();
        registration.setName("ContactScreenContextFilter");
        registration.setOrder(4);
        registration.addUrlPatterns("/*");
        ContactScreenContextFilter contextFilter = new ContactScreenContextFilter();
        registration.setFilter(contextFilter);
        return registration;
    }
}
