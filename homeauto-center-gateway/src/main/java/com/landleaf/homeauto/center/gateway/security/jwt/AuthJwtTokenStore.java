/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.landleaf.homeauto.center.gateway.security.jwt;

import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.*;

/**
 * 重写jwtTokenStore，增加存储等自定义逻辑
 */
@Slf4j
public class AuthJwtTokenStore implements TokenStore {

    private AuthJwtAccessTokenConverter jwtTokenEnhancer;

    private ApprovalStore approvalStore;

    private RedisUtils redisUtils;

    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
     *
     * @param jwtTokenEnhancer
     */
    public AuthJwtTokenStore(AuthJwtAccessTokenConverter jwtTokenEnhancer, RedisUtils redisUtils) {
        this.jwtTokenEnhancer = jwtTokenEnhancer;
        this.redisUtils = redisUtils;
    }

    /**
     * ApprovalStore to be used to validate and restrict refresh tokens.
     *
     * @param approvalStore the approvalStore to set
     */
    public void setApprovalStore(ApprovalStore approvalStore) {
        this.approvalStore = approvalStore;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return jwtTokenEnhancer.extractAuthentication(jwtTokenEnhancer.decode(token));
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = convertAccessToken(tokenValue);
        if (jwtTokenEnhancer.isRefreshToken(accessToken)) {
            throw new InvalidTokenException("Encoded token is a refresh token");
        }
        return accessToken;
    }

    private OAuth2AccessToken convertAccessToken(String tokenValue) {
        return jwtTokenEnhancer.extractAccessToken(tokenValue, jwtTokenEnhancer.decode(tokenValue));
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        // gh-807 Approvals (if any) should only be removed when Refresh Tokens are removed (or expired)
        log.info("AuthJwtTokenStore 增加移除JwtToken逻辑");

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        log.info("AuthJwtTokenStore 增加存储refreshToken逻辑");
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        OAuth2AccessToken encodedRefreshToken = convertAccessToken(tokenValue);
        OAuth2RefreshToken refreshToken = createRefreshToken(encodedRefreshToken);
        if (approvalStore != null) {
            OAuth2Authentication authentication = readAuthentication(tokenValue);
            if (authentication.getUserAuthentication() != null) {
                String userId = authentication.getUserAuthentication().getName();
                String clientId = authentication.getOAuth2Request().getClientId();
                Collection<Approval> approvals = approvalStore.getApprovals(userId, clientId);
                Collection<String> approvedScopes = new HashSet<String>();
                for (Approval approval : approvals) {
                    if (approval.isApproved()) {
                        approvedScopes.add(approval.getScope());
                    }
                }
                if (!approvedScopes.containsAll(authentication.getOAuth2Request().getScope())) {
                    return null;
                }
            }
        }
        return refreshToken;
    }

    private OAuth2RefreshToken createRefreshToken(OAuth2AccessToken encodedRefreshToken) {
        if (!jwtTokenEnhancer.isRefreshToken(encodedRefreshToken)) {
            throw new InvalidTokenException("Encoded token is not a refresh token");
        }
        if (encodedRefreshToken.getExpiration() != null) {
            return new DefaultExpiringOAuth2RefreshToken(encodedRefreshToken.getValue(),
                    encodedRefreshToken.getExpiration());
        }
        return new DefaultOAuth2RefreshToken(encodedRefreshToken.getValue());
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        remove(token.getValue());
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        // gh-807 Approvals (if any) should only be removed when Refresh Tokens are removed (or expired)
        log.info("AuthJwtTokenStore 增加通过refreshToken移除token逻辑");
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        // jwttoken返回null,查找token 另外实现  这里不改，改了就破坏原有逻辑了
        // We don't want to accidentally issue a token, and we have no way to reconstruct the refresh token
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return Collections.emptySet();
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return Collections.emptySet();
    }

    public void setTokenEnhancer(AuthJwtAccessTokenConverter tokenEnhancer) {
        this.jwtTokenEnhancer = tokenEnhancer;
    }

    private void remove(String token) {
        if (approvalStore != null) {
            OAuth2Authentication auth = readAuthentication(token);
            String clientId = auth.getOAuth2Request().getClientId();
            Authentication user = auth.getUserAuthentication();
            if (user != null) {
                Collection<Approval> approvals = new ArrayList<Approval>();
                for (String scope : auth.getOAuth2Request().getScope()) {
                    approvals.add(new Approval(user.getName(), clientId, scope, new Date(), ApprovalStatus.APPROVED));
                }
                approvalStore.revokeApprovals(approvals);
            }
        }
    }
}
