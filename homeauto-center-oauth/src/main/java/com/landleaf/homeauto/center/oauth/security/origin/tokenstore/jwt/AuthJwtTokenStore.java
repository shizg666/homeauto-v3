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

package com.landleaf.homeauto.center.oauth.security.origin.tokenstore.jwt;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.common.constance.RedisCacheConst;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
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

import java.lang.reflect.Field;
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
	 * +失效时间为允许刷新的截止时间
	 */
	private Long enableRefreshTime;

	/**
	 * 最多允许同时发放token数
	 */
	private Integer maxTokenCount;

	/**
	 * Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
	 *
	 * @param jwtTokenEnhancer
	 */
	public AuthJwtTokenStore(AuthJwtAccessTokenConverter jwtTokenEnhancer, RedisUtils redisUtils, Long enableRefreshTime, Integer maxTokenCount) {
		this.jwtTokenEnhancer = jwtTokenEnhancer;
		this.redisUtils = redisUtils;
		this.enableRefreshTime = enableRefreshTime;
		this.maxTokenCount = maxTokenCount;
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
		// 最多允许同时生成token数  maxTokenCount

		log.info("AuthJwtTokenStore 增加存储JwtToken逻辑,根据access_token:userType:userId形式存储");
		HomeAutoUserDetails principal = (HomeAutoUserDetails) authentication.getPrincipal();
		String source = principal.getSource();
        UserTypeEnum userTypeEnum = UserTypeEnum.getEnumByType(Integer.parseInt(source));
        String uniqueId =null;
        try {
            Field uniqueProperty = principal.getClass().getDeclaredField(userTypeEnum.getUniquePropertyName());
            //打开私有访问
            uniqueProperty.setAccessible(true);
            uniqueId = (String) uniqueProperty.get(principal);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // app以及web登录的唯一标记是userId,wechat 为openId
        String key = String.format(RedisCacheConst.USER_TOKEN,source,uniqueId);
		HomeAutoToken homeAutoToken = new HomeAutoToken();
		homeAutoToken.setUserType(source);
        homeAutoToken.setOpenId(principal.getOpenId());
		homeAutoToken.setUserId(principal.getUserId());
		homeAutoToken.setAccessToken(token.getValue());
		homeAutoToken.setCreateTime(new Date());
		homeAutoToken.setUserName(principal.getUsername());
		homeAutoToken.setExpireTime(token.getExpiration());
		homeAutoToken.setEnableRefreshTime(enableRefreshTime+token.getExpiration().getTime());
		redisUtils.addMap(key,token.getValue(),homeAutoToken);
        // 控制token数量
		try {
			controlMaxTokenCount(source,uniqueId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	private void controlMaxTokenCount(String userType,String uniqueId) {
		String key = String.format(RedisCacheConst.USER_TOKEN,userType,uniqueId);
		Map<Object, Object> map = redisUtils.getMap(key);
		int userTokenSize = map.size();
		if (userTokenSize > maxTokenCount) {
			List<HomeAutoToken> tmpList = Lists.newArrayList();
			for (Map.Entry entry : map.entrySet()) {
				Object value = entry.getValue();
				log.info("取出token值,{}",JSON.toJSONString(value));
				HomeAutoToken homeAutoToken = JSON.parseObject(JSON.toJSONString(value),HomeAutoToken.class);
				tmpList.add(homeAutoToken);
			}
			tmpList.sort(Comparator.comparing(HomeAutoToken::getEnableRefreshTime));
			//控制token数量，删除多余 token
			for (int i = 0; i < userTokenSize - maxTokenCount; i++) {
				redisUtils.hdel(key,tmpList.get(i).getAccessToken());
			}
		}
		log.debug(String.format("%s-%stoken数量为%s", uniqueId, userType, userTokenSize));
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
		if (encodedRefreshToken.getExpiration()!=null) {
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
