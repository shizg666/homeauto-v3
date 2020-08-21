package com.landleaf.homeauto.center.adapter.controller;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceStatusReadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * adapter项目调试控制器
 *
 * @author wenyilu
 */
@RestController
@RequestMapping("/adapter/test")
@Slf4j
public class AdapterTestController extends BaseController {


    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;


    @ApiOperation("控制设备")
    @PostMapping("/control/device")
    public Response testControlDevice(@RequestBody AdapterDeviceControlDTO deviceControlDTO) {
        deviceControlDTO.setMessageId(String.valueOf(getMsgId(deviceControlDTO.getTerminalMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_CENTER_ADAPTER, RocketMqConst.TAG_DEVICE_WRITE, JSON.toJSONString(deviceControlDTO));
        return returnSuccess();
    }

    @ApiOperation("控制场景")
    @PostMapping("/control/scene")
    public Response testControlScene(@RequestBody AdapterSceneControlDTO sceneControlDTO) {
        sceneControlDTO.setMessageId(String.valueOf(getMsgId(sceneControlDTO.getTerminalMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_CENTER_ADAPTER, RocketMqConst.TAG_FAMILY_SCENE_SET, JSON.toJSONString(sceneControlDTO));
        return returnSuccess();
    }

    @ApiOperation("读取状态")
    @PostMapping("/status/read")
    public Response testStatusRead(@RequestBody AdapterDeviceStatusReadDTO readDTO) {
        readDTO.setMessageId(String.valueOf(getMsgId(readDTO.getTerminalMac())));

        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_CENTER_ADAPTER, RocketMqConst.TAG_DEVICE_STATUS_READ, JSON.toJSONString(readDTO));
        return returnSuccess();
    }

    @ApiOperation("配置更新")
    @PostMapping("/config/update")
    public Response testConfigUpdate(@RequestBody AdapterConfigUpdateDTO configUpdateDTO) {
        configUpdateDTO.setMessageId(String.valueOf(getMsgId(configUpdateDTO.getTerminalMac())));
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_CENTER_ADAPTER, RocketMqConst.TAG_FAMILY_CONFIG_UPDATE, JSON.toJSONString(configUpdateDTO));
        return returnSuccess();
    }


    @Autowired
    private RedisUtils redisUtil;

    public int getMsgId(String screenMac) {
        long value = redisUtil.hincr(RedisCacheConst.CENTER_ADAPTER_TO_SCREEN_MESSAGE_ID_INCR, screenMac, 1L);
        return (int) (value % 65536);
    }


}
