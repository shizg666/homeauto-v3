package com.landleaf.homeauto.center.oauth.security.extend.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.center.oauth.service.impl.LoginSuccessService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.app.AppLoginRequestDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.wechat.WechatLoginRequestDTO;
import com.landleaf.homeauto.common.util.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 认证成功处理器
 *
 * @author wenyilu
 */
@Slf4j
public class ExtendAuthorizeSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String ACCEPT_TYPE_HTML = "text/html";

    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    private static final String GRANT_TYPE = "custom";

    private ClientDetailsService clientDetailsService;

    private PasswordEncoder passwordEncoder;

    private AuthorizationServerTokenServices authorizationServerTokenServices;

    private LoginSuccessService loginSuccessService;

    public ExtendAuthorizeSuccessHandler loginSuccessService(LoginSuccessService loginSuccessService) {
        this.loginSuccessService = loginSuccessService;
        return this;
    }

    public ExtendAuthorizeSuccessHandler clientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
        return this;
    }

    public ExtendAuthorizeSuccessHandler passwordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    public ExtendAuthorizeSuccessHandler authorizationServerTokenServices(AuthorizationServerTokenServices authorizationServerTokenServices) {
        this.authorizationServerTokenServices = authorizationServerTokenServices;
        return this;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("登录成功之后的处理");


        String type = request.getHeader("Accept");
        log.info("请求type:"+type);
        boolean flag = true;
        if(!org.apache.commons.lang3.StringUtils.isEmpty(type) &&type.contains(ACCEPT_TYPE_HTML)){
            flag=false;
        }
        if (flag) {
            String clientId = null;
            String clientSecret = null;
            String servletPath = request.getServletPath();

            if (servletPath.contains("/login/app")) {

                try {
                    byte[] body = StreamUtils.getByteByStream(request.getInputStream());
                    String data = new String(body, StandardCharsets.UTF_8);
                    AppLoginRequestDTO appLoginRequestDTO = JSON.parseObject(data, AppLoginRequestDTO.class);
                    clientId = appLoginRequestDTO.getClientId();
                    clientSecret = appLoginRequestDTO.getClientSecret();
                } catch (Exception e) {
                    System.out.println("错误信息");
                }

            } else if(servletPath.contains("/login/wechat")) {

                try {
                    byte[] body = StreamUtils.getByteByStream(request.getInputStream());
                    String data = new String(body, StandardCharsets.UTF_8);
                    WechatLoginRequestDTO wechatLoginRequestDTO = JSON.parseObject(data, WechatLoginRequestDTO.class);
                    clientId = wechatLoginRequestDTO.getClientId();
                    clientSecret = wechatLoginRequestDTO.getClientSecret();
                } catch (Exception e) {
                    System.out.println("错误信息");
                }
            } else {
                clientId = request.getParameter("client_id");
                clientSecret = request.getParameter("client_secret");
            }

            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            if (null == clientDetails) {
                throw new UnapprovedClientAuthenticationException("clientId不存在" + clientId);
            } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
                throw new UnapprovedClientAuthenticationException("clientSecret 不匹配" + clientId);
            }

            TokenRequest tokenRequest = new TokenRequest(Collections.EMPTY_MAP, clientId, clientDetails.getScope(), GRANT_TYPE);

            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

            HomeAutoUserDetails principal = (HomeAutoUserDetails) authentication.getPrincipal();
            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            // 组装返回信息
            Object result = loginSuccessService.buildLoginSuccessData(principal.getUserId(), principal.getSource(), token.getValue(), principal);
            response.setContentType(CONTENT_TYPE_JSON);
            Response data = new Response();
            data.setResult(result);
            data.setSuccess(true);
            response.getWriter().write(JSON.toJSONString(data, SerializerFeature.WriteMapNullValue));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
