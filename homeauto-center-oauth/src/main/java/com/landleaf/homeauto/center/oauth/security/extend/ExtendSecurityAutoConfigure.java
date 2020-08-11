package com.landleaf.homeauto.center.oauth.security.extend;


import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendAppNonSmartSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendAppSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendWebSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeFailureHandler;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeSuccessHandler;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendLogoutSuccessHandler;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppNonSmartUserDetailsService;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppUserDetailsService;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWebUserDetailsService;
import com.landleaf.homeauto.center.oauth.service.impl.LoginSuccessService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author wenyilu
 */
@Configuration
@ConditionalOnClass(ExtendSecurityAutoConfigure.class)
@EnableConfigurationProperties(value = ExtendFailureProperties.class)
public class ExtendSecurityAutoConfigure {


    @Bean
    @ConditionalOnMissingBean(ExtendAuthorizeFailureHandler.class)
    public ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler(ExtendFailureProperties failureProperties) {
        ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler = new ExtendAuthorizeFailureHandler();
        return extendAuthorizeFailureHandler;
    }

    @Bean
    @ConditionalOnMissingBean(ExtendAuthorizeSuccessHandler.class)
    public ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler(ClientDetailsService clientDetailsService,
                                                                       AuthorizationServerTokenServices authorizationServerTokenServices,
                                                                       PasswordEncoder passwordEncoder,
                                                                       LoginSuccessService loginSuccessService) {

        ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler = new ExtendAuthorizeSuccessHandler()
                .clientDetailsService(clientDetailsService)
                .authorizationServerTokenServices(authorizationServerTokenServices)
                .passwordEncoder(passwordEncoder)
                .loginSuccessService(loginSuccessService);
        return extendAuthorizeSuccessHandler;
    }

    @Bean
    @ConditionalOnMissingBean(ExtendLogoutSuccessHandler.class)
    public ExtendLogoutSuccessHandler extendLogoutSuccessHandler(TokenStore tokenStore) {
        return new ExtendLogoutSuccessHandler().tokenStore(tokenStore);
    }


    @Bean
    @ConditionalOnMissingBean(ExtendAppSecurityConfigurerAdapter.class)
    ExtendAppSecurityConfigurerAdapter extendAppSecurityConfigurerAdapter(ExtendAppUserDetailsService extendAppUserDetailsService,
                                                                          ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler,
                                                                          ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler) {
        return new ExtendAppSecurityConfigurerAdapter()
                .extendAuthorizeFailureHandler(extendAuthorizeFailureHandler)
                .extendAuthorizeSuccessHandler(extendAuthorizeSuccessHandler)
                .extendAppSecurityConfigurerAdapter(extendAppUserDetailsService);
    }

    @Bean
    @ConditionalOnMissingBean(ExtendAppNonSmartSecurityConfigurerAdapter.class)
    ExtendAppNonSmartSecurityConfigurerAdapter extendAppNonSmartSecurityConfigurerAdapter(ExtendAppNonSmartUserDetailsService extendAppNonSmartUserDetailsService,
                                                                                          ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler,
                                                                                          ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler) {
        return new ExtendAppNonSmartSecurityConfigurerAdapter()
                .extendAuthorizeFailureHandler(extendAuthorizeFailureHandler)
                .extendAuthorizeSuccessHandler(extendAuthorizeSuccessHandler)
                .extendAppNonSmartUserDetailsService(extendAppNonSmartUserDetailsService);
    }

    @Bean
    @ConditionalOnMissingBean(ExtendWebSecurityConfigurerAdapter.class)
    ExtendWebSecurityConfigurerAdapter extendWebSecurityConfigurerAdapter(ExtendWebUserDetailsService extendWebUserDetailsService,
                                                                          ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler,
                                                                          ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler,
                                                                          ExtendAuthenticationEntryPoint extendAuthenticationEntryPoint) {
        return new ExtendWebSecurityConfigurerAdapter()
                .extendAuthorizeFailureHandler(extendAuthorizeFailureHandler)
                .extendAuthorizeSuccessHandler(extendAuthorizeSuccessHandler)
                .extendWebUserDetailsService(extendWebUserDetailsService)
                .extendAuthenticationEntryPoint(extendAuthenticationEntryPoint);
    }

}
