package com.landleaf.homeauto.center.oauth.service.impl;

import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.ITokenService;
import com.landleaf.homeauto.common.constance.RedisCacheConst;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.StringUtil;
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

    @Override
    public void clearToken(String userId, UserTypeEnum userTypeEnum) {
        String key = null;
        String uniqueId = userId;
        switch (userTypeEnum) {
            case WEB:
                break;
            case APP:
                break;
            case WECHAT:
                HomeAutoAppCustomer customer = homeAutoAppCustomerService.getById(userId);
                if (customer == null || StringUtil.isEmpty(customer.getOpenId())) {
                    return;
                }
                uniqueId = customer.getOpenId();
                break;

        }
        key = String.format(RedisCacheConst.USER_TOKEN, userTypeEnum.getType(), uniqueId);
        redisUtils.del(key);
    }
}
