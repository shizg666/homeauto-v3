package com.landleaf.homeauto.common.web.configuration.restful;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * restful接口调用客户端自动装配
 */
@Configuration
@ConditionalOnProperty(prefix = "homeauto.restful", name = "enable")
public class RestFulTemplateConfiguration {

    @Autowired
    private RestfulTemplateProperties restfulTemplateProperties;
    @Autowired
    private FastJsonMessageConverterProperties fastJsonMessageConverterProperties;

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
        restTemplateClient.setFastJsonHttpMessageConverter(fastJsonHttpMessageConverter());
        return restTemplateClient;
    }


    /**
     * 配置fastjson转换类
     *
     * @return
     */
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        if (!CollectionUtils.isEmpty(supportedMediaTypes())) {
            fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes());
        }
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig());
        fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        return fastJsonHttpMessageConverter;
    }

    /**
     * 配置可处理的消息类型
     *
     * @return
     */
    private List<MediaType> supportedMediaTypes() {
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        List<String> supportedMediaTypesConfig = fastJsonMessageConverterProperties.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(supportedMediaTypesConfig)) {
            for (String supportedMediaType : supportedMediaTypesConfig) {
                supportedMediaTypes.add(MediaType.valueOf(supportedMediaType));
            }
        }
        return supportedMediaTypes;
    }

    /**
     * 配置转换规则
     *
     * @return
     */
    public FastJsonConfig fastJsonConfig() {
        FastJsonConfig config = new FastJsonConfig();
        List<String> featuresConfig = fastJsonMessageConverterProperties.getFeatures();
        if (!CollectionUtils.isEmpty(featuresConfig)) {
            Feature[] features = new Feature[featuresConfig.size()];
            for (int i = 0; i < featuresConfig.size(); i++) {
                features[i] = Feature.valueOf(featuresConfig.get(i));
            }
            config.setFeatures(features);
        }
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, // 空字段保留
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero);
        return config;
    }
}
