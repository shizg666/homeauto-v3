package com.landleaf.homeauto.center.oauth.mapper;

import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermissionScop;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.mysql.HomeAutoBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台账号角色权限范围表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 */
public interface SysRolePermissionScopMapper extends HomeAutoBaseMapper<SysRolePermissionScop> {

    List<SysUser> getUserScopeByPath(@Param("paths") List<String> paths);


    List<String> getPathsByUserId(String userId);
}
