package com.landleaf.homeauto.center.oauth.web.controller;


import com.landleaf.homeauto.center.oauth.cache.SysRoleCacheProvider;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.po.oauth.SysRole;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 后台账号角色关联表 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/sys-user-role")
@Api(value = "/sys-user-role", tags = {"后台账号角色关联操作"})
public class SysUserRoleController extends BaseController {
    @Autowired
    private SysRoleCacheProvider userRoleCacheProvider;

    @GetMapping("/user/role")
    public SysRole getSysUserRole(@RequestParam("userId") String userId) {
        return userRoleCacheProvider.getUserRole(userId);
    }
}
