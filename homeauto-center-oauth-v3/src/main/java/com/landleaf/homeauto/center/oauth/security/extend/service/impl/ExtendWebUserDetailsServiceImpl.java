package com.landleaf.homeauto.center.oauth.security.extend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWebUserDetailsService;
import com.landleaf.homeauto.center.oauth.service.ISysUserRoleService;
import com.landleaf.homeauto.center.oauth.service.ISysUserService;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.NO_ANY_MANU_PERMISSION_ERROR;

/**
 *
 */
@Service
public class ExtendWebUserDetailsServiceImpl implements ExtendWebUserDetailsService {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Override
    public UserDetails loadUserByEmailOrPhone(String account) {


        SysUser sysUser = sysUserService.resolveSysUser(account);
        if(sysUser==null){
            return null;
        }
        HomeAutoUserDetails webUser = new HomeAutoUserDetails(null, sysUser.getPassword(), account, String.valueOf(UserTypeEnum.WEB_DEPLOY.getType()),sysUser.getId());

        SysUserRole userRole = sysUserRoleService.getByUserAndRole(sysUser.getId());
        if(userRole==null){
            throw new InsufficientAuthenticationException(NO_ANY_MANU_PERMISSION_ERROR.getMsg());
        }

        return webUser;
    }


}
