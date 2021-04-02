package com.landleaf.homeauto.center.oauth.security.extend.service.impl;

import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWechatUserDetailsService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoWechatRecordService;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.enums.oauth.CustomerThirdTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 微信用户登录
 * @author pilo
 */
@Service
public class ExtendWechatUserDetailsServiceImpl implements ExtendWechatUserDetailsService {

    @Autowired
    private IHomeAutoWechatRecordService homeAutoWechatRecordService;
    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;

    @Override
    public UserDetails loadUserByOpenId(String openid, String sessionKey) {
        HomeAutoUserDetails wechatUser = null;
        homeAutoWechatRecordService.saveOrUpdateRecord(openid,sessionKey);
        // 处理用户
        HomeAutoAppCustomer customer = homeAutoAppCustomerService.getCustomerByOpenId(openid, CustomerThirdTypeEnum.WECHAT.getCode());
        if (customer == null) {
            wechatUser = new HomeAutoUserDetails(null, null, null,null, String.valueOf(UserTypeEnum.WECHAT.getType()), openid);
            return wechatUser;
        }
        wechatUser = new HomeAutoUserDetails(null, customer.getPassword(), customer.getMobile(),  customer.getId(),String.valueOf(UserTypeEnum.WECHAT.getType()), openid);
        return wechatUser;
    }
}
