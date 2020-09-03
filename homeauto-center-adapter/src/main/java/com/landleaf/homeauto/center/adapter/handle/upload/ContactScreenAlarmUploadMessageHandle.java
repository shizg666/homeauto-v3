package com.landleaf.homeauto.center.adapter.handle.upload;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.adapter.remote.DeviceRemote;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceAlarmUploadDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
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
public class ContactScreenAlarmUploadMessageHandle implements Observer {

    @Autowired
    private DeviceRemote deviceRemote;
    @Autowired(required = false)
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    @Async("adapterDealUploadMessageExecute")
    public void update(Observable o, Object arg) {

        AdapterMessageUploadDTO message = (AdapterMessageUploadDTO) arg;
        // 走下面处理逻辑
        Integer terminalType = message.getTerminalType();
        // 组装数据
        String messageName = message.getMessageName();
        if (terminalType != null && TerminalTypeEnum.SCREEN.getCode().intValue() == terminalType.intValue()
                && StringUtils.equals(AdapterMessageNameEnum.FAMILY_SECURITY_ALARM_EVENT.getName(), messageName)) {
            Response<AdapterFamilyDTO> familyDTOResponse = null;
            try {
                familyDTOResponse = deviceRemote.getFamily(message.getTerminalMac(), message.getTerminalType());
            } catch (Exception e) {
                log.error("[大屏上报安防报警状态消息]获取家庭信息异常,[终端地址]:{}", message.getTerminalMac());
                return;
            }
            if (familyDTOResponse != null && familyDTOResponse.isSuccess()) {
                AdapterFamilyDTO familyDTO = familyDTOResponse.getResult();
                if (familyDTO == null) {
                    log.error("[大屏上报安防报警状态消息]家庭不存在,[终端地址]:{}", message.getTerminalMac());
                    return;
                }
                AdapterDeviceAlarmUploadDTO uploadDTO = (AdapterDeviceAlarmUploadDTO) message;
                uploadDTO.setFamilyId(familyDTO.getFamilyId());
                uploadDTO.setFamilyCode(familyDTO.getFamilyCode());
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
    }

}
