package com.landleaf.homeauto.center.oauth.asyn;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;


public interface IFutureService {


    @Async
    Future refreshCustomerCache(String customerId);

    public Future refreshUserCache(String userId);
}
