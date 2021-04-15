package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionMenuAndPageDTO;
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

    /**
     * 根据用户获取相应权限类型权限列表
     * @param userId
     * @param permissionType
     * @return
     */
    List<SysPermission> getSysUserPermissions(String userId, Integer permissionType);

    List<SysPermission> queryPermissionByIds(List<String> permissionIds);

    /**
     * 新增权限
     * @param permission
     * @return
     */
    boolean savePermission(SysPermission permission);

    /**
     * 修改权限
     * @param permission
     * @return
     */
    boolean updatePermission(SysPermission permission);

    List<SysPermission> queryAllPermission();

    /**
     * 查询用户权限缓存
     * @param userId
     * @param permissionType  require false
     * @return
     */
    List<TreeNodeVO> listUserPermissions(String userId, Integer permissionType);

    /**
     * 前端查询用户权限菜单列表
     * @param permissionType
     * @return
     */
    List<TreeNodeVO> listAllPermissions(Integer permissionType);

    /**
     * 根据名称列出所有相关权限的树状结构
     * @param name
     * @return
     */
    List<TreeNodeVO> findPermissionsByName(String name);

    /**
     * 根据权限主键批量删除
     * @param ids
     * @return
     */
    boolean delete(List<String> ids);

    /**
     * 列出当前用户的菜单及按钮权限
     * @param userId
     * @return
     */
    SysPermissionMenuAndPageDTO listUserMenuAndPagePermissions(String userId);


}
