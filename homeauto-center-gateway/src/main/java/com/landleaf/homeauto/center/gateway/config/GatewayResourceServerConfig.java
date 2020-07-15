package com.landleaf.homeauto.center.gateway.config;//package com.landleaf.demo.wenyilu.oauth2.server.config.resource;//package com.landleaf.demo.wenyilu.oath2.server.config.resource;

import com.landleaf.homeauto.center.gateway.security.handler.ZuulWebSecurityExpressionHandler;
import com.landleaf.homeauto.center.gateway.security.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName GatewayResourceServerConfig
 * @Description: 资源服务配置
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Configuration
@EnableResourceServer
public class GatewayResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ZuulWebSecurityExpressionHandler zuulWebSecurityExpressionHandler;
    @Autowired
    private TokenStore tokenStore;

    /**
     * 白名单路径
     */
    @Value("${homeauto.security.excluded.paths}")
    private String[] excludePaths;
    /**
     * oauth2需要登录认证的路径
     */
    @Value("${homeauto.security.requestMatchers}")
    private String[] requestMatchers;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                // 定义的不存在access_token时候响应
                .authenticationEntryPoint(new SecurityAuthenticationEntryPoint())
                .accessDeniedHandler(new GatewayServerAccessDeniedHandler())
                .and()
               .requestMatchers()
                .antMatchers(requestMatchers)
                .and()
                .authorizeRequests()
                //放过申请令牌的请求不需要身份认证
                .antMatchers(excludePaths).permitAll()
                .anyRequest().access("#permissionService.hasPermission(request,authentication)")
                .and()
                .csrf().disable();
        http.anonymous().disable();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 定义权限解析转换器生效
        resources.expressionHandler(zuulWebSecurityExpressionHandler)
                .tokenStore(tokenStore);
        resources.tokenServices(defaultTokenServices());
        resources.authenticationEntryPoint(gatewayOAuth2AuthenticationEntryPoint())
        .tokenExtractor(new GatewayTokenExtractor());
    }

    @Primary
    @Bean
    public GatewayDefaultTokenServices defaultTokenServices() {
        Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(new ArrayList<>(tokenEnhancers));
        GatewayDefaultTokenServices defaultTokenServices = new GatewayDefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        //是否可以重用刷新令牌
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        return defaultTokenServices;
    }


    @Bean
    @Primary
    public GatewayOAuth2AuthenticationEntryPoint gatewayOAuth2AuthenticationEntryPoint() {
        GatewayResourceWebResponseExceptionTranslator gatewayResourceWebResponseExceptionTranslator = new GatewayResourceWebResponseExceptionTranslator();
        GatewayOAuth2AuthenticationEntryPoint gatewayOAuth2AuthenticationEntryPoint = new GatewayOAuth2AuthenticationEntryPoint(gatewayResourceWebResponseExceptionTranslator);
        return gatewayOAuth2AuthenticationEntryPoint;
    }
}
