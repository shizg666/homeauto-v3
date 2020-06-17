package com.landleaf.homeauto.common.util;

import com.landleaf.homeauto.common.config.auth.login.context.TokenContext;
import com.landleaf.homeauto.common.config.auth.login.token.Token;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lokiy
 * @date 2019/8/19 12:07
 * @description:
 */
public class TokenContextUtil {


    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getUser() {
        if (TokenContext.getToken() == null) {
            return "0_0";
        }
        String userId = StringUtils.isBlank(TokenContext.getToken().getUserId()) ? "0" :
                TokenContext.getToken().getUserId();
        String userType = StringUtils.isBlank(TokenContext.getToken().getUserType()) ? "0" :
                TokenContext.getToken().getUserType();
        return userType + "_" + userId;
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getUserId() {
        if (TokenContext.getToken() == null) {
            return "0";
        }
        return StringUtils.isBlank(TokenContext.getToken().getUserId()) ? "0" :
                TokenContext.getToken().getUserId();
    }

    public static Token getWebUser() {
        Token token = TokenContext.getToken();
        if (TokenContext.getToken() == null) {
            return new Token();
        }
        return token;
    }
}
