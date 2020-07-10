package com.landleaf.homeauto.center.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.oauth.mapper.SysUserRoleMapper;
import com.landleaf.homeauto.center.oauth.service.ISysUserRoleService;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;
import com.landleaf.homeauto.common.enums.DelFlagEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台账号角色关联表 服务实现类
 * </p>
 *
 * @author wyl
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public boolean updateUserRole(String userId, String roleId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("del_flag", DelFlagEnum.UNDELETE.getType());
        boolean remove = remove(queryWrapper);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        save(sysUserRole);
        //更新缓存

        return true;

    }

    @Override
    public SysUserRole getByUserAndRole(String userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("del_flag", DelFlagEnum.UNDELETE.getType());

        return getOne(queryWrapper);

    }

    @Override
    public List<SysUserRole> queryAllUserRole() {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", DelFlagEnum.UNDELETE.getType());
        return list(queryWrapper);
    }
}
