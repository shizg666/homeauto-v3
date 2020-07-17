package com.landleaf.homeauto.center.oauth.security.extend.provider;

import cn.hutool.crypto.digest.BCrypt;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppUserDetailsService;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendAppAuthenticationToken;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.util.Objects;

import static com.landleaf.homeauto.common.constance.ErrorCodeEnumConst.PASSWORD_INPUT_ERROE;

/**
 * app登录认证处理类
 *
 * @author wenyilu
 */
@Slf4j
public class ExtendAppAuthorizeProvider implements AuthenticationProvider {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker preAuthenticationChecks = new ExtendAppAuthorizeProvider.DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new ExtendAppAuthorizeProvider.DefaultPostAuthenticationChecks();

    private ExtendAppUserDetailsService appUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof ExtendAppAuthenticationToken)) {
            throw new InternalAuthenticationServiceException("Authentication can not cast of AppAuthenticationToken");
        }
        ExtendAppAuthenticationToken authenticationToken = (ExtendAppAuthenticationToken) authentication;
        String mobile = authenticationToken.getName();

        String password = Objects.toString(authenticationToken.getCredentials(), "");
        UserDetails userDetails = appUserDetailsService.loadUserByMobile(mobile);
        //校验密码是否正确或其它校验
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(PASSWORD_INPUT_ERROE);
        }
        if (userDetails == null) {
            throw new BusinessException(ErrorCodeEnumConst.USER_NOT_FOUND);
        }
        if (!BCrypt.checkpw(password, userDetails.getPassword())) {
            throw new BusinessException(PASSWORD_INPUT_ERROE);
        }
        this.preAuthenticationChecks.check(userDetails);
        this.postAuthenticationChecks.check(userDetails);
        ExtendAppAuthenticationToken appAuthenticationToken = new ExtendAppAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        appAuthenticationToken.setDetails(authenticationToken.getDetails());
        return appAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ExtendAppAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public ExtendAppAuthorizeProvider appUserDetailsService(ExtendAppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
        return this;
    }


    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                log.debug("User account credentials have expired");
                throw new CredentialsExpiredException(ExtendAppAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
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
                throw new LockedException(ExtendAppAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            } else if (!user.isEnabled()) {
                log.debug("User account is disabled");
                throw new DisabledException(ExtendAppAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            } else if (!user.isAccountNonExpired()) {
                log.debug("User account is expired");
                throw new AccountExpiredException(ExtendAppAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }
}
