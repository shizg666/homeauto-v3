package com.landleaf.homeauto.center.device.service.bridge;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.asyn.FutureService;
import com.landleaf.homeauto.center.device.util.MessageIdUtils;
import com.landleaf.homeauto.common.constant.TimeConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterSceneControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceStatusReadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

/**
 * @Description 提供给APP相关操作实现类
 * @Author zhanghongbin
 * @Date 2020/8/25 16:11
 */
public class AppServiceImpl implements IAppService{


    @Autowired
    private FutureService futureService;
    @Autowired
    private BridgeRequestMessageService bridgeRequestMessageService;


    @Override
    public AdapterSceneControlAckDTO familySceneControl(AdapterSceneControlDTO requestDTO) {
        //1. 设置唯一的messageId
        //2. 发送app_adapter的rocketMq
        //3. 等待接收app_adapter的ack
        String messageId = MessageIdUtils.genMessageId();
        requestDTO.setMessageId(messageId);
        bridgeRequestMessageService.dealMsg(requestDTO);

        AdapterSceneControlAckDTO ackDTO = null;
        try {
            //3秒内去获取返回值
            String result = futureService.getAppControlCache(messageId, TimeConst.THIRD_SECOND_MILLISECONDS).get();

            ackDTO = JSON.parseObject(result,AdapterSceneControlAckDTO.class);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ackDTO;
    }

    @Override
    public AdapterDeviceControlAckDTO deviceWriteControl(AdapterDeviceControlDTO deviceControlDTO) {


        //1. 设置唯一的messageId
        //2. 发送app_adapter的rocketMq
        //3. 等待接收app_adapter的ack
        String messageId = MessageIdUtils.genMessageId();
        deviceControlDTO.setMessageId(messageId);
        bridgeRequestMessageService.dealMsg(deviceControlDTO);

        AdapterDeviceControlAckDTO ackDTO = null;
        try {
            //3秒内去获取返回值
            String result = futureService.getAppControlCache(messageId, TimeConst.THIRD_SECOND_MILLISECONDS).get();

             ackDTO = JSON.parseObject(result,AdapterDeviceControlAckDTO.class);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ackDTO;
    }

    @Override
    public AdapterDeviceStatusReadAckDTO deviceStatusRead(AdapterDeviceStatusReadDTO requestDTO) {

        //1. 设置唯一的messageId
        //2. 发送app_adapter的rocketMq
        //3. 等待接收app_adapter的ack
        String messageId = MessageIdUtils.genMessageId();
        requestDTO.setMessageId(messageId);
        bridgeRequestMessageService.dealMsg(requestDTO);

        AdapterDeviceStatusReadAckDTO ackDTO = null;
        try {
            //3秒内去获取返回值
            String result = futureService.getAppControlCache(messageId, TimeConst.THIRD_SECOND_MILLISECONDS).get();

            ackDTO = JSON.parseObject(result,AdapterDeviceStatusReadAckDTO.class);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ackDTO;
    }

    @Override
    public AdapterConfigUpdateAckDTO configUpdate(AdapterConfigUpdateDTO requestDTO) {
        //1. 设置唯一的messageId
        //2. 发送app_adapter的rocketMq
        //3. 等待接收app_adapter的ack
        String messageId = MessageIdUtils.genMessageId();
        requestDTO.setMessageId(messageId);
        bridgeRequestMessageService.dealMsg(requestDTO);

        AdapterConfigUpdateAckDTO ackDTO = null;
        try {
            //3秒内去获取返回值
            String result = futureService.getAppControlCache(messageId, TimeConst.THIRD_SECOND_MILLISECONDS).get();

            ackDTO = JSON.parseObject(result,AdapterConfigUpdateAckDTO.class);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ackDTO;
    }


}
