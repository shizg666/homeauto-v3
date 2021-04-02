package com.landleaf.homeauto.center.device.bean;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jsms.api.JSMSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 极光平台配置
 *
 * @author Yujiumin
 * @version 2020/07/23
 */
@Configuration
public class JgConfig {

    @Value("#{homeAutoJgProperties.appKey}")
    private String appKey;

    @Value("#{homeAutoJgProperties.masterSecret}")
    private String masterSecret;

    @Bean
    public JPushClient jPushClient() {
        return new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
    }

    @Bean
    public JSMSClient jsmsClient() {
        return new JSMSClient(masterSecret, appKey);
    }
}
