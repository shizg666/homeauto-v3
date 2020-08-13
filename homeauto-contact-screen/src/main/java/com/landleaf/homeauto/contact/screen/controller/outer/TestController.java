package com.landleaf.homeauto.contact.screen.controller.outer;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constance.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.*;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.contact.screen.common.util.MessageIdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenyilu
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Api(value = "/test", tags = {"测试类"})
public class TestController {


    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;
    @Autowired
    private MessageIdUtil messageIdUtil;

    @ApiOperation("控制设备")
    @PostMapping("/control/device")
    public void testControlDevice(@RequestBody ScreenMqttDeviceControlDTO deviceControlDTO) {
        deviceControlDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId(deviceControlDTO.getFamilyCode(), deviceControlDTO.getScreenMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_DEVICE_WRITE, JSON.toJSONString(deviceControlDTO));
    }

    @ApiOperation("控制场景")
    @PostMapping("/control/scene")
    public void testControlScene(@RequestBody ScreenMqttSceneControlDTO sceneControlDTO) {
        sceneControlDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId(sceneControlDTO.getFamilyCode(), sceneControlDTO.getScreenMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_FAMILY_SCENE_SET, JSON.toJSONString(sceneControlDTO));
    }

    @ApiOperation("读取状态")
    @PostMapping("/status/read")
    public void testStatusRead(@RequestBody ScreenMqttDeviceStatusReadDTO readDTO) {
        readDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId(readDTO.getFamilyCode(), readDTO.getScreenMac())));

        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_DEVICE_STATUS_READ, JSON.toJSONString(readDTO));
    }

    @ApiOperation("apk升级")
    @PostMapping("/apk-update")
    public void testApkUpdate(@RequestBody ScreenMqttApkUpdateDTO apkUpdateDTO) {
        apkUpdateDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId(apkUpdateDTO.getFamilyCode(), apkUpdateDTO.getScreenMac())));

        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_SCREEN_APK_UPDATE, JSON.toJSONString(apkUpdateDTO));
    }

    @ApiOperation("配置更新")
    @PostMapping("/config/update")
    public void testConfigUpdate(@RequestBody ScreenMqttConfigUpdateDTO configUpdateDTO) {
        configUpdateDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId(configUpdateDTO.getFamilyCode(), configUpdateDTO.getScreenMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_FAMILY_CONFIG_UPDATE, JSON.toJSONString(configUpdateDTO));

    }

}
