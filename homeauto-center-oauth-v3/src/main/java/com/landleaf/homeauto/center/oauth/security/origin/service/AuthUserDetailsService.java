package com.landleaf.homeauto.center.oauth.security.origin.service;

import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义获取用户信息处理类
 * 权限刷新只能重新定义,后关联token，原始的不可修改，是个坑
 *
 * @ClassName AuthUserDetailsService
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/
@Slf4j
@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("usernameis:" + username);
         //查询数据库操作
         if(!username.equals("admin")){
            throw new UsernameNotFoundException("the user is not found");
         }else{
            //用户角色也应在数据库中获取
             String role = "ROLE_ADMIN";
             List<SimpleGrantedAuthority> authorities = new ArrayList<>();
             authorities.add(new SimpleGrantedAuthority(role));
             //线上环境应该通过用户名查询数据库获取加密后的密码
            String password = passwordEncoder.encode("123456");
            return new org.springframework.security.core.userdetails.User(username,password, authorities);        }


    }
}

