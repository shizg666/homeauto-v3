package com.landleaf.homeauto.center.oauth.security.extend.provider;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWechatUserDetailsService;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendWechatAuthenticationToken;
import com.landleaf.homeauto.center.oauth.util.WechatUtil;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.util.Objects;

/**
 * 微信登录认证处理类
 *
 * @author wenyilu
 */
@Slf4j
public class ExtendWechatAuthorizeProvider implements AuthenticationProvider {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker preAuthenticationChecks = new ExtendWechatAuthorizeProvider.DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new ExtendWechatAuthorizeProvider.DefaultPostAuthenticationChecks();

    private ExtendWechatUserDetailsService wechatUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof ExtendWechatAuthenticationToken)) {
            throw new InternalAuthenticationServiceException("Authentication can not cast of ExtendWechatAuthenticationToken");
        }
        ExtendWechatAuthenticationToken authenticationToken = (ExtendWechatAuthenticationToken) authentication;
        String code = Objects.toString(authentication.getPrincipal(), null);
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);

        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        if (openid == null) {
            throw new InsufficientAuthenticationException("openid获取不到！" + JSONObject.toJSONString(SessionKeyOpenId));
        }
        if (sessionKey == null) {
            throw new InsufficientAuthenticationException("sessionKey获取不到！" + JSONObject.toJSONString(SessionKeyOpenId));
        }

        // 查询用户是否存在找出userId
        UserDetails customer = wechatUserDetailsService.loadUserByOpenId(openid,sessionKey);

        this.preAuthenticationChecks.check(customer);
        this.postAuthenticationChecks.check(customer);
        ExtendWechatAuthenticationToken wechatAuthenticationToken = new ExtendWechatAuthenticationToken(customer, null, customer.getAuthorities());
        wechatAuthenticationToken.setDetails(authenticationToken.getDetails());
        return wechatAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ExtendWechatAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public ExtendWechatAuthorizeProvider wechatUserDetailsService(ExtendWechatUserDetailsService wechatUserDetailsService) {
        this.wechatUserDetailsService = wechatUserDetailsService;
        return this;
    }


    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                log.debug("User account credentials have expired");
                throw new CredentialsExpiredException(ExtendWechatAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
            }
        }
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        private DefaultPreAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                log.debug("User account is locked");
                throw new LockedException(ExtendWechatAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            } else if (!user.isEnabled()) {
                log.debug("User account is disabled");
                throw new DisabledException(ExtendWechatAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            } else if (!user.isAccountNonExpired()) {
                log.debug("User account is expired");
                throw new AccountExpiredException(ExtendWechatAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }
}
