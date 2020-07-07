package com.landleaf.homeauto.center.oauth.security.extend;


import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendAppSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeFailureHandler;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendAuthorizeSuccessHandler;
import com.landleaf.homeauto.center.oauth.security.extend.handler.ExtendLogoutSuccessHandler;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppUserDetailsService;
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
        extendAuthorizeFailureHandler.setCodeParam(failureProperties.getStatusParam());
        extendAuthorizeFailureHandler.setMsgParam(failureProperties.getMsgParam());
        return extendAuthorizeFailureHandler;
    }

    @Bean
    @ConditionalOnMissingBean(ExtendAuthorizeSuccessHandler.class)
    public ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler(ClientDetailsService clientDetailsService,
                                                                       AuthorizationServerTokenServices authorizationServerTokenServices,
                                                                       PasswordEncoder passwordEncoder) {

        ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler = new ExtendAuthorizeSuccessHandler()
                .clientDetailsService(clientDetailsService)
                .authorizationServerTokenServices(authorizationServerTokenServices)
                .passwordEncoder(passwordEncoder);
        return extendAuthorizeSuccessHandler;
    }

    @Bean
    @ConditionalOnMissingBean(ExtendLogoutSuccessHandler.class)
    public ExtendLogoutSuccessHandler extendLogoutSuccessHandler(TokenStore tokenStore) {
        return new ExtendLogoutSuccessHandler().tokenStore(tokenStore);
    }

    @Bean
    @ConditionalOnMissingBean(ExtendWebResponseExceptionTranslator.class)
    public ExtendWebResponseExceptionTranslator baseWebResponseExceptionTranslator(ExtendFailureProperties failureProperties) {
        ExtendWebResponseExceptionTranslator extendWebResponseExceptionTranslator = new ExtendWebResponseExceptionTranslator();
        extendWebResponseExceptionTranslator.setCodeParam(failureProperties.getStatusParam());
        extendWebResponseExceptionTranslator.setMsgParam(failureProperties.getMsgParam());
        return extendWebResponseExceptionTranslator;
    }


    @Bean
    @ConditionalOnMissingBean(ExtendAppSecurityConfigurerAdapter.class)
    ExtendAppSecurityConfigurerAdapter extendAppSecurityConfigurerAdapter(ExtendAppUserDetailsService extendAppUserDetailsService,
                                                                          ExtendAuthorizeFailureHandler extendAuthorizeFailureHandler,
                                                                          ExtendAuthorizeSuccessHandler extendAuthorizeSuccessHandler) {
        return new ExtendAppSecurityConfigurerAdapter()
                .baseAuthenticationFailureHandler(extendAuthorizeFailureHandler)
                .baseAuthenticationSuccessHandler(extendAuthorizeSuccessHandler)
                .appUserDetailsService(extendAppUserDetailsService);
    }

}
