package com.landleaf.homeauto.center.device.asyn;

import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class FutureService implements IFutureService {

    @Autowired
    private RedisUtils redisUtil;


    @Override
    public Future<String> getAppControlCache(String messageId, Long timeout) {
        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = currentTimeMillis + timeout;
        Future<String> future;

        String cache = null;

        try {
            String key = RedisCacheConst.ADAPTER_APP_MSG_WAIT_ACK_PREFIX.concat(messageId);
            while (System.currentTimeMillis() < expireTimeMillis) {
                Thread.sleep(300L);
                if (redisUtil.hasKey(key)) {
                    cache = (String) redisUtil.get(key);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        future = new AsyncResult<>(cache);

        return future;
    }

}
