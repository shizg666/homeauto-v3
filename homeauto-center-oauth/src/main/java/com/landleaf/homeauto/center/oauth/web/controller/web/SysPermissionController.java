package com.landleaf.homeauto.center.oauth.web.controller.web;


import com.landleaf.homeauto.center.oauth.asyn.IFutureService;
import com.landleaf.homeauto.center.oauth.cache.AllSysPermissionsProvider;
import com.landleaf.homeauto.center.oauth.cache.ListUserPermissionsMenuProvider;
import com.landleaf.homeauto.center.oauth.cache.SysPermisssionCacheProvider;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.context.TokenContext;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionForAddDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionForUpdateDTO;
import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private SysPermisssionCacheProvider sysPermisssionCacheProvider;
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired(required = false)
    private IFutureService futureService;

    @Autowired
    private ListUserPermissionsMenuProvider listUserPermissionsMenuProvider;
    @Autowired
    private AllSysPermissionsProvider allSysPermissionsProvider;

    @GetMapping("/user/permissions/userId")
    public List<SysPermission> getSysUserPermissions(@RequestParam("userId") String userId) {
        return sysPermissionService.getSysUserPermissions(userId, null);
    }

    @ApiOperation(value = "添加", notes = "添加", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Response add(@RequestBody SysPermissionForAddDTO requestBody) {
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(requestBody, permission);
        listUserPermissionsMenuProvider.remove();
        allSysPermissionsProvider.remove();
        return returnSuccess(sysPermissionService.savePermission(permission));
    }


    @ApiOperation(value = "更新", notes = "更新", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response update(@RequestBody SysPermissionForUpdateDTO requestBody) {
        sysPermisssionCacheProvider.remove(requestBody.getId());
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(requestBody, permission);
        sysPermissionService.updatePermission(permission);
        sysPermisssionCacheProvider.getSysUserPermissions(requestBody.getId());
        listUserPermissionsMenuProvider.remove();
        allSysPermissionsProvider.remove();
        return returnSuccess();
    }

    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody List<String> ids) {
        boolean b = sysPermissionService.delete(ids);
        futureService.refreshSysPermissions(null);
        listUserPermissionsMenuProvider.remove();
        allSysPermissionsProvider.remove();
        return returnSuccess();
    }

    @ApiOperation(value = "列出当前用户的菜单及页面权限列表", notes = "列出当前用户的菜单及页面权限列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/user/menu/page/list", method = RequestMethod.GET)
    public Response listUserMenuAndPagePermissions() {
        return returnSuccess(sysPermissionService.listUserMenuAndPagePermissions(TokenContext.getToken().getUserId()));
    }
    @ApiOperation(value = "列出当前登录用户的所有功能列表", notes = "列出当前登录用户的所有功能列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/user/permissions", method = RequestMethod.GET)
    public Response listUserPermissions(@RequestParam(required = false, value = "permissionType") Integer permissionType) {
        return returnSuccess(listUserPermissionsMenuProvider.getListUserPermissionsFromCache(TokenContext.getToken().getUserId(), permissionType));
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
        SysPermission sysPermission = sysPermisssionCacheProvider.getSysUserPermissions(permisssionId);
        return returnSuccess(sysPermission);
    }
}
