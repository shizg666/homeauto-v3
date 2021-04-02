package com.landleaf.homeauto.center.oauth.schedule;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.KEY_PRE_TOKEN;

/**
 * 定时清除过期token
 **/
@Component
public class ClearExpireUserTokenSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClearExpireUserTokenSchedule.class);

    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void refreshCacheScheduling() {
        LOGGER.info("定时清除过期token,开始时间:{}", new Date());
        int count = 0;
        try {
            Set<String> keys = redisUtils.keys(KEY_PRE_TOKEN + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                for (String key : keys) {
                    try {
                        Map<Object, Object> map = redisUtils.getMap(key);
                        if (map != null && map.size() > 0) {
                            for (Map.Entry entry : map.entrySet()) {
                                Object key1 = entry.getKey();
                                Object value = entry.getValue();
                                HomeAutoToken token = null;
                                if (value != null) {
                                    token = JSON.parseObject(JSON.toJSONString(value), HomeAutoToken.class);
                                    long refreshToken = token.getEnableRefreshTime();
                                    if (refreshToken < System.currentTimeMillis()) {
                                        redisUtils.hdel(key, key1);
                                        count++;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
            System.out.println(keys);
        } catch (Exception e) {
            LOGGER.info("定时清除过期token异常:{}", e.getMessage(), e);
        }
        LOGGER.info("定时清除过期token,共清除过期token:{}个，结束时间:{}", count, new Date());
    }
}
