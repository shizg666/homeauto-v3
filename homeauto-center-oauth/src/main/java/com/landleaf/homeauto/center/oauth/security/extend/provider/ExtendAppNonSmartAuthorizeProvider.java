package com.landleaf.homeauto.center.oauth.security.extend.provider;

import cn.hutool.crypto.digest.BCrypt;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppNonSmartUserDetailsService;
import com.landleaf.homeauto.center.oauth.security.extend.token.ExtendAppNonSmartAuthenticationToken;
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

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.PASSWORD_INPUT_ERROE;
import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.USER_NOT_FOUND;

/**
 * app-nonSmart登录认证处理类
 *
 * @author wenyilu
 */
@Slf4j
public class ExtendAppNonSmartAuthorizeProvider implements AuthenticationProvider {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker preAuthenticationChecks = new ExtendAppNonSmartAuthorizeProvider.DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new ExtendAppNonSmartAuthorizeProvider.DefaultPostAuthenticationChecks();

    private ExtendAppNonSmartUserDetailsService extendAppNonSmartUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof ExtendAppNonSmartAuthenticationToken)) {
            throw new InternalAuthenticationServiceException("Authentication can not cast of ExtendAppNonSmartAuthenticationToken");
        }
        ExtendAppNonSmartAuthenticationToken authenticationToken = (ExtendAppNonSmartAuthenticationToken) authentication;
        String mobile = authenticationToken.getName();

        String password = Objects.toString(authenticationToken.getCredentials(), "");
        UserDetails userDetails = extendAppNonSmartUserDetailsService.loadUserByMobile(mobile);
        //校验密码是否正确或其它校验
        if (StringUtils.isEmpty(password)) {
            throw new InsufficientAuthenticationException(PASSWORD_INPUT_ERROE.getMsg());
        }
        if (userDetails == null) {
            throw new InsufficientAuthenticationException(USER_NOT_FOUND.getMsg());
        }
        if (!BCrypt.checkpw(password, userDetails.getPassword())) {
            throw new InsufficientAuthenticationException(PASSWORD_INPUT_ERROE.getMsg());
        }
        this.preAuthenticationChecks.check(userDetails);
        this.postAuthenticationChecks.check(userDetails);
        ExtendAppNonSmartAuthenticationToken appAuthenticationToken = new ExtendAppNonSmartAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        appAuthenticationToken.setDetails(authenticationToken.getDetails());
        return appAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ExtendAppNonSmartAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public ExtendAppNonSmartAuthorizeProvider extendAppNonSmartUserDetailsService(ExtendAppNonSmartUserDetailsService extendAppNonSmartUserDetailsService) {
        this.extendAppNonSmartUserDetailsService = extendAppNonSmartUserDetailsService;
        return this;
    }


    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                log.debug("User account credentials have expired");
                throw new CredentialsExpiredException(ExtendAppNonSmartAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired", "User credentials have expired"));
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
                throw new LockedException(ExtendAppNonSmartAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            } else if (!user.isEnabled()) {
                log.debug("User account is disabled");
                throw new DisabledException(ExtendAppNonSmartAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            } else if (!user.isAccountNonExpired()) {
                log.debug("User account is expired");
                throw new AccountExpiredException(ExtendAppNonSmartAuthorizeProvider.this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }
    }
}
