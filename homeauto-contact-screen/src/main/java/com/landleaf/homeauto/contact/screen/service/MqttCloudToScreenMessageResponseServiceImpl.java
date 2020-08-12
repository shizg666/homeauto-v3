package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.common.context.SpringManager;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttBaseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttResponseBaseDTO;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenResponseProcedureEnum;
import com.landleaf.homeauto.contact.screen.common.util.ContactScreenRedisKeyUtil;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.controller.inner.procedure.response.AbstractResponseRocketMqProcedure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 内部服务向下消息响应处理器
 *
 * @author wenyilu
 */
@Service
@Slf4j
public class MqttCloudToScreenMessageResponseServiceImpl implements MqttCloudToScreenMessageResponseService {
    @Autowired
    private MqttCloudToScreenTimeoutService mqttCloudToScreenTimeoutService;


    @Override
    @Async("screenToCloudResponseMessageExecute")
    public void response(ScreenMqttResponseBaseDTO screenResponseBaseDTO, String outerMessageId, String operateName) {


        String messageKey = ContactScreenRedisKeyUtil.getMessageKey(screenResponseBaseDTO.getFamilyCode(), screenResponseBaseDTO.getScreenMac(), operateName, outerMessageId, 0);

        // 标记收到返回信息,方便超时队列移除: 返回原始信息
        ContactScreenDomain originMessage = mqttCloudToScreenTimeoutService.rmTimeoutTask(messageKey);

        if (originMessage == null) {
            // 找不到原始信息，也就没必要处理了
            return;
        }
        ScreenMqttBaseDTO data = originMessage.getData();
        String innerMessageId = data.getMessageId();
        screenResponseBaseDTO.setMessageId(innerMessageId);
        // 对状态码进行处理
        int code = screenResponseBaseDTO.getCode();

        // 先不管code，直接响应出去

        // 通过rocketMq返回响应信息
        ContactScreenResponseProcedureEnum procedureEnum = ContactScreenResponseProcedureEnum.getByCode(operateName);

        AbstractResponseRocketMqProcedure responseRocketMqProcedure = (AbstractResponseRocketMqProcedure) SpringManager.getBean(procedureEnum.getBeanName());

        responseRocketMqProcedure.procedureMessage(screenResponseBaseDTO);

    }
}
