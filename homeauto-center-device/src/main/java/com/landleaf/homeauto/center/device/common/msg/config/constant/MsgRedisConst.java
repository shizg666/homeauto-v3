package com.landleaf.homeauto.center.device.common.msg.config.constant;
/**
 * @ClassName McRedisConst
 * @Description: 消息Redis常量类
 * @Author ljy88
 * @Date 2020/4/20 
 * @Version V1.0
 **/
public interface MsgRedisConst {


    /**
     * 锁过期时间
     */
    Long MC_JPUSH_MIN_FREQUENCY_LOCK_EXPIRE_TIME = 1L;

    /**
     * 锁过期时间
     */
    Long MC_JPUSH_DELAY_QUEUE_LOCK_EXPIRE_TIME = 3L;

    /**
     * 频率过期时间1分钟
     */
    Long FREQUENCY_EXPIRE_TIME = 60L;

    /**
     * 消息 极光推送-每分钟 频率数 key
     */
    String MC_JPUSH_MIN_FREQUENCY = "mc_jpush_min_frequency";

    /**
     * 消息 极光推送-每分钟 频率数 锁 , 拼接当前推送 任务id
     */
    String MC_JPUSH_MIN_FREQUENCY_LOCK = "mc_jpush_min_frequency_lock_";


    /**
     * 消息 极光推送 延迟队列 key
     */
    String MC_JPUSH_DELAY_QUEUE = "mc_jpush_delay_queue";

    /**
     * 消息 极光推送 延迟队列 锁 , 拼接当前推送 任务id
     */
    String MC_JPUSH_DELAY_QUEUE_LOCK = "mc_jpush_delay_queue_lock_";

    /**
     * 延迟消息处理锁
     */
    String MC_JPUSH_DELAY_QUEUE_DEAL_LOCK = "mc_jpush_delay_queue_deal_lock_";
    /**
     * 延迟队列处理过期锁
     */
    Long MC_JPUSH_DELAY_QUEUE_DEAL_LOCK_EXPIRE_TIME = 30L;
}
