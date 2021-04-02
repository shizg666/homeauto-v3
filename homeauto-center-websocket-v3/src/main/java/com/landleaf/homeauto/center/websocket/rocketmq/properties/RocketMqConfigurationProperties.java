package com.landleaf.homeauto.center.websocket.rocketmq.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 全局配置
 *
 * @author Yujiumin
 * @version 2020/9/10
 */
@Configuration
@ConfigurationProperties("rocketmq")
public class RocketMqConfigurationProperties {

    private String nameServerAddr = "127.0.0.1:9876";

    public String getNameServerAddr() {
        return nameServerAddr;
    }

    public void setNameServerAddr(String nameServerAddr) {
        this.nameServerAddr = nameServerAddr;
    }
}
