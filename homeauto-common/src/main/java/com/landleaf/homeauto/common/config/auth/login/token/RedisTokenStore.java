package com.landleaf.homeauto.common.config.auth.login.token;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.constance.RedisCacheConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtil;
import com.landleaf.homeauto.common.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.landleaf.homeauto.common.constance.ErrorCodeEnumConst.LOGIN_EXPIRED;
import static com.landleaf.homeauto.common.constance.RedisCacheConst.KEY_PRE_TOKEN;

/**
 * redis存储token的实现
 */
@Component
public class RedisTokenStore implements TokenStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTokenStore.class);

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public String getTokenKey() {
        String tokenKey = (String) redisUtil.get(RedisCacheConst.KEY_TOKEN_KEY);
        if (tokenKey == null || tokenKey.trim().isEmpty()) {
            tokenKey = TokenUtil.getHexKey();
            redisUtil.set(RedisCacheConst.KEY_TOKEN_KEY, tokenKey);
        }
        return tokenKey;
    }

    @Override
    public Token createNewToken(String userId, String userType) {
        return createNewToken(userId, userType, TokenUtil.DEFAULT_EXPIRE);
    }

    @Override
    public Token createNewToken(String userId, String userType, long expire) {
        return createNewToken(userId, userType, expire, 86400L, 20);
    }

    @Override
    public Token createNewToken(String userId, String userType, long expire, int maxToken) {
        return createNewToken(userId, userType, expire, 86400L, maxToken);
    }

    @Override
    public Token createNewToken(String userId, String userType, long expire, long refreshTokenExpire, int maxToken) {
        String subject = String.format("%s@%s", userId, userType);
        String tokenKey = getTokenKey();
        LOGGER.debug("-------------------------------------------");
        LOGGER.debug("构建token使用tokenKey：" + tokenKey);
        LOGGER.debug("-------------------------------------------");
        Token token = TokenUtil.buildToken(subject, expire, TokenUtil.parseHexKey(tokenKey));
        if (storeToken(token, refreshTokenExpire)) {
            String key = KEY_PRE_TOKEN + userId + ":" + userType;
            Map<Object, Object> map = redisUtil.getMap(key);
            int userTokenSize = map.size();
            if (userTokenSize > maxToken) {
                List<Token> tmpList = Lists.newArrayList();
                for (Map.Entry entry : map.entrySet()) {
                    String entryKey = (String) entry.getKey();
                    Token value = (Token) entry.getValue();
                    tmpList.add(value);
                }
                tmpList.sort(Comparator.comparing(Token::getRefreshToken));
                //控制token数量，删除多余 token
                for (int i = 0; i < userTokenSize - maxToken; i++) {
                    redisUtil.hdel(key, tmpList.get(i).getAccessToken());
                }
            }
            LOGGER.debug(String.format("%s-%stoken数量为%s", userId, userType, userTokenSize));
            return token;
        }
        return null;
    }

    /**
     * @param token
     * @param refreshTokenExpire 单位秒
     * @return
     */
    @Override
    public boolean storeToken(Token token, long refreshTokenExpire) {
        // 存储access_token
        long refreshToken = refreshTokenExpire * 1000L + System.currentTimeMillis();
        token.setRefreshToken(refreshToken);
        String key = KEY_PRE_TOKEN + token.getUserId() + ":" + token.getUserType();
        String item = token.getAccessToken();
        redisUtil.addMap(key, item, token);
        return true;
    }

    @Override
    public boolean storeToken(Token token) {
        return storeToken(token, 86400L);
    }

    @Override
    public Token findToken(String userId, String userType, String access_token) {
        try {
            if (userId != null && !userId.trim().isEmpty()) {
                String key = KEY_PRE_TOKEN + userId + ":" + userType;
                Object hget = redisUtil.hget(key, access_token);
                Token token = null;
                if (hget != null) {
                    token = (Token) hget;
                    long refreshToken = token.getRefreshToken();
                    if (refreshToken < System.currentTimeMillis()) {
                        redisUtil.hdel(key, access_token);
                        throw new BusinessException(LOGIN_EXPIRED);
                    }
                    return token;
                }
            }
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Token> findTokensByUserId(String userId, String userType) {
        if (userId == null || userId.trim().isEmpty()) {
            return null;
        }
        List<Token> tokens = new ArrayList<Token>();
        String key = KEY_PRE_TOKEN + userId + ":" + userType;
        Map<Object, Object> map = redisUtil.getMap(key);
        if (map != null || map.size() > 0) {
            map.forEach((k, v) -> {
                Token tmp = (Token) v;
                tokens.add(tmp);
            });
        }
        return tokens;
    }

    @Override
    public void removeToken(String userId, String userType, String access_token) {
        String key = KEY_PRE_TOKEN + userId + ":" + userType;
        redisUtil.hdel(key, access_token);
    }

    @Override
    public void removeTokensByUserId(String userId, String userType) {
        redisUtil.del(KEY_PRE_TOKEN + userId + ":" + userType);
    }

    @Override
    public void refreshTokenExpire(String userId, String userType, String access_token, long refreshTokenExpire) {
        String key = KEY_PRE_TOKEN + userId + ":" + userType;
        long refreshToken = System.currentTimeMillis() + refreshTokenExpire * 1000L;
        Object hget = redisUtil.hget(key, access_token);
        if (hget != null) {
            Token tmp = (Token) hget;
            tmp.setRefreshToken(refreshToken);
            redisUtil.hset(key, access_token, tmp);
            LOGGER.info("token:续约完成{}", access_token);
        }
    }

    private String[] setToArray(Set<String> set) {
        if (set == null) {
            return null;
        }
        return set.toArray(new String[set.size()]);
    }
}
