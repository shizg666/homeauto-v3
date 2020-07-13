package com.landleaf.homeauto.center.oauth.web.controller;


import com.landleaf.homeauto.common.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 后台账号角色权限表 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/sys-role-permission")
@Api(value = "/sys-role-permission", tags = {"后台账号角色权限操作"})
public class SysRolePermissionController extends BaseController {

}
