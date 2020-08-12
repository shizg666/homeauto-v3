package com.landleaf.homeauto.contact.screen.service;

import com.landleaf.homeauto.common.context.SpringManager;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttUploadBaseDTO;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.AckCodeTypeEnum;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenErrorCodeEnumConst;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenResponseProcedureEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttResponse;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.CommonResponsePayload;
import com.landleaf.homeauto.contact.screen.handle.mqtt.to.MqttCommonResponseHandle;
import com.landleaf.homeauto.contact.screen.controller.inner.procedure.upload.AbstractUploadRocketMqProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 大屏通知消息上报处理器
 *
 * @author wenyilu
 */
@Service
public class MqttScreenToCloudMessageReportServiceImpl implements MqttScreenToCloudMessageReportService {

    @Autowired
    private MqttCommonResponseHandle mqttCommonResponseHandle;

    @Override
    public void upload(ScreenMqttUploadBaseDTO screenUploadBaseDTO, String operateName, String outerMessageId) {
        //  一个上报，一个响应
        uploadToCloud(screenUploadBaseDTO,operateName);
        responseToScreen(operateName, outerMessageId);
    }

    // 响应大屏
    private void responseToScreen(String operateName, String outerMessageId) {
        ContactScreenHeader context = ContactScreenContext.getContext();
        String screenMac = context.getScreenMac();
        String familyCode = context.getFamilyCode();
        ContactScreenMqttResponse response = new ContactScreenMqttResponse();

        ContactScreenHeader header = ContactScreenHeader.builder().name(operateName).messageId(outerMessageId).familyCode(familyCode)
                .screenMac(screenMac).ackCode(AckCodeTypeEnum.NON_REQUIRED.type).build();
        CommonResponsePayload payload = CommonResponsePayload.builder().code(ContactScreenErrorCodeEnumConst.SUCCESS.getCode())
                .message(ContactScreenErrorCodeEnumConst.SUCCESS.getMsg()).build();

        response.setPayload(payload);
        response.setHeader(header);

        mqttCommonResponseHandle.handlerRequest(response);

    }

    /**
     * 上报云端
     * @param screenUploadBaseDTO
     */
    private void uploadToCloud(ScreenMqttUploadBaseDTO screenUploadBaseDTO, String operateName) {

        // 通过rocketMq返回响应信息
        ContactScreenResponseProcedureEnum procedureEnum = ContactScreenResponseProcedureEnum.getByCode(operateName);

        AbstractUploadRocketMqProcedure uploadRocketMqProcedure = (AbstractUploadRocketMqProcedure) SpringManager.getBean(procedureEnum.getBeanName());

        uploadRocketMqProcedure.procedureMessage(screenUploadBaseDTO);

    }
}
