package com.landleaf.homeauto.center.oauth.web.controller;


import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.cache.SysRoleCacheProvider;
import com.landleaf.homeauto.center.oauth.cache.SysUserRoleCacheProvider;
import com.landleaf.homeauto.center.oauth.cache.UserInfoCacheProvider;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.center.oauth.service.ISysRolePermissionScopService;
import com.landleaf.homeauto.center.oauth.service.ISysUserService;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.sysuser.*;
import com.landleaf.homeauto.common.domain.po.oauth.*;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台账号表 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/sys-user")
@Api(value = "/sys-user", description = "后台账号操作")
public class SysUserController extends BaseController {

    @Autowired
    private UserInfoCacheProvider userInfoCacheProvider;
    @Autowired
    private SysRoleCacheProvider sysRoleCacheProvider;
    @Autowired
    private SysUserRoleCacheProvider sysUserRoleCacheProvider;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private ISysRolePermissionScopService sysRolePermissionScopService;


    @GetMapping(value = "/userinfo")
    public SysUser getSysUserInfo(@RequestParam("userId") String userId) {
        return userInfoCacheProvider.getUserInfo(userId);
    }

    @GetMapping(value = "/userinfo/name")
    public List<SysUser> getSysUserByName(@RequestParam("name") String name) {
        return sysUserService.getSysUserByName(name);
    }

    @GetMapping(value = "/userinfo/complex")
    public SysUserInfoComplexDTO getSysUserInfoComplex(@RequestParam("userId") String userId) {
        SysUserInfoComplexDTO result = new SysUserInfoComplexDTO();
        SysUser userInfo = userInfoCacheProvider.getUserInfo(userId);
        SysUserRole userRole = sysUserRoleCacheProvider.getUserRole(userId);
        List<SysPermission> sysUserPermissions = sysPermissionService.getSysUserPermissions(userId, null);
        List<SysRolePermissionScop> sysRolePermissionScops = sysRolePermissionScopService.getPermissionScopByRoleId(userRole.getRoleId());
        SysRole sysRole = sysRoleCacheProvider.getUserRole(userRole.getRoleId());
        result.setSysPermissions(sysUserPermissions);
        result.setSysUser(userInfo);
        result.setSysRole(sysRole);
        result.setSysRolePermissionScops(sysRolePermissionScops);
        return result;
    }

    @RequestMapping(value = "/personal/information", method = RequestMethod.GET)
    public Response getPersonalInformation(@RequestParam("userId") String userId) {
        return returnSuccess(sysUserService.getPersonalInformation(userId));
    }

    @PostMapping("/personal/avatar")
    public Response updateAvatar(@RequestBody SysUserUpdateAvatarReqDTO requstBody) {
        userInfoCacheProvider.remove(requstBody.getId());
        boolean updateAvatar = sysUserService.updateAvatar(requstBody.getId(), requstBody.getAvatar());
        userInfoCacheProvider.getUserInfo(requstBody.getId());
        return returnSuccess();
    }

    @PostMapping("/personal/update")
    Response updatePersonalInfo(@RequestBody SysPersonalUpdateReqDTO requstBody) {
        userInfoCacheProvider.remove(requstBody.getUserId());
        sysUserService.updatePersonalInfo(requstBody.getUserId(), requstBody.getMobile(), requstBody.getCode(), requstBody.getName());
        userInfoCacheProvider.getUserInfo(requstBody.getUserId());
        /**
         * 这边是先删
         * 再更新
         * 再刷新
         */
        return returnSuccess();
    }

    @ApiOperation(value = "重置密码")
    @PostMapping(value = "/personal/resetPwd")
    public Response resetPersonalPwd(@RequestBody SysRestPasswordReqDTO requestBody) {
        userInfoCacheProvider.remove(requestBody.getId());
        String newPassword = requestBody.getNewPassword();
        String userId = requestBody.getId();
        String oldPassword = requestBody.getOldPassword();
        sysUserService.resetPersonalPwd(userId, newPassword, oldPassword);
        userInfoCacheProvider.getUserInfo(requestBody.getId());
        return returnSuccess();
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码", consumes = "application/json")
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public Response forgetPassword(@RequestBody SysUserForgetPasswordDTO requestBody) {
        sysUserService.forgetPwd(requestBody);
        return returnSuccess();
    }

    @ApiOperation(value = "分页", notes = "分页", consumes = "application/json")
    @PostMapping(value = "/page")
    public Response<BasePageVO<SysPersonalInformationDTO>> pageListSysUsers(@RequestBody SysUserPageReqDTO requestBody) {
        BasePageVO<SysPersonalInformationDTO> result = sysUserService.pageListSysUsers(requestBody);
        return returnSuccess(result);
    }

    @ApiOperation(value = "修改系统账号", notes = "修改系统账号", consumes = "application/json")
    @PostMapping(value = "/update")
    public Response updateSysUser(@RequestBody SysUserUpdateReqDTO requestBody) {
        //删除缓存
        userInfoCacheProvider.remove(requestBody.getId());
        sysUserRoleCacheProvider.reomve(requestBody.getId());
        //修改
        sysUserService.updateSysUser(requestBody);
        //刷新缓存
        userInfoCacheProvider.getUserInfo(requestBody.getId());
        sysUserRoleCacheProvider.getUserRole(requestBody.getId());
        return returnSuccess();
    }

    @ApiOperation(value = "新建系统账号", notes = "新建系统账号", consumes = "application/json")
    @PostMapping(value = "/add")
    public Response addSysUser(@RequestBody SysUserAddReqDTO requestBody) {
        return returnSuccess(sysUserService.addSysUser(requestBody));
    }

    @ApiOperation(value = "启用/停用", notes = "启用/停用", consumes = "application/json")
    @PostMapping(value = "/update/status")
    public Response updateStatus(@RequestBody SysUserUpdateStatusReqDTO requestBody) {
        userInfoCacheProvider.remove(requestBody.getUserId());
        sysUserService.updateStatus(requestBody);
        userInfoCacheProvider.getUserInfo(requestBody.getUserId());
        return returnSuccess();
    }


    /**
     * mc使用
     */
    @ApiOperation("根据ids获取系统用户信息")
    @PostMapping("/list/user-ids")
    public Response<List<SysUser>> getSysUserByIds(@RequestBody List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return returnSuccess(Lists.newArrayList());
        }
        List<SysUser> sysUsers = (List<SysUser>) sysUserService.listByIds(ids);
        return returnSuccess(sysUsers);
    }


    @ApiOperation("根据path获取系统用户下拉菜单")
    @PostMapping("/user-scope")
    public Response<List<SelectedVO>> getUserScopeByPath(@RequestBody List<String> paths) {
        List<SelectedVO> result = sysUserService.getUserScopeByPath(paths);
        return returnSuccess(result);
    }


    @ApiOperation("根据名称模糊查询匹配用户列表")
    @GetMapping("/name/list")
    public Response<List<SelectedVO>> getUserListByName(@RequestParam(value = "name", required = false) String name) {

        return returnSuccess(sysUserService.getUserListByName(name));
    }


}
