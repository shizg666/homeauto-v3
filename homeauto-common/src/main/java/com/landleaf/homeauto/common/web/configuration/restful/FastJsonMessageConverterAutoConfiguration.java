package com.landleaf.homeauto.common.web.configuration.restful;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * FastJson自动装载
 */
@Configuration
@ConditionalOnProperty(prefix = "homeauto.fastjson",name = "enable")
public class FastJsonMessageConverterAutoConfiguration {

    @Autowired
    private FastJsonMessageConverterProperties fastJsonMessageConverterProperties;

    /**
     * 配置fastjson转换类
     * @return
     */
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMethodMessageConverter() {
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
     * @return
     */
    @Bean
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
