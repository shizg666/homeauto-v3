package com.landleaf.homeauto.center.oauth.asyn;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;


public interface IFutureService {

    @Async
    Future refreshUserCache(String userId);

    @Async
    Future refreshUserCacheRole(String roleId);

    /**
     * 更新权限相关缓存
     * @param permissionId
     * @return
     */
    @Async
    Future refreshSysPermissions(String permissionId);

    @Async
    Future refreshCustomerCache(String customerId);

    @Async
    Future refreshUserRoleCache(String userId);
}
