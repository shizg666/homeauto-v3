package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.redis.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constance.RedisCacheConst.COMMON_EXPIRE;
import static com.landleaf.homeauto.common.constance.RedisCacheConst.KEY_CUSTOMER_INFO;

/**
 * APP客户信息缓存
 **/
@Service
public class CustomerCacheProvider implements CacheProvider {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;

    /**
     * 根据客户ID获取客户信息
     *
     * @param userId 客户ID
     */
    public HomeAutoAppCustomer getSmarthomeCustomer(String userId) {
        boolean hasKey = redisUtil.hasKey(KEY_CUSTOMER_INFO);
        if (hasKey) {
            Object hget = redisUtil.hget(KEY_CUSTOMER_INFO, userId);
            if (hget != null) {
                return JSON.parseObject(JSON.toJSONString(hget), HomeAutoAppCustomer.class);
            }
        }
        return getSmarthomeCustomerByDB(userId);
    }

    private HomeAutoAppCustomer getSmarthomeCustomerByDB(String userId) {
        HomeAutoAppCustomer result = null;
        HomeAutoAppCustomer smarthomeCustomer = homeAutoAppCustomerService.getById(userId);
        if (smarthomeCustomer != null) {
            result = new HomeAutoAppCustomer();
            BeanUtils.copyProperties(smarthomeCustomer, result);
            redisUtil.hset(KEY_CUSTOMER_INFO, userId, result, COMMON_EXPIRE);
        }
        return result;
    }

    /**
     * 缓存所有客户信息
     */
    public void cacheAllCustomer() {
        List<HomeAutoAppCustomer> allCustomers = homeAutoAppCustomerService.list();
        redisUtil.del(KEY_CUSTOMER_INFO);
        if (!CollectionUtils.isEmpty(allCustomers)) {
            Map<String, Object> customerMap = allCustomers.stream().collect(Collectors.toMap(HomeAutoAppCustomer::getId, i -> {
                return i;
            }, (o, n) -> o));
            redisUtil.hmset(KEY_CUSTOMER_INFO, customerMap);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cacheAllCustomer();
    }

    public void remove(String customerId) {
        redisUtil.hdel(KEY_CUSTOMER_INFO, customerId);
    }
}
