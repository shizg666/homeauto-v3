package com.landleaf.homeauto.contact.screen.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.context.SpringManager;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttBaseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.response.ScreenMqttResponseBaseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.upload.ScreenMqttDeviceStatusUploadDTO;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenErrorCodeEnumConst;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenResponseToInnerProcedureEnum;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenUploadToInnerProcedureEnum;
import com.landleaf.homeauto.contact.screen.common.util.ContactScreenRedisKeyUtil;
import com.landleaf.homeauto.contact.screen.controller.inner.procedure.response.AbstractResponseRocketMqProcedure;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenMessageResponseService;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenTimeoutService;
import com.landleaf.homeauto.contact.screen.service.MqttScreenToCloudMessageReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内部服务向下消息响应处理器
 *
 * @author wenyilu
 */
@Service
@Slf4j
public class MqttCloudToScreenMessageResponseServiceImpl implements MqttCloudToScreenMessageResponseService {
    @Autowired
    @Lazy
    private MqttCloudToScreenTimeoutService mqttCloudToScreenTimeoutService;
    @Autowired
    private MqttScreenToCloudMessageReportService mqttScreenToCloudMessageReportService;


    @Override
    @Async("screenToCloudResponseMessageExecute")
    public void response(ScreenMqttResponseBaseDTO screenResponseBaseDTO, String outerMessageId, String operateName) {


        String messageKey = ContactScreenRedisKeyUtil.getMessageKey(screenResponseBaseDTO.getScreenMac(), operateName, outerMessageId, 0);

        // 标记收到返回信息,方便超时队列移除: 返回原始信息
        ContactScreenDomain originMessage = mqttCloudToScreenTimeoutService.rmTimeoutTask(messageKey, operateName);

        if (originMessage == null) {
            // 找不到原始信息，也就没必要处理了
            return;
        }
        ScreenMqttBaseDTO data = originMessage.getData();
        String innerMessageId = data.getMessageId();
        screenResponseBaseDTO.setMessageId(innerMessageId);
        // 对状态码进行处理
        int code = screenResponseBaseDTO.getCode();

        // 通过rocketMq返回响应信息
        ContactScreenResponseToInnerProcedureEnum procedureEnum = ContactScreenResponseToInnerProcedureEnum.getByCode(operateName);

        AbstractResponseRocketMqProcedure responseRocketMqProcedure = (AbstractResponseRocketMqProcedure) SpringManager.getBean(procedureEnum.getBeanName());

        responseRocketMqProcedure.procedureMessage(screenResponseBaseDTO);

        log.info("[返回内部mq消息执行结果]-[正常]:消息类别:[{}],内部消息编号:[{}],外部消息编号:[{}],消息体:{}",
                operateName, innerMessageId, outerMessageId
                , JSON.toJSONString(screenResponseBaseDTO));


        if (StringUtils.equals(operateName, ContactScreenResponseToInnerProcedureEnum.DEVICE_WRITE.getCode()) && code == ContactScreenErrorCodeEnumConst.SUCCESS.getCode()) {
            // 正常响应,再模拟发一条状态上报消息
            ScreenMqttDeviceStatusUploadDTO screenUploadBaseDTO = new ScreenMqttDeviceStatusUploadDTO();
            screenUploadBaseDTO.setMessageId(outerMessageId);
            screenUploadBaseDTO.setScreenMac(screenResponseBaseDTO.getScreenMac());
            ScreenMqttDeviceControlDTO controlDTO = (ScreenMqttDeviceControlDTO) data;

            screenUploadBaseDTO.setProductCode(controlDTO.getProductCode());
            screenUploadBaseDTO.setDeviceSn(controlDTO.getDeviceSn());
            screenUploadBaseDTO.setItems(controlDTO.getData());
            mqttScreenToCloudMessageReportService.uploadToCloud(screenUploadBaseDTO, ContactScreenUploadToInnerProcedureEnum.DEVICE_STATUS_UPDATE.getCode());

            extendLogic(controlDTO, outerMessageId);

        }


    }

    /**
     * 收到成功响应后的额外逻辑
     *
     * @param controlDTO
     * @param outerMessageId
     */
    private void extendLogic(ScreenMqttDeviceControlDTO controlDTO, String outerMessageId) {

        // 如果是调光灯,开？100%：0% 再下发一条百分比上报命令
        String productCode = controlDTO.getProductCode();
        List<ScreenDeviceAttributeDTO> controlData = controlDTO.getData();
        if (NumberUtils.isNumber(productCode)&&Integer.parseInt(productCode)>=11100 &&Integer.parseInt(productCode)<=11199&& controlData.stream().anyMatch(i -> {
            return StringUtils.equals(i.getCode(), "switch");
        })) {
            ScreenMqttDeviceStatusUploadDTO screenUploadBaseDTO = new ScreenMqttDeviceStatusUploadDTO();
            screenUploadBaseDTO.setMessageId(outerMessageId);
            screenUploadBaseDTO.setScreenMac(controlDTO.getScreenMac());
            screenUploadBaseDTO.setProductCode(controlDTO.getProductCode());
            screenUploadBaseDTO.setDeviceSn(controlDTO.getDeviceSn());
            List<ScreenDeviceAttributeDTO> data = Lists.newArrayList();
            ScreenDeviceAttributeDTO dto = new ScreenDeviceAttributeDTO();
            dto.setCode("dimming");
            dto.setValue(controlData.stream().anyMatch(i -> {
                return StringUtils.equals(i.getValue(), "on");
            }) ? "100" : "0");
            data.add(dto);
            screenUploadBaseDTO.setItems(data);
            mqttScreenToCloudMessageReportService.uploadToCloud(screenUploadBaseDTO, ContactScreenUploadToInnerProcedureEnum.DEVICE_STATUS_UPDATE.getCode());
        }
    }

    @Override
    @Async("screenToCloudResponseMessageExecute")
    public void responseErrorMsg(String screenMac, String innerMessageId, String operateName, String outerMessageId) {

        responseErrorMsg(screenMac, innerMessageId, operateName, outerMessageId, ErrorCodeEnumConst.NETWORK_ERROR.getMsg(), ErrorCodeEnumConst.NETWORK_ERROR.getCode());
    }

    @Override
    @Async("screenToCloudResponseMessageExecute")
    public void responseErrorMsg(String screenMac, String innerMessageId, String operateName, String outerMessageId, String errorMsg, Integer errorCode) {

        ScreenMqttResponseBaseDTO readResponseDTO = new ScreenMqttResponseBaseDTO();
        readResponseDTO.setScreenMac(screenMac);
        readResponseDTO.setMessageId(innerMessageId);
        readResponseDTO.setMessage(errorMsg);
        readResponseDTO.setCode(errorCode);
        // 如果是设备写操作，则需要返回操作结果
        // 通过rocketMq返回响应信息
        ContactScreenResponseToInnerProcedureEnum procedureEnum = ContactScreenResponseToInnerProcedureEnum.getByCode(operateName);

        AbstractResponseRocketMqProcedure responseRocketMqProcedure = (AbstractResponseRocketMqProcedure) SpringManager.getBean(procedureEnum.getBeanName());

        responseRocketMqProcedure.procedureMessage(readResponseDTO);

        log.info("[返回内部mq消息执行结果]-[异常]:消息类别:[{}],内部消息编号:[{}],外部消息编号:[{}],消息体:{}",
                operateName, innerMessageId, outerMessageId
                , JSON.toJSONString(readResponseDTO));
    }
}
