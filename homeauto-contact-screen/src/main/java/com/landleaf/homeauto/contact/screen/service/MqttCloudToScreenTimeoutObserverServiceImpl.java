package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.DelayQueue;

/**
 * @author wenyilu
 */
@Service
@Slf4j
public class MqttCloudToScreenTimeoutObserverServiceImpl implements MqttCloudToScreenTimeoutObserverService {


    @Autowired
    MqttCloudToScreenMessageServiceImpl mqttCloudToScreenMessageService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    @Async("timeoutExecute")
    public void mvTask(DelayQueue<ContactScreenDomain> queue) {
        if (log.isDebugEnabled()) {
            log.debug("开始移除超时任务，线程名为:{}", Thread.currentThread().getName());
        }
        ContactScreenDomain domain = null;
        while (true) {
            try {
                domain = queue.take();
            } catch (InterruptedException e1) {
                log.error("从DelayQueue获取message信息失败.", e1);
                continue;
            }
            if (null == domain) {
                continue;
            }
            // 如果domain的key值存在ack的消息队列中，则已有返回值，不再关注
            if (log.isDebugEnabled()) {
                log.debug("处理超时任务。消息编号为:{}", domain.getMessageKey());
            }
            if (redisUtils.hasKey(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_ACK_PREFIX.concat(domain.getMessageKey()))) {
                if (log.isDebugEnabled()) {
                    log.debug("已经存在ack消息，不再继续重发任务。消息编号为:{}", domain.getMessageKey());
                }
                redisUtils.del(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_ACK_PREFIX.concat(domain.getMessageKey()));

                continue;
            }
            if (!redisUtils.hasKey(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(domain.getMessageKey()))) {
                if (log.isDebugEnabled()) {
                    log.debug("原始信息不存在，不再继续重发任务。消息编号为:{}", domain.getMessageKey());
                }
                continue;
            }
            if (!domain.isRetryFlag()) {
                // 异常返回

                continue;
            }
            // 重发
            mqttCloudToScreenMessageService.addTask(domain);
        }
    }

}
