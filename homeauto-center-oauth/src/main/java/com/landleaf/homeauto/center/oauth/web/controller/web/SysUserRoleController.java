package com.landleaf.homeauto.center.oauth.web.controller.web;


import com.landleaf.homeauto.center.oauth.cache.SysRoleCacheProvider;
import com.landleaf.homeauto.center.oauth.cache.SysUserRoleCacheProvider;
import com.landleaf.homeauto.common.context.TokenContext;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.po.oauth.SysRole;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;
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
@RequestMapping("/auth/sys-user-role")
@Api(value = "/sys-user-role", tags = {"后台账号角色关联操作"})
public class SysUserRoleController extends BaseController {
    @Autowired
    private SysUserRoleCacheProvider sysUserRoleCacheProvider;
    @Autowired
    private SysRoleCacheProvider sysRoleCacheProvider;

    @GetMapping("/user/role")
    public Response<SysRole> getSysUserRole() {
        String userId = TokenContext.getToken().getUserId();
        SysUserRole userRole = sysUserRoleCacheProvider.getUserRole(userId);
        if(userRole!=null){
            return returnSuccess(sysRoleCacheProvider.getUserRole(userRole.getRoleId()));
        }
        return returnSuccess();
    }
}
