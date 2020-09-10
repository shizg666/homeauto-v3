package com.landleaf.homeauto.center.device.service.bridge;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.asyn.IFutureService;
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
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @Description 提供给APP相关操作实现类
 * @Author zhanghongbin
 * @Date 2020/8/25 16:11
 */
@Service
@Slf4j
public class AppServiceImpl implements IAppService{


    @Autowired
    private IFutureService ifutureService;

    @Autowired
    private BridgeRequestMessageService bridgeRequestMessageService;


    @Override
    public AdapterSceneControlAckDTO familySceneControl(AdapterSceneControlDTO requestDTO) {
        //1. 设置唯一的messageId
        //2. 发送app_adapter的rocketMq
        //3. 等待接收app_adapter的ack
        String messageId = MessageIdUtils.genMessageId();
        requestDTO.setMessageId(messageId);
        requestDTO.setMessageName(AdapterMessageNameEnum.TAG_FAMILY_SCENE_SET.getName());

        bridgeRequestMessageService.dealMsg(requestDTO);

        log.debug("messageId:{}",messageId);
        AdapterSceneControlAckDTO ackDTO = null;
        try {
            //3秒内去获取返回值
            String result = ifutureService.getAppControlCache(messageId, TimeConst.THIRD_SECOND_MILLISECONDS).get();

            ackDTO = JSON.parseObject(result,AdapterSceneControlAckDTO.class);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ackDTO;
    }

    @Override
    public AdapterDeviceControlAckDTO deviceWriteControl(AdapterDeviceControlDTO requestDTO) {


        //1. 设置唯一的messageId
        //2. 发送app_adapter的rocketMq
        //3. 等待接收app_adapter的ack
        String messageId = MessageIdUtils.genMessageId();
        requestDTO.setMessageId(messageId);

        requestDTO.setMessageName(AdapterMessageNameEnum.TAG_DEVICE_WRITE.getName());

        bridgeRequestMessageService.dealMsg(requestDTO);

        AdapterDeviceControlAckDTO ackDTO = null;
        try {
            //3秒内去获取返回值
            String result = ifutureService.getAppControlCache(messageId, TimeConst.THIRD_SECOND_MILLISECONDS).get();

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
        requestDTO.setMessageName(AdapterMessageNameEnum.TAG_DEVICE_STATUS_READ.getName());
        bridgeRequestMessageService.dealMsg(requestDTO);

        AdapterDeviceStatusReadAckDTO ackDTO = null;
        try {
            //3秒内去获取返回值
            String result = ifutureService.getAppControlCache(messageId, TimeConst.THIRD_SECOND_MILLISECONDS).get();

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
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1. 设置唯一的messageId
        //2. 发送app_adapter的rocketMq
        //3. 等待接收app_adapter的ack
        String messageId = MessageIdUtils.genMessageId();
        requestDTO.setMessageId(messageId);
        requestDTO.setMessageName(AdapterMessageNameEnum.TAG_FAMILY_CONFIG_UPDATE.getName());
        bridgeRequestMessageService.dealMsg(requestDTO);

        AdapterConfigUpdateAckDTO ackDTO = null;
        try {
            //3秒内去获取返回值
            String result = ifutureService.getAppControlCache(messageId, TimeConst.THIRD_SECOND_MILLISECONDS).get();

            ackDTO = JSON.parseObject(result,AdapterConfigUpdateAckDTO.class);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return ackDTO;
    }


}
