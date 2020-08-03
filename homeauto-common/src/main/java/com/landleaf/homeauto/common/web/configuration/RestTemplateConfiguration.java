package com.landleaf.homeauto.common.web.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 区别于feign底层使用的restTemplate
 *
 * @author wenyilu
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean("outRestTemplate")
    public RestTemplate outRestTemplate() {
        return new RestTemplate();
    }
}
