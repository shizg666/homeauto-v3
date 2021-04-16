package com.landleaf.homeauto.center.oauth.cache;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 更新用户相关缓存（用户信息、角色信息、权限信息、范围信息）
 **/
@Component
public class RefreshCacheProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshCacheProvider.class);

    @Autowired
    private UserInfoCacheProvider userInfoCacheProvider;
    @Autowired
    private CustomerCacheProvider customerCacheProvider;


    /**
     * 更新用户缓存
     *
     * @param userId 用户ID
     * @return
     */
    public boolean refresh(String userId) {
        try {
            if (StringUtils.isEmpty(userId)) {
                userInfoCacheProvider.cacheAllUser();
            } else {
                userInfoCacheProvider.getUserInfo(userId);
            }
        } catch (Exception e) {
            LOGGER.error("刷新用户基本信息缓存异常", e);
        }
        return true;
    }


    /**
     * 更新客户缓存
     *
     * @param customerId 客户ID
     * @return
     */
    public boolean refreshCustomerCache(String customerId) {
        try {
            if (StringUtils.isEmpty(customerId)) {
                customerCacheProvider.cacheAllCustomer();
            } else {
                customerCacheProvider.getCustomer(customerId);
            }
        } catch (Exception e) {
            LOGGER.error("刷新客户基本信息缓存异常", e);
        }
        return true;
    }


    /***
     * 定时刷新缓存
     * 每隔30分钟刷新所有缓存
     */
    @Scheduled(cron = "0 */30 * * * ?")
    public void refreshCacheScheduling() {
        LOGGER.info("定时刷新用户角色相关缓存,开始时间:{}", new Date());
        try {
            //角色缓存
            customerCacheProvider.cacheAllCustomer();
            //用户基本信息缓存
            userInfoCacheProvider.cacheAllUser();
        } catch (Exception e) {
            LOGGER.info("定时刷新用户角色相关缓存异常:{}", e.getMessage(), e);
        }
        LOGGER.info("定时刷新用户角色相关缓存,结束时间:{}", new Date());
    }
}
