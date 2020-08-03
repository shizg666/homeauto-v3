package com.landleaf.homeauto.center.oauth.web.controller.web;


import com.landleaf.homeauto.center.oauth.cache.RefreshCacheProvider;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 刷新用户相关缓存
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/refresh/cache")
@Api(value = "/refresh/cache", tags = {"刷新用户相关缓存"})
public class RefreshUserCacheController extends BaseController {
    @Autowired
    private RefreshCacheProvider refreshCacheProvider;

    @ApiOperation(value = "刷新客户缓存", notes = "刷新客户缓存", consumes = "application/json")
    @GetMapping("/customer")
    public Response refreshCustomerCache(@RequestParam(value = "customerId", required = false) String customerId) {
        refreshCacheProvider.refreshCustomerCache(customerId);
        return returnSuccess();
    }

    @ApiOperation(value = "刷新后台账号基本信息缓存", notes = "刷新后台账号基本信息缓存", consumes = "application/json")
    @GetMapping("/user")
    public Response refreshSysUserCache(@RequestParam(value = "userId", required = false) String userId) {
        refreshCacheProvider.refresh(userId);
        refreshCacheProvider.refreshUserRoleCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "刷新角色相关缓存", notes = "刷新角色相关缓存", consumes = "application/json")
    @GetMapping("/role")
    public Response refreshRoleCache(@RequestParam(value = "roleId", required = false) String roleId) {
        refreshCacheProvider.refreshUserCacheRole(roleId);
        return returnSuccess();
    }

    @ApiOperation(value = "刷新权限缓存", notes = "刷新权限缓存", consumes = "application/json")
    @GetMapping("/permission")
    public Response refreshPermissionCache(@RequestParam(value = "permissionId", required = false) String permissionId) {
        refreshCacheProvider.refreshSysPermissions(permissionId);
        return returnSuccess();
    }
}
