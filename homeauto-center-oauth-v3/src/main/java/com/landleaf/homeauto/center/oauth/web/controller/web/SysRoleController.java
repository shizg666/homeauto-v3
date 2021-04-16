package com.landleaf.homeauto.center.oauth.web.controller.web;


import com.landleaf.homeauto.center.oauth.cache.*;
import com.landleaf.homeauto.center.oauth.service.ISysCacheService;
import com.landleaf.homeauto.center.oauth.service.ISysRoleService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.web.BaseController;
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

import static com.landleaf.homeauto.common.constant.RedisCacheConst.*;

/**
 * <p>
 * 后台账号角色表 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/auth/sys-role")
@Api(value = "/auth/sys-role", tags = {"后台账号角色操作"})
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysCacheService sysCacheService;

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
        sysCacheService.deleteCache(requestBody.getSysRole().getId(),ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE);
        boolean updateSysRole = sysRoleService.updateSysRole(requestBody);
        //更新缓存
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
        sysCacheService.deleteCache(null,ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE);
        sysRoleService.deleteSysRoles(ids);
        //更新缓存
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
        sysCacheService.deleteCache(requestBody.getRoleId(),ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE);
        sysRoleService.updateStatus(requestBody);
        return returnSuccess();
    }
}
