package com.landleaf.homeauto.center.oauth.asyn;

import com.landleaf.homeauto.center.oauth.cache.RefreshCacheProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author pilo
 */
@Service
public class FutureService implements IFutureService {

    public static final Logger LOGGER = LoggerFactory.getLogger(FutureService.class);

    @Autowired
    private RefreshCacheProvider refreshCacheProvider;


    /**
     * 异步刷新缓存
     */
    @Override
    @Async
    public Future refreshUserCache(String userId) {
        Future<Boolean> future = null;
        try {
            boolean refresh = refreshCacheProvider.refresh(userId);
            future = (Future<Boolean>) new AsyncResult<Boolean>(refresh);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return future;
    }


    @Override
    public Future refreshCustomerCache(String customerId) {
        Future<Boolean> future = null;
        try {
            boolean refresh = refreshCacheProvider.refreshCustomerCache(customerId);
            future = (Future<Boolean>) new AsyncResult<Boolean>(refresh);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return future;
    }


}
