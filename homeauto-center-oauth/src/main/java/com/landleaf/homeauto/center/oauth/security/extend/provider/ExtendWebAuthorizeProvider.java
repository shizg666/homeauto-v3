package com.landleaf.homeauto.center.oauth.security.extend.provider;

import com.landleaf.homeauto.center.oauth.security.extend.adapter.ExtendWebSecurityConfigurerAdapter;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWebUserDetailsService;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendAppAuthenticationToken;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendWebAuthenticationToken;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

import static com.landleaf.homeauto.common.constance.ErrorCodeEnumConst.PASSWORD_INPUT_ERROE;
import static com.landleaf.homeauto.common.constance.ErrorCodeEnumConst.USER_NOT_FOUND;

/**
 * web登录认证处理类
 *
 * @author wenyilu
 */
@Slf4j
public class ExtendWebAuthorizeProvider implements AuthenticationProvider {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker preAuthenticationChecks = new ExtendWebAuthorizeProvider.DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new ExtendWebAuthorizeProvider.DefaultPostAuthenticationChecks();

    private ExtendWebUserDetailsService extendWebUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof ExtendWebAuthenticationToken)) {
            throw new InternalAuthenticationServiceException("Authentication can not cast of WebAuthenticationToken");
        }
        ExtendWebAuthenticationToken authenticationToken = (ExtendWebAuthenticationToken) authentication;
        String account = authenticationToken.getName();

        String password = Objects.toString(authenticationToken.getCredentials(), "");
        UserDetails userDetails = extendWebUserDetailsService.loadUserByEmailOrPhone(account);
        //校验密码是否正确或其它校验
        if (StringUtils.isEmpty(password)) {
            throw new InsufficientAuthenticationException(PASSWORD_INPUT_ERROE.getMsg());
        }
        if (userDetails == null) {

            throw new UsernameNotFoundException(USER_NOT_FOUND.getMsg());
        }
        if (!StringUtils.equalsIgnoreCase(PasswordUtil.md5Hex(password), userDetails.getPassword())) {
            throw new InsufficientAuthenticationException(PASSWORD_INPUT_ERROE.getMsg());
        }
        this.preAuthenticationChecks.check(userDetails);
        this.postAuthenticationChecks.check(userDetails);
        ExtendWebAuthenticationToken extendWebAuthenticationToken = new ExtendWebAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        extendWebAuthenticationToken.setDetails(authenticationToken.getDetails());
        return extendWebAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ExtendWebAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public ExtendWebAuthorizeProvider extendWebUserDetailsService(ExtendWebUserDetailsService extendWebUserDetailsService) {
        this.extendWebUserDetailsService = extendWebUserDetailsService;
        return this;
    }


    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                log.debug("User account credentials have expired");
                throw new CredentialsExpiredException(ExtendWebAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
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
                throw new LockedException(ExtendWebAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            } else if (!user.isEnabled()) {
                log.debug("User account is disabled");
                throw new DisabledException(ExtendWebAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            } else if (!user.isAccountNonExpired()) {
                log.debug("User account is expired");
                throw new AccountExpiredException(ExtendWebAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }
}
