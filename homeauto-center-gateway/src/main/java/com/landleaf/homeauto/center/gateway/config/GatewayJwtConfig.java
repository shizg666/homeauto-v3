package com.landleaf.homeauto.center.gateway.config;

import com.landleaf.homeauto.center.gateway.security.jwt.AuthJwtAccessTokenConverter;
import com.landleaf.homeauto.center.gateway.security.jwt.AuthJwtTokenStore;
import com.landleaf.homeauto.common.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author wenyilu
 */
@Configuration
public class GatewayJwtConfig {


    /**
     * 白名单路径
     */
    @Value("${homeauto.security.jwt.key-value}")
    private String jwtSigningKey;

    /**
     * 使用jwtTokenStore存储token
     *
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore(RedisUtil redisUtil) {
        return new AuthJwtTokenStore(authJwtAccessTokenConverter(), redisUtil);
    }

    /**
     * 用于生成jwt
     * 使用对称加密
     * @return
     */
    @Bean
    public AuthJwtAccessTokenConverter authJwtAccessTokenConverter() {
        AuthJwtAccessTokenConverter accessTokenConverter = new AuthJwtAccessTokenConverter();
        // 生成签名的key
        accessTokenConverter.setSigningKey(jwtSigningKey);
        return accessTokenConverter;
    }


}
