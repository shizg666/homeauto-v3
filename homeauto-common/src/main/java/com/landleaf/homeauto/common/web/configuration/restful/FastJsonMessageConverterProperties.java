package com.landleaf.homeauto.common.web.configuration.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FastJson转换参数配置
 *
 * @author wenyilu
 */
@Component
@ConfigurationProperties(prefix = "homeauto.fastjson")
public class FastJsonMessageConverterProperties {

    /**
     * 是否使用fastjson转换json
     */
    private boolean enable;

    /**
     * 支持的参数类型
     */
    private List<String> supportedMediaTypes;


    @Autowired
    private Environment env;

    /**
     * 转换的配置
     */
    private List<String> features;

    public List<String> getSupportedMediaTypes() {
        return supportedMediaTypes;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setSupportedMediaTypes(List<String> supportedMediaTypes) {
        this.supportedMediaTypes = supportedMediaTypes;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

}
