package com.landleaf.homeauto.center.device.service.ack;

import com.landleaf.homeauto.center.device.service.bridge.BridgeUploadMessageService;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @className: AckReadStatusExtendService
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/9
 **/
@Component
public class AckReadStatusExtendService {
    @Autowired
    private BridgeUploadMessageService bridgeUploadMessageService;

    @Async(value = "screenAckReadMessagePool")
    public void triggerUploadStatus(AdapterDeviceStatusReadAckDTO deviceStatusReadAckDTO) {
        AdapterDeviceStatusUploadDTO uploadDTO = new AdapterDeviceStatusUploadDTO();
        BeanUtils.copyProperties(deviceStatusReadAckDTO, uploadDTO);
        bridgeUploadMessageService.dealMsg(uploadDTO);
    }
}
