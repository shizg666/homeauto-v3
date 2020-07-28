package com.landleaf.homeauto.center.oauth.security.origin.config;

import com.landleaf.homeauto.center.oauth.security.origin.properties.OAuth2Properties;
import com.landleaf.homeauto.center.oauth.security.origin.tokenstore.jwt.AuthJwtAccessTokenConverter;
import com.landleaf.homeauto.center.oauth.security.origin.tokenstore.jwt.AuthJwtTokenStore;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @ClassName AuthJwtTokenStoreConfig
 * @Description: jwt形式token配置类
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Configuration
@ConditionalOnProperty(prefix = "homeauto.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
public class AuthJwtTokenStoreConfig {
    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Value("${homeauto.security.oauth2.customerEnableRefreshTime}")
    private Long enableRefreshTime;
    @Value("${homeauto.security.oauth2.maxTokenCount}")
    private Integer maxTokenCount;
    /**
     * 使用jwtTokenStore存储token
     *
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore(RedisUtils redisUtils) {
        return new AuthJwtTokenStore(authJwtAccessTokenConverter(), redisUtils,enableRefreshTime,maxTokenCount);
    }

    /**
     * 用于生成jwt
     *
     * @return
     */
    @Bean
    public AuthJwtAccessTokenConverter authJwtAccessTokenConverter() {
        AuthJwtAccessTokenConverter accessTokenConverter = new AuthJwtAccessTokenConverter();
        // 生成签名的key
        accessTokenConverter.setSigningKey(oAuth2Properties.getJwtSigningKey());
//        accessTokenConverter.setKeyPair(keyPair());
        return accessTokenConverter;
    }

    /**
     * key
     * @return
     */
//    @Bean
//    public KeyPair keyPair(){
//        KeyPair keyPair = new KeyStoreKeyFactory(
//                oAuth2Properties.getKeyPath(),
//                oAuth2Properties.getSecret().toCharArray()).getKeyPair(oAuth2Properties.getAlias());
//        return keyPair;
//    }
//    /**
//     * 用于扩展JWT
//     *
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
//    public TokenEnhancer jwtTokenEnhancer() {
//        return new AuthJwtTokenEnhancer();
//    }


}
