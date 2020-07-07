package com.landleaf.homeauto.center.oauth.security.origin.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName AuthNoEncryptPasswordEncoder
 * @Description: 不加密方式
 * @Author wyl
 * @Date 2020/6/2
 * @Version V1.0
 **/

public class AuthNoEncryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return (String) charSequence;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals((String) charSequence);
    }

}