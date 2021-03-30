package com.landleaf.homeauto.center.oauth.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.center.oauth.domain.HomeAutoUserDetails;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.ISysUserService;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerRegisterResDTO;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 登录成功后业务处理类
 * @author wenyilu
 */
@Slf4j
@Service
public class LoginSuccessService {


    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;


    /**
     *
     * @param userId
     * @param source
     * @param access_token
     */
    public Object buildLoginSuccessData(String userId, String source, String access_token, HomeAutoUserDetails principal){
        Object result = new Object();
        if(Integer.parseInt(source)==UserTypeEnum.APP.getType()||Integer.parseInt(source)==UserTypeEnum.APP_NO_SMART.getType()){
            return homeAutoAppCustomerService.buildAppLoginSuccessData(userId, access_token);
        }else  if(Integer.parseInt(source)==UserTypeEnum.WEB_DEPLOY.getType()||Integer.parseInt(source)==UserTypeEnum.WEB_OPERATION.getType()){
            return  sysUserService.buildWebLoginSuccessData(userId,access_token);
        }else  if(Integer.parseInt(source)==UserTypeEnum.WECHAT.getType()){
            return  homeAutoAppCustomerService.buildWechatLoginSuccessData(userId,access_token,principal.getOpenId());
        }
        return result ;
    }

    private void buildWebLoginSuccessData(String userId, String access_token) {


    }

    private void buildAppLoginSuccessData(String userId, String access_token) {
    }

}
