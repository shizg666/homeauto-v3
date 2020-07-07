package com.landleaf.homeauto.center.oauth.security.origin.config;

import com.landleaf.homeauto.center.oauth.security.origin.handler.AuthExceptionEntryPoint;
import com.landleaf.homeauto.center.oauth.security.origin.converter.AuthServerUserAuthenticationConverter;
import com.landleaf.homeauto.center.oauth.security.origin.properties.OAuth2ClientProperties;
import com.landleaf.homeauto.center.oauth.security.origin.properties.OAuth2Properties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName AuthAuthorizeConfig
 * @Description: 认证服务配置
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Configuration
@EnableAuthorizationServer
public class AuthAuthorizeConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private OAuth2Properties oAuth2Properties;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService authUserDetailsService;

    /**
     * 根据配置动态变化 可以为jwt、redis 、jdbc
     */
    @Autowired
    private TokenStore tokenStore;


    @Autowired
    private WebResponseExceptionTranslator authWebResponseExceptionTranslator;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenServices(defaultTokenServices())
                // 自定义UserDetails扩展类解析逻辑check-token
                .accessTokenConverter(defaultAccessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(authUserDetailsService);

        // 认证异常翻译
        endpoints.exceptionTranslator(authWebResponseExceptionTranslator);
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(new ArrayList<>(tokenEnhancers));
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        //是否可以重用刷新令牌
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        return defaultTokenServices;
    }

    @Primary
    @Bean
    public DefaultAccessTokenConverter defaultAccessTokenConverter() {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        UserAuthenticationConverter userTokenConverter = new AuthServerUserAuthenticationConverter();
        accessTokenConverter.setUserTokenConverter(userTokenConverter);
        return accessTokenConverter;
    }

    /**
     * 配置客户端一些信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置有点全，其实用不了这么多哈
        InMemoryClientDetailsServiceBuilder build = clients.inMemory();
        if (ArrayUtils.isNotEmpty(oAuth2Properties.getClients())) {
            for (OAuth2ClientProperties config : oAuth2Properties.getClients()) {
                build.withClient(config.getClientId())
                        .secret(config.getClientSecret())
                        // token有效时间  秒
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        .refreshTokenValiditySeconds(config.getRefreshTokenValiditySeconds())
                        // OAuth2支持的验证模式   模式可以自定义（无非要新写一套逻辑）
                        .authorizedGrantTypes("refresh_token", "password")
                        // 跳过认证确认的过程
                        .autoApprove(true)
                        // 授权码方式需要
                        .redirectUris(config.getRedirectUris())
                        // 限制允许的权限配置
                        .scopes("all");
            }
        }
    }


    /**
     * 配置授权服务器端点的安全
     *
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .authenticationEntryPoint(new AuthExceptionEntryPoint());
    }


}

