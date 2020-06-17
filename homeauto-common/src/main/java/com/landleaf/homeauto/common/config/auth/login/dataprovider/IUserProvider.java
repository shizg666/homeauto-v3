package com.landleaf.homeauto.common.config.auth.login.dataprovider;


import com.landleaf.homeauto.common.domain.po.oauth.IUser;
import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;

import java.util.List;

/**
 * 提供用户获取
 * @author wenyilu
 */
public interface IUserProvider {

    /**
     * 根据用户id和类型获取用户信息
     * 包含后台账号与客户
     *
     * @param userId
     * @param userType
     */
    IUser getUserByIdAndType(String userId, String userType);

    /**
     * 根据用户id和类型获取用户信息
     * 包含后台账号与客户
     *
     * @param userId
     * @param userType
     * @param user
     */
    String getUserNameByIUser(String userId, String userType, IUser user);

    /**
     * 获取后台用户权限
     *
     * @param userId 后台账号用户ID
     * @return
     */
    List<SysPermission> getPermissionBySysUser(String userId);

}
