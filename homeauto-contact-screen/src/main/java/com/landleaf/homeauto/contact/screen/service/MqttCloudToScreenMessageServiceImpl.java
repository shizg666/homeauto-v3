package com.landleaf.homeauto.contact.screen.service;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttBaseDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.common.util.ContactScreenRedisKeyUtil;
import com.landleaf.homeauto.contact.screen.common.util.MessageIdUtil;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.handle.mqtt.to.DeviceStatusReadHandle;
import com.landleaf.homeauto.contact.screen.handle.mqtt.to.DeviceWriteHandle;
import com.landleaf.homeauto.contact.screen.handle.mqtt.to.FamilyConfigUpdateHandle;
import com.landleaf.homeauto.contact.screen.handle.mqtt.to.FamilySceneSetHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Observable;

/**
 * 内部服务向下消息的处理器
 *
 * @author wenyilu
 */
@Service
public class MqttCloudToScreenMessageServiceImpl extends Observable implements MqttCloudToScreenMessageService {

    @Autowired
    private DeviceWriteHandle deviceWriteHandle;
    @Autowired
    private DeviceStatusReadHandle deviceStatusReadHandle;
    @Autowired
    private FamilyConfigUpdateHandle familyConfigUpdateHandle;
    @Autowired
    private FamilySceneSetHandle familySceneSetHandle;

    @Autowired
    private MessageIdUtil messageIdUtil;

    @Autowired
    private RedisUtils redisUtils;

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
            // 如果是设备写操作，则需要返回操作结果

            if (StringUtils.equals(operateName, ContactScreenNameEnum.DEVICE_WRITE.getCode())) {
                // 写操作返回错误

            }

            if (StringUtils.equals(operateName, ContactScreenNameEnum.DEVICE_STATUS_READ.getCode())) {
                // 状态读取返回错误

            }
            if (StringUtils.equals(operateName, ContactScreenNameEnum.FAMILY_CONFIG_UPDATE.getCode())) {
                // 配置更新通知返回错误

            }
            if (StringUtils.equals(operateName, ContactScreenNameEnum.FAMILY_SCENE_SET.getCode())) {
                // 控制场景返回错误

            }
            // 到这里了说明也不需要响应了,超时了，删除key
            redisUtils.del(RedisCacheConst.CONTACT_SCREEN_MSG_DTO_ACK_PREFIX.concat(screenDomain.getMessageKey()));

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

        String familyCode = requestDto.getFamilyCode();
        String screenMac = requestDto.getScreenMac();

        int outerMessageId = messageIdUtil.getMsgId(familyCode, screenMac);

        ContactScreenDomain messageDomain = ContactScreenDomain.builder().data(requestDto).sendTime(System.currentTimeMillis())
                .outerMessageId(String.valueOf(outerMessageId)).build();

        String messageKey = ContactScreenRedisKeyUtil.getMessageKey(familyCode, screenMac, operateName, String.valueOf(outerMessageId),0);
        messageDomain.setOperateName(operateName);
        messageDomain.setMessageKey(messageKey);

        return messageDomain;

    }
}
