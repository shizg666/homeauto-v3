package com.landleaf.homeauto.common.web.context;

import com.landleaf.homeauto.common.domain.HomeAutoToken;

/**
 * 存取当前请求token
 */
public class TokenContext {

    /**
     * 用threadLocal存储当前登陆的token
     */
    private static final ThreadLocal<HomeAutoToken> TOKEN_HOLDER = new ThreadLocal<HomeAutoToken>();

    /**
     * 获取当前token
     */
    public static HomeAutoToken getToken() {
        return TOKEN_HOLDER.get();
    }

    /**
     * 设置当前token
     */
    public static void setToken(HomeAutoToken token) {
        TOKEN_HOLDER.set(token);
    }

    /**
     * 清除token
     */
    public static void remove() {
        TOKEN_HOLDER.remove();
    }
}
