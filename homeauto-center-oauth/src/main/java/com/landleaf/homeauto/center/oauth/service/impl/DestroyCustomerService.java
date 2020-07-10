package com.landleaf.homeauto.center.oauth.service.impl;

import com.landleaf.homeauto.center.oauth.cache.CustomerCacheProvider;
import com.landleaf.homeauto.center.oauth.service.IDestroyCustomerService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.common.constance.RedisCacheConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.oauth.CheckResultVO;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DestroyCustomerService implements IDestroyCustomerService {

    @Autowired
    private IHomeAutoAppCustomerService smarthomeCustomerService;
    @Autowired
    private CustomerCacheProvider customerCacheProvider;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 销毁客户账号
     *
     * @param userId 客户账号ID
     * @return
     */
    @Override
    public Response destroyCustomer(String userId) {
        Response response = new Response();
        CheckResultVO checkResultVO = null;
        try {
            //数据库层面
            checkResultVO = smarthomeCustomerService.destroyCustomer(userId);
            //缓存层面
            /**
             * 1、客户缓存
             * 2、token缓存
             */
            try {
                customerCacheProvider.remove(userId);
                String key = String.format(RedisCacheConst.USER_TOKEN, UserTypeEnum.APP.getType(), userId);
                // TODO 需要注意 因为用的jwttoken,若这里注销账户需要清除相应记录，那么登录认证时需要校验用户是否仍然有效和token是否存在
                redisUtil.del(key);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            response.setSuccess(true);
        } catch (BusinessException e) {
            log.error(e.getMessage());
            response = new Response();
            response.setSuccess(false);
            response.setErrorCode(e.getErrorCode());
            response.setErrorMsg(e.getMessage());
        }
        return response;
    }
}
