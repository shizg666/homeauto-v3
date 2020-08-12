package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.common.constance.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Observable;
import java.util.concurrent.DelayQueue;

/**
 * 超时的逻辑实现
 * @author wenyilu
 */
@Service
@Slf4j
public class MqttCloudToScreenTimeoutServiceImpl extends Observable implements MqttCloudToScreenTimeoutService {

    @PostConstruct
    protected void addObserver() {
        this.addObserver(this);
    }

    private DelayQueue<ContactScreenDomain> queue = new DelayQueue<ContactScreenDomain>();

    @Resource
    private MqttCloudToScreenTimeoutObserverService timeoutObserverServiceImpl;

    @Resource
    private RedisUtils redisUtils;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTimeoutTask(ContactScreenDomain messageDomain) {
        redisUtils.set(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageDomain.getMessageKey()), messageDomain, RedisCacheConst.CONTACT_SCREEN_MESSAGE_COMMON_EXPIRE);
        queue.offer(messageDomain);
        setChanged();
        notifyObservers();
    }

    /**
     * {@inheritDoc} 移除超时任务
     */
    @Override
    public ContactScreenDomain rmTimeoutTask(String  messageKey) {
        log.info("收到ack返回，redis的messagekey为{}", RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageKey));
        if (!redisUtils.hasKey(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageKey))) {
            return null;
        }
        redisUtils.set(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_ACK_PREFIX.concat(messageKey), messageKey, RedisCacheConst.COMMON_EXPIRE);

        ContactScreenDomain orginDomain = (ContactScreenDomain) redisUtils.get(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(messageKey));

        // 删除原始信息
        redisUtils.del(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_ACK_PREFIX.concat(messageKey));

        return orginDomain;
    }

    @Override
    public void update(Observable o, Object arg) {
        timeoutObserverServiceImpl.mvTask(queue);
    }
}
