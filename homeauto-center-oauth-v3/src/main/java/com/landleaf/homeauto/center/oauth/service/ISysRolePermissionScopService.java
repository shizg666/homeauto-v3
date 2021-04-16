package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermissionScop;

import java.util.List;

/**
 * <p>
 * 后台账号角色权限范围表 服务类
 * </p>
 *
 * @author wenyilu
 */
public interface ISysRolePermissionScopService extends IService<SysRolePermissionScop> {

    List<SysRolePermissionScop> queryScopByRoleIds(List<String> roleIds);

    void updateRolePermissionScop(String id, List<String> sysRolePermissionScops);

    List<SysRolePermissionScop> queryAllPermissionScop();

    List<SysRolePermissionScop> getPermissionScopByRoleId(String roleId);

    List<String> queryPathsByRoleId(String roleId);

    void removeByRoleIds(List<String> roleIds);
}
