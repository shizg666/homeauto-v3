package com.landleaf.homeauto.center.oauth.security.origin.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.oauth.util.Commonutils;
import com.landleaf.homeauto.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthLoginInSuccessHandler
 * @Description: 登录成功处理器  由原来的跳转到redirectUrl改为直接返回token
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Slf4j
@Component
public class AuthLoginInSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        log.info("【登陆成功啦，牛逼...】 authentication={}", authentication);

        String header = request.getHeader("Authorization");
        /****************************校验加盐第三方信息******************************************/
        if (header == null || !header.startsWith("Basic ")) {
            response.getWriter().write(JSON.toJSONString(ResponseUtil.returnError("请求头中无client信息", "400")));
            return;
        }
        String[] tokens = Commonutils.extractAndDecodeHeader(header);

        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            response.getWriter().write(JSON.toJSONString(ResponseUtil.returnError("clientId 对应的配置信息不存在" + clientId, "400")));
            return;
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            response.getWriter().write(JSON.toJSONString(ResponseUtil.returnError("clientSecret 不匹配" + clientId, "400")));
            return;
        }

        TokenRequest tokenRequest = new TokenRequest(Maps.newHashMap(), clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.getWriter().write(JSON.toJSONString(ResponseUtil.returnSuccess(token)));
        log.info("token={}", JSON.toJSON(token));

    }


}