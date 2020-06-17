package com.landleaf.homeauto.common.config.auth.login.token;

import java.util.List;

/**
 * 操作token的接口
 * @author wenyilu
 */
public interface TokenStore {

    String getTokenKey();

    Token createNewToken(String userId, String userType);

    Token createNewToken(String userId, String userType, long expire);

    Token createNewToken(String userId, String userType, long expire, int maxToken);

    Token createNewToken(String userId, String userType, long expire, long refreshTokenExpire, int maxToken);

    boolean storeToken(Token token);

    boolean storeToken(Token token, long refreshTokenExpire);

    Token findToken(String userId, String userType, String access_token);

    List<Token> findTokensByUserId(String userId, String userType);

    void removeToken(String userId, String userType, String access_token);

    void removeTokensByUserId(String userId, String userType);

    void refreshTokenExpire(String userId, String userType, String access_token, long refreshTokenExpire);
}
