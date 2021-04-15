package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;
import com.landleaf.homeauto.common.domain.po.oauth.SysRole;
import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermissionScop;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 后台用户综合信息数据实体
 *
 * @author wenyilu*/
@Data
public class SysUserInfoComplexDTO implements Serializable {

    private static final long serialVersionUID = -2600682159641266673L;
    /**
     * 用户基础信息
     */
    private SysUser sysUser;

    /**
     * 用户权限信息
     */
    private List<SysPermission> sysPermissions;

    /**
     * 用户角色信息
     */
    private SysRole sysRole;

    /**
     * 用户权限范围信息
     */
    private List<SysRolePermissionScop> sysRolePermissionScops;

    public SysUserInfoComplexDTO() {
    }

    public SysUserInfoComplexDTO(SysUser sysUser, List<SysPermission> sysPermissions, SysRole sysRole, List<SysRolePermissionScop> sysRolePermissionScops) {
        this.sysUser = sysUser;
        this.sysPermissions = sysPermissions;
        this.sysRole = sysRole;
        this.sysRolePermissionScops = sysRolePermissionScops;
    }
}
