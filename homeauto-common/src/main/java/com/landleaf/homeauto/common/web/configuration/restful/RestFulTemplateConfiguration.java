package com.landleaf.homeauto.common.web.configuration.restful;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;

import java.util.Arrays;

/**
 * restful接口调用客户端自动装配
 */
@Configuration
@ConditionalOnProperty(prefix = "homeauto.restful",name = "enable")
public class RestFulTemplateConfiguration {

    @Autowired
    private RestfulTemplateProperties restfulTemplateProperties;

    @Autowired(required = false)
    private FastJsonHttpMessageConverter fastJsonHttpMethodMessageConverter;

    /**
     * 创建访问restful接口的客户端工具
     *
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestTemplateClient restTemplateClient() {

        RestTemplateClient restTemplateClient = new RestTemplateClient();
        restTemplateClient.setRestfulTemplateProperties(restfulTemplateProperties);
        fastJsonHttpMethodMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON_UTF8));
        restTemplateClient.setFastJsonHttpMessageConverter4(fastJsonHttpMethodMessageConverter);
        return restTemplateClient;
    }
}
