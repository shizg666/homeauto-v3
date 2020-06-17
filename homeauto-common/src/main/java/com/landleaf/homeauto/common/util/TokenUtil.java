package com.landleaf.homeauto.common.util;

import com.landleaf.homeauto.common.config.auth.login.token.Token;
import com.landleaf.homeauto.common.config.auth.login.util.Hex;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * Token工具类
 */
public class TokenUtil {
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;  // 默认过期时长,单位秒

    /**
     * 生成token
     */
    public static Token buildToken(String subject) {
        return buildToken(subject, DEFAULT_EXPIRE);
    }

    public static Token buildToken(String subject, long expire) {
        return buildToken(subject, expire, getKey());
    }

    public static Token buildToken(String subject, long expire, Key key) {
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        Date now = new Date(nowMillis);//颁发时间
        Date expireDate = new Date(nowMillis + 1000 * expire);  // 单位毫秒
//        Map<String, Object> claims = new HashMap<String, Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
//        claims.put("uid", "DSSFAWDWADAS...");
//        claims.put("user_name", "admin");
//        claims.put("nick_name", "DASDA121");
        String access_token = Jwts.builder()
                //.setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                //.setId(id)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setSubject(subject)
                .signWith(key)
                .setExpiration(expireDate)
                .setIssuedAt(now)
                .compact();
        Token token = new Token();
        // token.setTokenKey(Hex.encodeToString(key.getEncoded()));
        token.setAccessToken(access_token);
        String[] userIdAndType = subject.split("@");
        token.setUserId(userIdAndType[0]);
        token.setUserType(userIdAndType[1]);
        token.setExpireTime(expireDate);
        return token;
    }

    /**
     * 解析token
     */
    public static String parseToken(String token, String hexKey) {

        JwtParser jwtParser = Classes.newInstance("com.landleaf.smarthome.auth.login.SmarthomeJwtParser");
        Jws<Claims> claimsJws1 = jwtParser.setSigningKey(parseHexKey(hexKey)).parseClaimsJws(token);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(parseHexKey(hexKey)).parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    /**
     * 解析token中包含的subject
     */
    public static String parseTokenForSubject(String token, String hexKey) {

        JwtParser jwtParser = Classes.newInstance("com.landleaf.smarthome.auth.login.SmarthomeJwtParser");
        Jws<Claims> claimsJws = jwtParser.setSigningKey(parseHexKey(hexKey)).parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    /**
     * 生成Key
     */
    public static Key getKey() {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * 生成16进制的key
     */
    public static String getHexKey() {
        return getHexKey(getKey());
    }

    public static String getHexKey(Key key) {
        return Hex.encodeToString(key.getEncoded());
    }

    /**
     * 把16进制的key转成Key
     */
    public static Key parseHexKey(String hexKey) {
        if (hexKey == null || hexKey.trim().isEmpty()) {
            return null;
        }
        SecretKey key = Keys.hmacShaKeyFor(Hex.decode(hexKey));
        return key;
    }

}
