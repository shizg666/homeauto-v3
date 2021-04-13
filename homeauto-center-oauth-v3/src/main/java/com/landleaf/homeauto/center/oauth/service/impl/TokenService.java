package com.landleaf.homeauto.center.oauth.service.impl;

import com.landleaf.homeauto.center.oauth.service.ICustomerThirdSourceService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.ITokenService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.po.oauth.CustomerThirdSource;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.enums.oauth.CustomerThirdTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
            case WEB_DEPLOY:
            case WEB_OPERATION:
            case APP:
                key = String.format(RedisCacheConst.USER_TOKEN, userTypeEnum.getType(), uniqueId);
                redisUtils.del(key);
                break;
            case WECHAT:
                HomeAutoAppCustomer customer = homeAutoAppCustomerService.getById(userId);
                if (customer == null ) {
                    return;
                }
                List<CustomerThirdSource> openIds = customerThirdSourceService.getRecordByUserId(userId, CustomerThirdTypeEnum.WECHAT.getCode());
                if(!CollectionUtils.isEmpty(openIds) ){
                    for (CustomerThirdSource openId : openIds) {
                        key = String.format(RedisCacheConst.USER_TOKEN, userTypeEnum.getType(), openId.getOpenId());
                        redisUtils.del(key);
                    }
                }
                break;
            default:
                break;

        }
        key = String.format(RedisCacheConst.USER_TOKEN, userTypeEnum.getType(), uniqueId);
        redisUtils.del(key);
    }

    @Override
    public void clearSysUserToken(String userId) {
        UserTypeEnum.sysUserTypeEnumMap.forEach((k,v)->{
            redisUtils.del(String.format(RedisCacheConst.USER_TOKEN, k, userId));
        });
    }
}
