package com.landleaf.homeauto.contact.screen.service;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttBaseDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.contact.screen.common.constance.TaskConst;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.common.util.ContactScreenRedisKeyUtil;
import com.landleaf.homeauto.contact.screen.common.util.MessageIdUtil;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

/**
 * 内部服务向下消息的处理器
 *
 * @author wenyilu
 */
@Service
@Slf4j
public class MqttCloudToScreenMessageServiceImpl extends Observable implements MqttCloudToScreenMessageService {

    @Autowired
    private Observer deviceWriteHandle;
    @Autowired
    private Observer deviceStatusReadHandle;
    @Autowired
    private Observer familyConfigUpdateHandle;
    @Autowired
    private Observer familySceneSetHandle;

    @Autowired
    private MessageIdUtil messageIdUtil;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MqttCloudToScreenMessageResponseService mqttCloudToScreenMessageResponseService;

    @PostConstruct
    protected void addObserver() {
        this.addObserver(deviceWriteHandle);
        this.addObserver(deviceStatusReadHandle);
        this.addObserver(familyConfigUpdateHandle);
        this.addObserver(familySceneSetHandle);
    }

    @Override
    public void addTask(ContactScreenDomain screenDomain) {

        // 如果重试次数>最大次数，则直接抛弃服务，并记录错误日志
        if (screenDomain.getMaxRetryTimes() <= screenDomain.getRetryTimes()) {
            /*******************************以下为错误处理逻辑***************************************************/
            String operateName = screenDomain.getOperateName();
            // 到这里了说明也不需要响应了,超时了，删除key
            mqttCloudToScreenMessageResponseService.responseErrorMsg(screenDomain.getData().getScreenMac(),
                    screenDomain.getData().getMessageId(), operateName,screenDomain.getOuterMessageId());
            String dto_key = RedisCacheConst.CONTACT_SCREEN_MSG_DTO_CACHE_PREFIX.concat(screenDomain.getMessageKey());
            redisUtils.del(dto_key);
            return;
        }
        /*********************************************需要执行的操作*****************************************************/
        // 异步通知执行
        setChanged();
        notifyObservers(screenDomain);
    }


    /**
     * 组装为通讯模块的消息体
     *
     * @param requestDto
     * @param operateName
     * @return
     */
    @Override
    public ContactScreenDomain buildMessage(ScreenMqttBaseDTO requestDto, String operateName) {

        String screenMac = requestDto.getScreenMac();

        int outerMessageId = messageIdUtil.getMsgId(screenMac);

        ContactScreenDomain messageDomain = ContactScreenDomain.builder().data(requestDto)
                .outerMessageId(String.valueOf(outerMessageId)).build();

        String messageKey = ContactScreenRedisKeyUtil.getMessageKey(screenMac, operateName, String.valueOf(outerMessageId), 0);
        messageDomain.setOperateName(operateName);
        messageDomain.setMessageKey(messageKey);
        messageDomain.setRetryFlag(true);
        messageDomain.setMaxRetryTimes(1);
        if (StringUtils.equals(operateName, ContactScreenNameEnum.FAMILY_CONFIG_UPDATE.getCode())) {
            messageDomain.setMaxRetryTimes(TaskConst.MAX_RETRY_TIMES);
        }
        return messageDomain;

    }
}
