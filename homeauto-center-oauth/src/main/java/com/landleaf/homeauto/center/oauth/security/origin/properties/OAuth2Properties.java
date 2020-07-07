package com.landleaf.homeauto.center.oauth.security.origin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName OAuth2Properties
 * @Description: oauth2基础配置
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "homeauto.security.oauth2")
public class OAuth2Properties {

    /**
     * 对称加密
     */
    private String jwtSigningKey = "homeauto";


    private OAuth2ClientProperties[] clients = {};
}
