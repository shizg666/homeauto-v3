package com.landleaf.homeauto.center.gateway.security.resource;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName GatewayOAuth2AuthenticationEntryPoint
 * @Description: TODO
 * @Author wyl
 * @Date 2020/7/14
 * @Version V1.0
 **/
public class GatewayOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    private WebResponseExceptionTranslator<?> exceptionTranslator;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        super.commence(request, response, authException);
    }

    public GatewayOAuth2AuthenticationEntryPoint(WebResponseExceptionTranslator exceptionTranslator) {
       this.setExceptionTranslator(exceptionTranslator);
    }
}
