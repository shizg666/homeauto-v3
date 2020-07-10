package com.landleaf.homeauto.common.domain;

import java.util.Date;

public class HomeAutoToken {

    private String accessToken;  // token

    private String userId;  // 用户id
    private String userName;  // 用户

    private String userType;//用户类型

    // private long tokenKey;  // 生成token时的key

    private long enableRefreshTime;  // 有效期内视为token有效可刷新

    private Date expireTime;  // 过期时间

    private Date createTime;

    private Date updateTime;


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

    public long getEnableRefreshTime() {
        return enableRefreshTime;
    }

    public void setEnableRefreshTime(long enableRefreshTime) {
        this.enableRefreshTime = enableRefreshTime;
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
