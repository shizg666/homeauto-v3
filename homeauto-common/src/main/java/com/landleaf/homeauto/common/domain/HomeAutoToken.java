package com.landleaf.homeauto.common.domain;

import lombok.Data;

import java.util.Date;

@Data
public class HomeAutoToken {

    private String accessToken;  // token

    private String userId;  // 用户id

    private String openId;  // 第三方Id

    private String userName;  // 用户

    private String userType;//用户类型

    private long enableRefreshTime;  // 有效期内视为token有效可刷新

    private Date expireTime;  // 过期时间

    private Date createTime;

    private Date updateTime;


}
