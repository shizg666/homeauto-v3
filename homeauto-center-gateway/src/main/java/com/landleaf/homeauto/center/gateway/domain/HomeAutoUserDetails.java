package com.landleaf.homeauto.center.gateway.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @ClassName HomeautoUserDetails
 * @Description: 扩展springsecurity的UserDetail
 * @Author wyl
 * @Date 2020/6/9
 * @Version V1.0
 **/
public class HomeAutoUserDetails implements UserDetails {

    private static final long serialVersionUID = -7979833199343460404L;
    private Collection<GrantedAuthority> authorities;
    private String password;
    private String username;
    private String userId;
    private String source;
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public HomeAutoUserDetails() {
    }

    public HomeAutoUserDetails(Collection<GrantedAuthority> authorities, String password, String username, String source, String userId) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.source = source;
        this.userId=userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}


