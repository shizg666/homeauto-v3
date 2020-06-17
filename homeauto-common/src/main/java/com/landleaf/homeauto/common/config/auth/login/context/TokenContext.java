package com.landleaf.homeauto.common.config.auth.login.context;


import com.landleaf.homeauto.common.config.auth.login.token.Token;

/**
 * 存取当前请求token
 */
public class TokenContext {

    /**
     * 用threadLocal存储当前登陆的token
     */
    private static final ThreadLocal<Token> TOKEN_HOLDER = new ThreadLocal<Token>();

    /**
     * 获取当前token
     */
    public static Token getToken() {
        return TOKEN_HOLDER.get();
    }

    /**
     * 设置当前token
     */
    public static void setToken(Token token) {
        TOKEN_HOLDER.set(token);
    }

    /**
     * 清除token
     */
    public static void remove() {
        TOKEN_HOLDER.remove();
    }
}
