package com.landleaf.homeauto.center.device.cache;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.redis.RedisUtils;

/**
 * @className: BaseCacheProvider
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/1
 **/
public abstract class BaseCacheProvider {

    public RedisUtils redisUtils;
    /**
     * 序列化类型-单体
     */
    public static final Integer SINGLE_TYPE = 1;
    /**
     * 序列化类型-集合
     */
    public static final Integer LIST_TYPE = 2;

    Object getBoFromRedis(String key, Integer type, Class classz) {
        if (redisUtils.hasKey(key)) {
            Object o = redisUtils.get(key);
            if (o != null) {
                if (type == 1) {
                    return JSON.parseObject(JSON.toJSONString(o), classz);
                }
                return JSON.parseArray(JSON.toJSONString(o), classz);
            }
        }
        return null;
    }
}
