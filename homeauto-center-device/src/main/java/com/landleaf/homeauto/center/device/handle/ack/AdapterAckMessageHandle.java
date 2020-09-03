package com.landleaf.homeauto.center.device.handle.ack;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.service.mybatis.IAdapterRequestMsgLogService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterSceneControlAckDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

/**
 * 监听Adapter到APP的响应ack
 * 将响应存储到redis缓存
 *
 * @author zhanghongbin
 */
@Component
@Slf4j
public class AdapterAckMessageHandle implements Observer {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IAdapterRequestMsgLogService adapterRequestMsgLogService;
    @Override
    @Async(value = "bridgeDealAckMessageExecute")
    public void update(Observable o, Object arg) {

        System.out.println("arg: " + arg.toString());


        AdapterMessageAckDTO message = (AdapterMessageAckDTO) arg;
        // 走下面处理逻辑
        Integer terminalType = message.getTerminalType();
        if (terminalType != null && TerminalTypeEnum.SCREEN.getCode().intValue() == terminalType.intValue()) {

            // 组装数据
            String messageName = message.getMessageName();

            String messageId = message.getMessageId();

            if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_DEVICE_WRITE.getName())) {
                // 控制设备ack   TODO ack时其它逻辑处理
                AdapterDeviceControlAckDTO ackDTO = (AdapterDeviceControlAckDTO) message;
                String key = RedisCacheConst.ADAPTER_APP_MSG_WAIT_ACK_PREFIX.concat(messageId);

                redisUtils.set(key, JSON.toJSONString(ackDTO), RedisCacheConst.COMMON_EXPIRE);

            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_DEVICE_STATUS_READ.getName())) {
                // 读取状态
                AdapterDeviceStatusReadAckDTO ackDTO = (AdapterDeviceStatusReadAckDTO) message;

                String key = RedisCacheConst.ADAPTER_APP_MSG_WAIT_ACK_PREFIX.concat(messageId);

                redisUtils.set(key, JSON.toJSONString(ackDTO), RedisCacheConst.COMMON_EXPIRE);


            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_FAMILY_CONFIG_UPDATE.getName())) {
                AdapterConfigUpdateAckDTO ackDTO = (AdapterConfigUpdateAckDTO) message;

                String key = RedisCacheConst.ADAPTER_APP_MSG_WAIT_ACK_PREFIX.concat(messageId);

                redisUtils.set(key, JSON.toJSONString(ackDTO), RedisCacheConst.COMMON_EXPIRE);


            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_FAMILY_SCENE_SET.getName())) {
                // 控制场景
                AdapterSceneControlAckDTO ackDTO = (AdapterSceneControlAckDTO) message;
                String key = RedisCacheConst.ADAPTER_APP_MSG_WAIT_ACK_PREFIX.concat(messageId);

                redisUtils.set(key, JSON.toJSONString(ackDTO), RedisCacheConst.COMMON_EXPIRE);
            } else {
                return;
            }

            try {
                adapterRequestMsgLogService.updatRecord(message);
            } catch (Exception e) {
                log.error("更新操作日志异常，装作没看见....");
            }

        }

    }

}