package com.landleaf.homeauto.center.device.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.service.ContactScreenService;
import com.landleaf.homeauto.center.device.service.IJSMSService;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.util.MessageIdUtils;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 通信桥测试
 * @Author zhanghongbin
 * @Date 2020/8/27 17:01
 */
@RestController
@RequestMapping("/bridge")
public class BridgeTestController extends BaseController {



    @Autowired
    private IJSMSService ijsmsService;

    @Autowired
    private ContactScreenService contactScreenService;



    @Autowired
    private IAppService iAppService;
    @Autowired(required = false)
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;


    /**
     * 测试mq发送
     */
    @GetMapping("/deviceControl")
    public void deviceControl(){

        AdapterDeviceControlDTO dto = new AdapterDeviceControlDTO();
        ScreenDeviceAttributeDTO attributeDTO = new ScreenDeviceAttributeDTO();
        attributeDTO.setCode("sh_2_0_glcSwitch");
        attributeDTO.setValue("1");

        dto.setData(Lists.newArrayList(attributeDTO));
        dto.setFamilyCode("101-0101101");
        dto.setFamilyId("69fad99efc164a8899e1dcf96fac680d");
        dto.setTerminalMac("888888");
//        dto.setTerminalType(TerminalTypeEnum.SCREEN.getCode().intValue());
        dto.setMessageName(AdapterMessageNameEnum.TAG_DEVICE_WRITE.getName());
        iAppService.deviceWriteControl(dto);
    }

    /**
     * 测试在线的大屏
     */
    @GetMapping("/screen/count")
    public void screenConut(){

        System.out.println(contactScreenService.getOnlineScreenNum());
    }


    /**
     * 测试mq消费
     */
    @GetMapping("/consumer")
    public void deviceControlConsumer(){

        AdapterDeviceControlAckDTO dto = new AdapterDeviceControlAckDTO();
        dto.setCode(0);
        dto.setFamilyCode("family003");
        dto.setMessage("成功");
        dto.setTerminalMac("xxxxx");
        dto.setMessageId(MessageIdUtils.genMessageId());
        dto.setMessageName(AdapterMessageNameEnum.TAG_DEVICE_WRITE.getName());

        mqProducerSendMsgProcessor.send("local_".concat(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_APP), dto.getMessageName(), JSON.toJSONString(dto));

    }


    /**
     * 测试短信发送
     */
    @GetMapping("/sms/{mobile}")
    public boolean smsSend(@PathVariable("mobile") String mobile){

        try {

//            ijsmsService.groupAddUser("南京熙华府","你的皮卡丘",mobile);

            return true;

        }catch (Exception e){
             e.printStackTrace();

             return false;

        }

    }
}
