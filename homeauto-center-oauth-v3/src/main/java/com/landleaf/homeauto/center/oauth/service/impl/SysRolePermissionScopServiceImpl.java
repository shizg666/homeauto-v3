package com.landleaf.homeauto.center.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.mapper.SysRolePermissionScopMapper;
import com.landleaf.homeauto.center.oauth.service.ISysRolePermissionScopService;
import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermissionScop;
import com.landleaf.homeauto.common.enums.DelFlagEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台账号角色权限范围表 服务实现类
 * </p>
 */
@Service
public class SysRolePermissionScopServiceImpl extends ServiceImpl<SysRolePermissionScopMapper, SysRolePermissionScop> implements ISysRolePermissionScopService {

    @Override
    public List<SysRolePermissionScop> queryScopByRoleIds(List<String> roleIds) {
        List<SysRolePermissionScop> result = Lists.newArrayList();
        QueryWrapper<SysRolePermissionScop> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", roleIds);
        List<SysRolePermissionScop> queryResult = list(queryWrapper);
        if (!CollectionUtils.isEmpty(queryResult)) {
            result.addAll(queryResult);
        }
        return result;
    }

    @Override
    public void updateRolePermissionScop(String roleId, List<String> scopPaths) {

        UpdateWrapper<SysRolePermissionScop> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", roleId);
        boolean deleteExist = remove(updateWrapper);

        List<SysRolePermissionScop> saveDatas = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(scopPaths)) {
            for (String path : scopPaths) {

                SysRolePermissionScop data = new SysRolePermissionScop();
                BeanUtils.copyProperties(path, data);
                data.setRoleId(roleId);
                data.setPath(path);
                saveDatas.add(data);
            }
            saveBatch(saveDatas);
        }
    }

    @Override
    public List<SysRolePermissionScop> queryAllPermissionScop() {
        QueryWrapper<SysRolePermissionScop> queryWrapper = new QueryWrapper<>();
        return list(queryWrapper);
    }

    @Override
    public List<SysRolePermissionScop> getPermissionScopByRoleId(String roleId) {
        List<String> roleIds = Lists.newArrayList();
        roleIds.add(roleId);
        return queryScopByRoleIds(roleIds);
    }

    @Override
    public List<String> queryPathsByRoleId(String roleId) {
        List<String> result = Lists.newArrayList();
        QueryWrapper<SysRolePermissionScop> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<SysRolePermissionScop> list = list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            result.addAll(list.stream().map(i -> {
                return i.getPath();
            }).collect(Collectors.toList()));
        }
        return result;
    }
}
