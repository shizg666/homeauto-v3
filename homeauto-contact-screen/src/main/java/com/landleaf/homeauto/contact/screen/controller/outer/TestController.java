package com.landleaf.homeauto.contact.screen.controller.outer;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.*;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.common.web.BaseController;
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
public class TestController extends BaseController {


    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;
    @Autowired
    private MessageIdUtil messageIdUtil;

    @ApiOperation("控制设备")
    @PostMapping("/control/device")
    public Response testControlDevice(@RequestBody ScreenMqttDeviceControlDTO deviceControlDTO) {
        deviceControlDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId(deviceControlDTO.getScreenMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_DEVICE_WRITE, JSON.toJSONString(deviceControlDTO));
       return returnSuccess();
    }

    @ApiOperation("控制场景")
    @PostMapping("/control/scene")
    public Response testControlScene(@RequestBody ScreenMqttSceneControlDTO sceneControlDTO) {
        sceneControlDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId( sceneControlDTO.getScreenMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_FAMILY_SCENE_SET, JSON.toJSONString(sceneControlDTO));
        return returnSuccess();
    }

    @ApiOperation("读取状态")
    @PostMapping("/status/read")
    public Response testStatusRead(@RequestBody ScreenMqttDeviceStatusReadDTO readDTO) {
        readDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId(readDTO.getScreenMac())));

        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_DEVICE_STATUS_READ, JSON.toJSONString(readDTO));
        return returnSuccess();
    }

    @ApiOperation("配置更新")
    @PostMapping("/config/update")
    public Response testConfigUpdate(@RequestBody ScreenMqttConfigUpdateDTO configUpdateDTO) {
        configUpdateDTO.setMessageId(String.valueOf(messageIdUtil.getMsgId(configUpdateDTO.getScreenMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_CONTACT_SCREEN, RocketMqConst.TAG_FAMILY_CONFIG_UPDATE, JSON.toJSONString(configUpdateDTO));
        return returnSuccess();
    }

}
