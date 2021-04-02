package com.landleaf.homeauto.center.oauth.cache;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.COMMON_EXPIRE;
import static com.landleaf.homeauto.common.constant.RedisCacheConst.KEY_CUSTOMER_INFO;

/**
 * APP客户信息缓存
 *
 * @author pilo*/
@Service
public class CustomerCacheProvider implements CacheProvider {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;

    /**
     * 根据客户ID获取客户信息
     *
     * @param userId 客户ID
     */
    public HomeAutoAppCustomer getCustomer(String userId) {
        boolean hasKey = redisUtils.hasKey(KEY_CUSTOMER_INFO);
        if (hasKey) {
            Object hget = redisUtils.hget(KEY_CUSTOMER_INFO, userId);
            if (hget != null) {
                return JSON.parseObject(JSON.toJSONString(hget), HomeAutoAppCustomer.class);
            }
        }
        return getCustomerByDB(userId);
    }

    private HomeAutoAppCustomer getCustomerByDB(String userId) {
        HomeAutoAppCustomer result = null;
        HomeAutoAppCustomer customer = homeAutoAppCustomerService.getById(userId);
        if (customer != null) {
            result = new HomeAutoAppCustomer();
            BeanUtils.copyProperties(customer, result);
            redisUtils.hset(KEY_CUSTOMER_INFO, userId, result, COMMON_EXPIRE);
        }
        return result;
    }

    /**
     * 缓存所有客户信息
     */
    public void cacheAllCustomer() {
        List<HomeAutoAppCustomer> allCustomers = homeAutoAppCustomerService.list();
        redisUtils.del(KEY_CUSTOMER_INFO);
        if (!CollectionUtils.isEmpty(allCustomers)) {
            Map<String, Object> customerMap = allCustomers.stream().collect(Collectors.toMap(HomeAutoAppCustomer::getId, i -> {
                return i;
            }, (o, n) -> o));
            redisUtils.hmset(KEY_CUSTOMER_INFO, customerMap);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cacheAllCustomer();
    }

    public void remove(String customerId) {
        redisUtils.hdel(KEY_CUSTOMER_INFO, customerId);
    }
}
