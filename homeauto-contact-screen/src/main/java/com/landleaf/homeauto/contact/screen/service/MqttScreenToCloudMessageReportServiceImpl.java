package com.landleaf.homeauto.contact.screen.service;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.context.SpringManager;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.AckCodeTypeEnum;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenErrorCodeEnumConst;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenUploadToInnerProcedureEnum;
import com.landleaf.homeauto.contact.screen.common.util.MessageIdUtil;
import com.landleaf.homeauto.contact.screen.controller.inner.procedure.upload.AbstractUploadRocketMqProcedure;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.CommonResponsePayload;
import com.landleaf.homeauto.contact.screen.handle.mqtt.to.MqttCommonResponseHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 大屏通知消息上报处理器
 *
 * @author wenyilu
 */
@Service
@Slf4j
public class MqttScreenToCloudMessageReportServiceImpl implements MqttScreenToCloudMessageReportService {

    @Autowired
    private MqttCommonResponseHandle mqttCommonResponseHandle;

    @Override
    public void upload(ScreenMqttUploadBaseDTO screenUploadBaseDTO, String operateName, String outerMessageId) {
        screenUploadBaseDTO.setMessageId(outerMessageId);
        //  一个上报，一个响应
        uploadToCloud(screenUploadBaseDTO, operateName);
        responseToScreen(operateName, outerMessageId);
    }

    // 响应大屏
    @Override
    public void responseToScreen(String operateName, String outerMessageId) {
        ContactScreenHeader context = ContactScreenContext.getContext();
        String screenMac = context.getScreenMac();
        ContactScreenMqttResponse response = new ContactScreenMqttResponse();

        ContactScreenHeader header = ContactScreenHeader.builder().name(operateName).messageId(outerMessageId)
                .screenMac(screenMac).ackCode(AckCodeTypeEnum.NON_REQUIRED.type).build();
        CommonResponsePayload payload = CommonResponsePayload.builder().code(ContactScreenErrorCodeEnumConst.SUCCESS.getCode())
                .message(ContactScreenErrorCodeEnumConst.SUCCESS.getMsg()).build();

        response.setPayload(payload);
        response.setHeader(header);

        mqttCommonResponseHandle.handlerRequest(response);

    }


    /**
     * 上报云端
     *
     * @param screenUploadBaseDTO
     */
    @Override
    public void uploadToCloud(ScreenMqttUploadBaseDTO screenUploadBaseDTO, String operateName) {

        // 通过rocketMq上报信息
        ContactScreenUploadToInnerProcedureEnum procedureEnum = ContactScreenUploadToInnerProcedureEnum.getByCode(operateName);

        AbstractUploadRocketMqProcedure uploadRocketMqProcedure = (AbstractUploadRocketMqProcedure) SpringManager.getBean(procedureEnum.getBeanName());

        uploadRocketMqProcedure.procedureMessage(screenUploadBaseDTO);

        log.info("[上报mq消息]:消息类别:[{}],消息编号:[{}],消息体:{}",
                operateName, screenUploadBaseDTO.getMessageId(), JSON.toJSONString(screenUploadBaseDTO));
    }
}
