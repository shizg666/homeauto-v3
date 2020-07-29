package com.landleaf.homeauto.center.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.mapper.SysRolePermissionMapper;
import com.landleaf.homeauto.center.oauth.service.ISysRolePermissionService;
import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermission;
import com.landleaf.homeauto.common.enums.DelFlagEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 后台账号角色权限表 服务实现类
 * </p>
 *
 * @author wyl
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Override
    @Transactional
    public void updateRolePermissions(String roleId, List<String> permissionIds) {
        UpdateWrapper<SysRolePermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", roleId);
        boolean deleteExist = remove(updateWrapper);
        List<SysRolePermission> saveDatas = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(permissionIds)) {
            for (String permissionId : permissionIds) {
                SysRolePermission data = new SysRolePermission();
                data.setPermissionId(permissionId);
                data.setRoleId(roleId);
                saveDatas.add(data);
            }
            saveBatch(saveDatas);
        }
    }

    @Override
    public List<SysRolePermission> getRolePermissionByRole(String roleId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);

        return list(queryWrapper);
    }

    @Override
    public List<SysRolePermission> queryAllRolePermission() {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        return list(queryWrapper);
    }
}
