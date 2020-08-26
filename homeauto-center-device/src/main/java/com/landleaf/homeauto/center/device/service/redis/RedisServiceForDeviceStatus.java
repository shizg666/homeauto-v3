package com.landleaf.homeauto.center.device.service.redis;

import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备状态相关的redis操作
 *
 * @author Yujiumin
 * @version 2020/8/26
 */
@Service
public class RedisServiceForDeviceStatus {

    private RedisUtils redisUtils;

    public void insertDeviceStatus() {

    }

    public Object getDeviceStatus(String key) {
        return redisUtils.get(key);
    }

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }
}
