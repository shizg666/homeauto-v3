package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermission;

import java.util.List;

/**
 * <p>
 * 后台账号角色权限表 服务类
 * </p>
 *
 * @author wyl
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    void updateRolePermissions(String id, List<String> permissionIds);

    List<SysRolePermission> getRolePermissionByRole(String roleId);

    List<SysRolePermission> queryAllRolePermission();

    void deleteRolePermissions(List<String> roleIds);
}
