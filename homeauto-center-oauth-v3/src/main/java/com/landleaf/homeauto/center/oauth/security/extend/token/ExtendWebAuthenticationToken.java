package com.landleaf.homeauto.center.oauth.security.extend.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


/**
 * web登录token的封装类
 *
 * @author wenyilu
 */
public class ExtendWebAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 3350753550064326311L;
    /**
     * 用户信息-邮箱
     */
    private final Object principal;
    /**
     * 密码
     */
    private Object credentials;

    /**
     * 未认证的构造函数
     *
     * @param principal
     * @param credentials
     */
    public ExtendWebAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    /**
     * 认证成功的构造函数
     *
     * @param principal
     * @param credentials
     * @param authorities
     */
    public ExtendWebAuthenticationToken(Object principal,
                                        Object credentials,
                                        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }


    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            return;
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
