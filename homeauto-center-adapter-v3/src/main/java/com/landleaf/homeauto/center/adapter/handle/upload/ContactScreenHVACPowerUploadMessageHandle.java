package com.landleaf.homeauto.center.adapter.handle.upload;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.adapter.remote.DeviceRemote;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterHVACPowerUploadDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

import static com.landleaf.homeauto.common.constant.RocketMqConst.DEVICE_STATUS_UPLOAD;

/**
 * 大屏通讯模块暖通功率上报处理类
 *
 */
@Component
@Slf4j
public class ContactScreenHVACPowerUploadMessageHandle implements Observer {

    @Autowired
    private DeviceRemote deviceRemote;
    @Autowired(required = false)
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    @Async("adapterDealUploadMessageExecute")
    public void update(Observable o, Object arg) {

        AdapterMessageUploadDTO message = (AdapterMessageUploadDTO) arg;
        // 组装数据
        String messageName = message.getMessageName();
        if (StringUtils.equals(AdapterMessageNameEnum.HVAC_POWER_UPLOAD.getName(), messageName)) {
            Response<AdapterFamilyDTO> familyDTOResponse = null;
            try {
                familyDTOResponse = deviceRemote.getFamily(message.getTerminalMac());
            } catch (Exception e) {
                log.error("[大屏上报设备状态消息]获取家庭信息异常,[终端地址]:{}", message.getTerminalMac());
                return;
            }
            if (familyDTOResponse != null && familyDTOResponse.isSuccess()) {
                AdapterFamilyDTO familyDTO = familyDTOResponse.getResult();
                if (familyDTO == null) {
                    log.error("[大屏上报设备状态消息]家庭不存在,[终端地址]:{}", message.getTerminalMac());
                    return;
                }
                AdapterHVACPowerUploadDTO uploadDTO = (AdapterHVACPowerUploadDTO) message;
                uploadDTO.setFamilyId(familyDTO.getFamilyId());
                uploadDTO.setFamilyCode(familyDTO.getFamilyCode());
                uploadDTO.setDeviceSn(((AdapterHVACPowerUploadDTO) message).getDeviceSn());
                uploadDTO.setProductCode(((AdapterHVACPowerUploadDTO) message).getProductCode());
                //发布消息出去
                try {
                    //此处使用统一的tag
                    mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_ADAPTER_TO_APP, DEVICE_STATUS_UPLOAD, JSON.toJSONString(arg));

                    log.info("[大屏上报设备功率消息]:消息编号:[{}],消息体:{}",
                            message.getMessageId(), message);

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

            }

        }
    }


}
