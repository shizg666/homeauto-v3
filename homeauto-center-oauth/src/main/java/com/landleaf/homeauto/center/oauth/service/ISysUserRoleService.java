package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;

import java.util.List;

/**
 * <p>
 * 后台账号角色关联表 服务类
 * </p>
 *
 * @author wyl
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 更新用户角色绑定关系
     *
     * @param userId
     * @param roleId
     * @return
     */
    boolean updateUserRole(String userId, String roleId);

    SysUserRole getByUserAndRole(String userId);

    List<SysUserRole> queryAllUserRole();
}
