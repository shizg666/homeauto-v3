package com.landleaf.homeauto.center.oauth.security.origin.config;

import com.landleaf.homeauto.center.oauth.security.origin.tokenstore.redis.AuthRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @ClassName AuthRedisTokenStoreConfig
 * @Description: redis存储token配置类
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Configuration
@ConditionalOnProperty(prefix = "homeauto.security.oauth2", name = "storeType", havingValue = "redis")
public class AuthRedisTokenStoreConfig {

    /**
     * redis连接工厂
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore redisTokenStore() {
        return new AuthRedisTokenStore(redisConnectionFactory);
    }
}
