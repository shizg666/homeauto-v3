package com.landleaf.homeauto.center.oauth.security.extend.service.impl;

import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendAppUserDetailsService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ExtendAppUserDetailsServiceImpl implements ExtendAppUserDetailsService {


    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        HomeAutoAppCustomer customer = homeAutoAppCustomerService.getCustomerByMobile(mobile, AppTypeEnum.SMART.getCode());
        if (customer == null) {
            return null;
        }
        HomeAutoUserDetails appUser = new HomeAutoUserDetails(null, customer.getPassword(), mobile, String.valueOf(UserTypeEnum.APP.getType()), customer.getId());
        return appUser;
    }
}
