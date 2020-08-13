package com.landleaf.homeauto.contact.screen.handle.mqtt.to;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constance.QosEnumConst;
import com.landleaf.homeauto.common.constance.TopicEnumConst;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttDeviceControlDTO;
import com.landleaf.homeauto.common.mqtt.SyncSendUtil;
import com.landleaf.homeauto.contact.screen.common.constance.TaskConst;
import com.landleaf.homeauto.contact.screen.common.enums.AckCodeTypeEnum;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttRequest;
import com.landleaf.homeauto.contact.screen.dto.payload.ContactScreenDeviceAttribute;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request.DeviceWritePayload;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenTimeoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

/**
 * 云端控制设备处理类
 *
 * @author wenyilu
 */
@Service
@Slf4j
public class DeviceWriteHandle implements Observer {
    @Autowired
    private MqttCloudToScreenTimeoutService timeoutService;
    @Autowired(required = false)
    private SyncSendUtil syncSendUtil;

    public void handlerRequest(ContactScreenMqttRequest request) {

        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_CLOUD_TO_SCREEN.getTopic(), JSON.toJSONString(request), QosEnumConst.QOS_0);

    }

    @Override
    @Async("cloudToScreenMessageExecute")
    public void update(Observable o, Object arg) {
        ContactScreenDomain message = (ContactScreenDomain) arg;
        // 走下面处理逻辑
        String operateName = message.getOperateName();
        if (StringUtils.equals(operateName, ContactScreenNameEnum.DEVICE_WRITE.getCode())) {

            ContactScreenMqttRequest payload = buildRequestData(message);
            //通过mqtt下发到大屏
            handlerRequest(payload);

            // 发送完后修改  发送时间：当前时间戳，发送次数+1
            message.setSendTime(System.currentTimeMillis() + TaskConst.TIME_OUT_MILLISECONDS);
            message.setRetryTimes(message.getRetryTimes() + 1);
            // 下发完毕后放置到超时队列，该队列过一定间隔时间后，判断是否下发成功，若不成功，走原下发逻辑，继续执行下发
            timeoutService.addTimeoutTask(message);

        }

    }

    private ContactScreenMqttRequest buildRequestData(ContactScreenDomain message) {

        ScreenMqttDeviceControlDTO deviceControlDTO = (ScreenMqttDeviceControlDTO) message.getData();

        ContactScreenHeader header = ContactScreenHeader.builder().ackCode(AckCodeTypeEnum.REQUIRED.type)
                .familyCode(deviceControlDTO.getFamilyCode()).screenMac(deviceControlDTO.getScreenMac())
                .familyScheme(deviceControlDTO.getFamilyScheme())
                .messageId(message.getOuterMessageId()).name(message.getOperateName()).build();

        List<ScreenDeviceAttributeDTO> data = deviceControlDTO.getData();
        List<ContactScreenDeviceAttribute> payloadAttributes = data.stream().map(i -> {
            ContactScreenDeviceAttribute contactScreenDeviceAttribute = new ContactScreenDeviceAttribute();
            BeanUtils.copyProperties(i, contactScreenDeviceAttribute);
            return contactScreenDeviceAttribute;
        }).collect(Collectors.toList());

        DeviceWritePayload payload = DeviceWritePayload.builder()
                .deviceSn(deviceControlDTO.getDeviceSn()).productCode(deviceControlDTO.getProductCode())
                .data(payloadAttributes).build();

        return ContactScreenMqttRequest.builder().header(header).payload(payload).build();

    }
}
