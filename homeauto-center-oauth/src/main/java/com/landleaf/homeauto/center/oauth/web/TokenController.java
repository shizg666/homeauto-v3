package com.landleaf.homeauto.center.oauth.web;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendAppAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName TokenController
 * @Description: token相关操作
 * @Author wyl
 * @Date 2020/6/9
 * @Version V1.0
 **/
@RestController
@RequestMapping("token")
public class TokenController {
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 根据用户名删除token
     */
    @GetMapping("/delete/username")
    public void delteAllUserTokenByUserName() {

    }

    /**
     * 根据用户名更新所有有效token
     */
    @GetMapping("/update/username")
    public void updateTokenByUserName(@RequestParam("clientId") String clientId, @RequestParam("username") String username) {
        Collection<OAuth2AccessToken> appOAuth2AccessTokenList = tokenStore.findTokensByClientIdAndUserName(clientId, username);
        if (!CollectionUtils.isEmpty(appOAuth2AccessTokenList)) {
            for (OAuth2AccessToken oAuth2AccessToken : appOAuth2AccessTokenList) {
                try {
                    ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

                    TokenRequest tokenRequest = new TokenRequest(Collections.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

                    OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(oAuth2AccessToken);
                    Collection<GrantedAuthority> authorities_old = oAuth2Authentication.getAuthorities();

                    //  生成当前的所有授权
                    List<GrantedAuthority> updatedAuthorities = Lists.newArrayList();
                    for (GrantedAuthority grantedAuthority : authorities_old) {
                        updatedAuthorities.add(grantedAuthority);
                    }
                    // 添加 ROLE_VIP 授权
                    updatedAuthorities.add(new SimpleGrantedAuthority("query3"));
                    // 生成新的认证信息
                    Authentication newAuth = new ExtendAppAuthenticationToken(updatedAuthorities, null, updatedAuthorities);

                    OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

                    OAuth2Authentication oAuth2Authentication2 = new OAuth2Authentication(oAuth2Request, newAuth);

                    tokenStore.storeAccessToken(oAuth2AccessToken, oAuth2Authentication2);

                } catch (ClientRegistrationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
