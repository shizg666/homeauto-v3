package com.landleaf.homeauto.center.oauth.service.impl;

import com.landleaf.homeauto.center.oauth.service.ICustomerThirdSourceService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.ITokenService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.enums.oauth.CustomerThirdTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName TokenService
 * @Description: token 业务相关操作
 * @Author wyl
 * @Date 2020/7/21
 * @Version V1.0
 **/
@Service
public class TokenService implements ITokenService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;
    @Autowired
    private ICustomerThirdSourceService customerThirdSourceService;

    @Override
    public void clearToken(String userId, UserTypeEnum userTypeEnum) {
        String key = null;
        String uniqueId = userId;
        switch (userTypeEnum) {
            case WEB:
            case APP_NO_SMART:
            case APP:
            case WECHAT:
                HomeAutoAppCustomer customer = homeAutoAppCustomerService.getById(userId);
                if (customer == null ) {
                    return;
                }
                uniqueId = customerThirdSourceService.getRecord(userId, CustomerThirdTypeEnum.WECHAT.getCode()).getOpenId();
                break;
            default:
                break;

        }
        key = String.format(RedisCacheConst.USER_TOKEN, userTypeEnum.getType(), uniqueId);
        redisUtils.del(key);
    }
}
