package com.landleaf.homeauto.center.device.service;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.websocket.DeviceStatusMessage;
import com.landleaf.homeauto.common.domain.websocket.MessageEnum;
import com.landleaf.homeauto.common.domain.websocket.MessageModel;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/9/4
 */
@Service
public class WebSocketMessageService {

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    /**
     * 推送设备的状态信息
     *
     * @param adapterDeviceStatusUploadDTO 设备状态信息
     */
    public void pushDeviceStatus(AdapterDeviceStatusUploadDTO adapterDeviceStatusUploadDTO) {
        DeviceStatusMessage deviceStatusMessage = new DeviceStatusMessage();
        deviceStatusMessage.setDeviceId(familyDeviceService.getFamilyDevice(adapterDeviceStatusUploadDTO.getFamilyId(), adapterDeviceStatusUploadDTO.getDeviceSn()).getId());
        deviceStatusMessage.setCategory(familyDeviceService.getDeviceCategory(adapterDeviceStatusUploadDTO.getDeviceSn(), adapterDeviceStatusUploadDTO.getFamilyId()).getCode());
        deviceStatusMessage.setAttributes(adapterDeviceStatusUploadDTO.getItems().stream().collect(Collectors.toMap(ScreenDeviceAttributeDTO::getCode, ScreenDeviceAttributeDTO::getValue)));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_APP, "*", JSON.toJSONString(new MessageModel(MessageEnum.DEVICE_STATUS, adapterDeviceStatusUploadDTO.getFamilyId(), deviceStatusMessage)));
    }

    /**
     * 推送家庭授权信息
     *
     * @param familyId 家庭ID
     * @param status   状态信息
     */
    public void pushFamilyAuth(String familyId, Integer status) {
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_APP, "*", JSON.toJSONString(new MessageModel(MessageEnum.FAMILY_AUTH, familyId, status)));
    }

}
