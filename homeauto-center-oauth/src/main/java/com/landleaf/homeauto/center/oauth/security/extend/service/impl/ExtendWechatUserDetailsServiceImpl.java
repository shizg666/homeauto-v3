package com.landleaf.homeauto.center.oauth.security.extend.service.impl;

import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.center.oauth.security.extend.service.ExtendWechatUserDetailsService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoWechatRecordService;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoWechatRecord;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 微信用户登录
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
        HomeAutoWechatRecord record = homeAutoWechatRecordService.getRecordByOpenId(openid);
        // 处理记录
        if (record == null) {
            // 新插入一条记录
            record = new HomeAutoWechatRecord();
            record.setOpenId(openid);
            record.setSessionKey(sessionKey);
            homeAutoWechatRecordService.save(record);
        } else {
            record.setSessionKey(sessionKey);
            homeAutoWechatRecordService.updateById(record);
        }
        // 处理用户
        HomeAutoAppCustomer customer = homeAutoAppCustomerService.getCustomerByOpenId(openid);
        if (customer == null) {
            wechatUser = new HomeAutoUserDetails(null, null, openid, String.valueOf(UserTypeEnum.WECHAT.getType()), null, openid);
            return wechatUser;
        }
        wechatUser = new HomeAutoUserDetails(null, customer.getPassword(), openid, String.valueOf(UserTypeEnum.WECHAT.getType()), customer.getId(), openid);
        return wechatUser;
    }
}
