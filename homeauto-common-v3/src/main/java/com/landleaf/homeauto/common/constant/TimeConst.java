package com.landleaf.homeauto.common.constant;

/**
 * @author Lokiy
 * @date 2019/8/16 13:46
 * @description:
 */
public interface TimeConst {

    int ONE_SECOND = 1;

    long THIRD_SECOND_MILLISECONDS = 3000L;
    long TEN_SECOND_MILLISECONDS = 10000L;
    /**
     * 1分钟
     */
    int ONE_MINUTE = 60;

    /**
     * 1天
     */
    int ONE_DAY = 86400;

    /**
     * 小时
     */
    int ONE_HOUR = 3600;
    
    /**
     * redis的锁的过期时间，单位秒
     */
    long REDIS_LOCK_EXPIRE = 60;
    
    /**
     * 1分钟的毫秒数
     */
    long MILLISECONDS_PER_MINUTE = 60 * 1000L;
    
    /**
     * 2099/12/31 23:59:59
     */
    long MAX_TIME = 4102415999000L;
}
