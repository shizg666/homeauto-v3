package com.landleaf.homeauto.center.oauth.web.controller.web;


import com.landleaf.homeauto.center.oauth.cache.RolePermissionsMenuProvider;
import com.landleaf.homeauto.center.oauth.service.ISysCacheService;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.center.oauth.service.ISysUserRoleService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionForAddDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionForUpdateDTO;
import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;
import com.landleaf.homeauto.common.domain.vo.oauth.TreeNodeVO;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.PERMISSION_BY_TYPE_PRE;
import static com.landleaf.homeauto.common.constant.RedisCacheConst.ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE;

/**
 * <p>
 * 后台账号操作权限表 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/auth/sys-permission")
@Api(value = "/sys-permission", tags = {"后台账号权限操作"})
public class SysPermissionController extends BaseController {
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private ISysCacheService sysCacheService;
    @Autowired
    private RolePermissionsMenuProvider rolePermissionsMenuProvider;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @GetMapping("/user/permissions/userId")
    public List<SysPermission> getSysUserPermissions(@RequestParam("userId") String userId) {
        return sysPermissionService.getSysUserPermissions(userId, null);
    }

    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@RequestBody SysPermissionForAddDTO requestBody) {
        sysCacheService.deleteCache(String.valueOf(requestBody.getPermissionType()),PERMISSION_BY_TYPE_PRE);
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(requestBody, permission);
        return returnSuccess(sysPermissionService.savePermission(permission));
    }


    @ApiOperation(value = "更新", notes = "更新", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody SysPermissionForUpdateDTO requestBody) {
        sysCacheService.deleteCacheBitch(PERMISSION_BY_TYPE_PRE,ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE);
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(requestBody, permission);
        sysPermissionService.updatePermission(permission);
        return returnSuccess();
    }

    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody List<String> ids) {
        sysCacheService.deleteCacheBitch(ROLE_PERMISSIONS_MENU_PROVIDER_KEY_PRE,PERMISSION_BY_TYPE_PRE);
        boolean b = sysPermissionService.delete(ids);
        return returnSuccess();
    }

    @ApiOperation(value = "列出当前用户的菜单及页面权限列表", notes = "列出当前用户的菜单及页面权限列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/user/menu/list", method = RequestMethod.GET)
    public Response listUserMenuAndPagePermissions() {
        return returnSuccess(sysPermissionService.listUserMenuAndPagePermissions(TokenContext.getToken().getUserId()));
    }
    @ApiOperation(value = "列出当前登录用户的所有功能列表", notes = "列出当前登录用户的所有功能列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/user/permissions", method = RequestMethod.GET)
    public Response listUserPermissions(@RequestParam(required = false, value = "permissionType") Integer permissionType) {
        SysUserRole userRole = sysUserRoleService.getByUserAndRole(TokenContext.getToken().getUserId());
        return returnSuccess(rolePermissionsMenuProvider.getListUserPermissionsFromCache(userRole.getRoleId(),userRole.getUserId(), permissionType));
    }

    /**
     * @description 查询所有权限列表
     * @author wyl
     */
    @ApiOperation(value = "查询所有权限列表,返回前端所需要的树状结构", notes = "查询所有权限列表，返回前端所需要的树状结构")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Response listResources(@RequestParam(required = false, value = "permissionType") Integer permissionType) {
        return returnSuccess(sysPermissionService.listAllPermissions(permissionType));
    }

    /**
     * @param permisssionId
     * @description 根据权限编码查询权限详细信息
     * @author wyl
     */
    @ApiOperation(value = "根据权限编码查询权限详细信息", notes = "根据权限编码查询权限详细信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public Response findResource(@RequestParam("permisssionId") String permisssionId) {
        SysPermission sysPermission = sysPermissionService.getById(permisssionId);
        return returnSuccess(sysPermission);
    }
    /**
     * @description 根据菜单名称查询
     * @author wyl
     */
    @ApiOperation(value = "根据菜单名称查询", notes = "根据菜单名称查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/permissions/name", method = RequestMethod.GET)
    public Response findPermissionsByName(@RequestParam(value = "name",required = false) String name) {
        List<TreeNodeVO> permissions = sysPermissionService.findPermissionsByName(name);
        return returnSuccess(permissions);
    }
}
