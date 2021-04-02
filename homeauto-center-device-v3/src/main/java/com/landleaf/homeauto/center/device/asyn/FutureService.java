package com.landleaf.homeauto.center.device.asyn;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDetailDO;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class FutureService implements IFutureService {

    public static final Logger LOGGER = LoggerFactory.getLogger(FutureService.class);
    @Autowired
    private RedisUtils redisUtil;

    @Value("${homeauto.apk.update.single:false}")
    private boolean single;
    @Autowired
    @Lazy
    private IAppService appService;
    @Autowired
    @Lazy
    private IHomeAutoFamilyService homeAutoFamilyService;

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
