package com.landleaf.homeauto.center.oauth.web.controller.web;


import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.service.ISysRolePermissionScopService;
import com.landleaf.homeauto.center.oauth.service.ISysUserRoleService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 后台账号角色权限范围表 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/auth/sys-role-permission-scop")
@Api(value = "/sys-role-permission-scop", tags = {"后台账号角色权限范围操作"})
public class SysRolePermissionScopController extends BaseController {
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRolePermissionScopService sysRolePermissionScopService;

    @ApiOperation(value = "获取用户权限范围", notes = "获取用户权限范围", consumes = "application/json")
    @GetMapping(value = "/paths")
    public Response getUserPaths(@RequestParam String userId) {
        List<String> paths = Lists.newArrayList();
        SysUserRole userRole = sysUserRoleService.getByUserAndRole(userId);
        if (userRole != null) {
            List<String> scopPaths = sysRolePermissionScopService.queryPathsByRoleId(userRole.getRoleId());
            paths.addAll(scopPaths);
        }

        return returnSuccess(paths);
    }
}
