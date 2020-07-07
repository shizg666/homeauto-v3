package com.landleaf.homeauto.center.oauth.security.origin.properties;

import lombok.Data;

/**
 * @ClassName OAuth2ClientProperties
 * @Description: 应用识别配置
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    /**
     * 若是授权码模式需要回调第三方应用接收授权码地址
     */
    private String redirectUris;

    /**
     * token
     */
    private Integer accessTokenValiditySeconds = 7200;

    private Integer refreshTokenValiditySeconds = 60 * 60 * 24 * 15;
}
