package com.landleaf.homeauto.center.oauth.web.controller.web;


import com.landleaf.homeauto.center.oauth.service.ISysRoleService;
import com.landleaf.homeauto.center.oauth.service.ISysUserRoleService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.po.oauth.SysRole;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;
    @GetMapping("/user/role")
    public Response<SysRole> getSysUserRole() {
        String userId = TokenContext.getToken().getUserId();
        SysUserRole userRole = sysUserRoleService.getByUserAndRole(userId);
        if(userRole!=null){
            return returnSuccess(sysRoleService.getById(userRole.getRoleId()));
        }
        return returnSuccess();
    }
}
