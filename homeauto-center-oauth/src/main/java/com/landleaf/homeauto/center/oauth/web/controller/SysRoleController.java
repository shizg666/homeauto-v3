package com.landleaf.homeauto.center.oauth.web.controller;


import com.landleaf.homeauto.center.oauth.cache.RefreshCacheProvider;
import com.landleaf.homeauto.center.oauth.cache.SysPermissionScopCacheProvider;
import com.landleaf.homeauto.center.oauth.cache.SysRoleCacheProvider;
import com.landleaf.homeauto.center.oauth.cache.SysRolePermisssionCacheProvider;
import com.landleaf.homeauto.center.oauth.service.ISysRoleService;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.sysrole.SysRoleAddComplexReqDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.sysrole.SysRolePageReqDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.sysrole.SysRoleUpdateComplexReqDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.sysrole.SysRoleUpdateStatusReqDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台账号角色表 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/auth/sys-role")
@Api(value = "/sys-role", tags = {"后台账号角色操作"})
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private RefreshCacheProvider refreshCacheProvider;
    @Autowired
    private SysPermissionScopCacheProvider sysPermissionScopCacheProvider;
    @Autowired
    private SysRoleCacheProvider sysRoleCacheProvider;
    @Autowired
    private SysRolePermisssionCacheProvider sysRolePermisssionCacheProvider;


    @ApiOperation(value = "角色基本信息", notes = "角色基本信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("/role/info")
    public Response getSysRoleInfo(@RequestParam("roleId") String roleId) {
        return returnSuccess(sysRoleService.getSysRoleInfo(roleId));
    }

    @ApiOperation(value = "查看角色及权限详情", notes = "查看角色及权限详情")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("/role/complex/info")
    public Response getSysRoleComplexInfo(@RequestParam("roleId") String roleId) {
        return returnSuccess(sysRoleService.getSysRoleComplexInfo(roleId));
    }

    @ApiOperation(value = "角色管理列表查询")
    @PostMapping(value = "/role/page")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    public Response pageListSysRoles(@RequestBody SysRolePageReqDTO reqDTO) {
        return returnSuccess(sysRoleService.pageListSysRoles(reqDTO));
    }

    @ApiOperation(value = "修改系统角色", notes = "修改系统角色", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping(value = "/role/update")
    public Response updateSysUser(@RequestBody SysRoleUpdateComplexReqDTO requestBody) {
        String roleId = requestBody.getSysRole().getId();
        //移除角色相关缓存
        sysRoleCacheProvider.remove(roleId);
        sysRolePermisssionCacheProvider.remove(roleId);
        sysPermissionScopCacheProvider.remove(roleId);
        boolean updateSysRole = sysRoleService.updateSysRole(requestBody);
        //更新缓存
        refreshCacheProvider.refreshUserCacheRole(requestBody.getSysRole().getId());
        return returnSuccess();
    }

    @ApiOperation(value = "新增系统角色", notes = "新增系统角色", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping(value = "/role/add")
    public Response addSysUser(@RequestBody SysRoleAddComplexReqDTO requestBody) {
        sysRoleService.addSysRole(requestBody);
        return returnSuccess();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("/role/delete")
    public Response deleteSysRoles(@RequestBody List<String> ids) {
        //移除角色相关缓存
        sysRoleCacheProvider.remove(null);
        sysRolePermisssionCacheProvider.remove(null);
        sysPermissionScopCacheProvider.remove(null);
        sysRoleService.deleteSysRoles(ids);
        //更新缓存
        refreshCacheProvider.refreshUserCacheRole(null);
        return returnSuccess();
    }

    @ApiOperation(value = "角色下拉列表查询")
    @ApiImplicitParam(name = "状态1：启用，2：停用", value = "status", paramType = "path", required = true)
    @GetMapping(value = "/role/list/{status}")
    public Response pageListSysRoles(@PathVariable("status") Integer status) {
        return returnSuccess(sysRoleService.listSysRolesByStatus(status));
    }

    @ApiOperation(value = "启用/停用", notes = "启用/停用", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping(value = "/update/status")
    public Response updateStatus(@RequestBody SysRoleUpdateStatusReqDTO requestBody) {
        //移除角色相关缓存
        sysRoleCacheProvider.remove(requestBody.getRoleId());
        sysRolePermisssionCacheProvider.remove(requestBody.getRoleId());
        sysPermissionScopCacheProvider.remove(requestBody.getRoleId());
        sysRoleService.updateStatus(requestBody);
        //更新缓存
        refreshCacheProvider.refreshUserCacheRole(requestBody.getRoleId());
        return returnSuccess();
    }
}
