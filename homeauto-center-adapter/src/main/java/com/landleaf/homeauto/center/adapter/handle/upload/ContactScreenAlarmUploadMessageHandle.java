package com.landleaf.homeauto.center.adapter.handle.upload;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.adapter.remote.DeviceRemote;
import com.landleaf.homeauto.center.adapter.service.FamilyParseProvider;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceAlarmUploadDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

/**
 * 大屏通讯模块报警上报处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ContactScreenAlarmUploadMessageHandle extends AbstractContactScreenUploadHandle implements Observer {

    @Autowired(required = false)
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    @Async("adapterDealUploadMessageExecute")
    public void update(Observable o, Object arg) {

        AdapterMessageUploadDTO message = (AdapterMessageUploadDTO) arg;
        // 组装数据
        String messageName = message.getMessageName();
        if (StringUtils.equals(AdapterMessageNameEnum.FAMILY_SECURITY_ALARM_EVENT.getName(), messageName)) {
            generateBaseFamilyInfo(message);
            //发布消息出去
            try {
                mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_APP,
                        messageName, JSON.toJSONString(arg));

                log.info("[大屏上报安防报警状态消息]:消息编号:[{}],消息体:{}",
                        message.getMessageId(), message);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }


        }
    }

    @Autowired
    public void setFamilyParseProvider(FamilyParseProvider familyParseProvider){
        this.familyParseProvider=familyParseProvider;
    }
}
