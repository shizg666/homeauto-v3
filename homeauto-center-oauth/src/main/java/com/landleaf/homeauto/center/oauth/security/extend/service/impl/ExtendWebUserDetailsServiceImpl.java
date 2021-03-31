package com.landleaf.homeauto.center.oauth.security.extend.service.impl;

import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWebUserDetailsService;
import com.landleaf.homeauto.center.oauth.service.ISysUserService;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.NO_ANY_MANU_PERMISSION_ERROR;

/**
 *
 * @author pilo
 */
@Service
public class ExtendWebUserDetailsServiceImpl implements ExtendWebUserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByEmailOrPhone(String account,Integer plat) {

        SysUser sysUser = sysUserService.resolveSysUser(account,plat);
        if(sysUser==null){
            return null;
        }
        HomeAutoUserDetails webUser = new HomeAutoUserDetails(null, sysUser.getPassword(), account, String.valueOf(plat),sysUser.getId());

        return webUser;
    }


}
