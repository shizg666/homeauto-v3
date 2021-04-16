package com.landleaf.homeauto.center.oauth.service;

import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;

/**
 * @ClassName ITokenService
 * @Author wyl
 * @Date 2020/7/21
 * @Version V1.0
 **/
public interface ITokenService {
    /**
     * 清除相关用户类型下用户下的token
     * @param userId
     * @param userType
     */
    void clearToken(String userId, UserTypeEnum userType);
    /**
     * 清除相关用户类型下用户下的token
     * @param userId
     * @param userTypes
     */
    void clearToken(String userId, UserTypeEnum... userTypes);
}
