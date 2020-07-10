package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;
import com.landleaf.homeauto.common.domain.vo.oauth.TreeNodeVO;

import java.util.List;

/**
 * <p>
 * 后台账号操作权限表 服务类
 * </p>
 * @author wenyilu
 */
public interface ISysPermissionService extends IService<SysPermission> {

    List<SysPermission> getSysUserPermissions(String userId, Integer permissionType);

    List<SysPermission> queryPermissionByIds(List<String> permissionIds);

    boolean savePermission(SysPermission permission);

    boolean updatePermission(SysPermission permission);

    List<SysPermission> queryAllPermission();

    List<TreeNodeVO> listUserPermissions(String userId, Integer permissionType);

    List<TreeNodeVO> listAllPermissions(Integer permissionType);

    boolean delete(List<String> ids);
}
