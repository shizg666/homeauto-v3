package com.landleaf.homeauto.contact.screen.handle.mqtt.to;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.enums.QosEnumConst;
import com.landleaf.homeauto.common.constant.enums.TopicEnumConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttDeviceStatusReadDTO;
import com.landleaf.homeauto.common.mqtt.SyncSendUtil;
import com.landleaf.homeauto.contact.screen.common.constance.TaskConst;
import com.landleaf.homeauto.contact.screen.common.enums.AckCodeTypeEnum;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenDomain;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttRequest;
import com.landleaf.homeauto.contact.screen.dto.payload.mqtt.request.DeviceStatusReadRequestPayload;
import com.landleaf.homeauto.contact.screen.service.MqttCloudToScreenTimeoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Observable;
import java.util.Observer;

/**
 * 云端读取设备状态处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class DeviceStatusReadHandle implements Observer {

    @Resource
    private MqttCloudToScreenTimeoutService timeoutService;

    @Autowired(required = false)
    private SyncSendUtil syncSendUtil;

    public void handlerRequest(ContactScreenMqttRequest request) {

        syncSendUtil.pubTopic(TopicEnumConst.CONTACT_SCREEN_CLOUD_TO_SCREEN.getTopic().concat(request.getHeader().getScreenMac()), JSON.toJSONString(request), QosEnumConst.QOS_0);

        log.info("[下发外部mqtt消息执行]:消息类别:[{}],外部消息编号:[{}],消息体:{}",
                request.getHeader().getName(), request.getHeader().getMessageId(), JSON.toJSONString(request));

    }

    @Override
    @Async("cloudToScreenMessageExecute")
    public void update(Observable o, Object arg) {
        ContactScreenDomain message = (ContactScreenDomain) arg;
        // 走下面处理逻辑
        String operateName = message.getOperateName();
        if (StringUtils.equals(operateName, ContactScreenNameEnum.DEVICE_STATUS_READ.getCode())) {

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

        ScreenMqttDeviceStatusReadDTO deviceStatusReadDTO = (ScreenMqttDeviceStatusReadDTO) message.getData();

        ContactScreenHeader header = ContactScreenHeader.builder().ackCode(AckCodeTypeEnum.REQUIRED.type)
                .screenMac(deviceStatusReadDTO.getScreenMac())
                .messageId(message.getOuterMessageId()).name(message.getOperateName()).build();

        DeviceStatusReadRequestPayload payload = DeviceStatusReadRequestPayload.builder()
                .deviceSn(deviceStatusReadDTO.getDeviceSn())
                .slaveAddress(deviceStatusReadDTO.getSlaveAddress())
                .productCode(deviceStatusReadDTO.getProductCode()).build();
        return ContactScreenMqttRequest.builder().header(header).payload(payload).build();

    }
}
