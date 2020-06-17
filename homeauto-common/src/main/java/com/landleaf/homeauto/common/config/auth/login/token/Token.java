package com.landleaf.homeauto.common.config.auth.login.token;

import java.util.Date;

/**
 * 自定义token对象，可扩展
 * @author wenyilu
 */
public class Token {

    /**
     * 自增主键
     */
    private Integer tokenId;

    /**
     * token
     */
    private String accessToken;

    /**
     *  用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    //用户类型
    private String userType;
// 生成token时的key
    // private long tokenKey;

    /**
     *  有效期内视为token有效可刷新
     */
    private long refreshToken;

    /**
     * 过期时间
     */
    private Date expireTime;

    private Date createTime;

    private Date updateTime;

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    /*public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }*/

    public long getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(long refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
