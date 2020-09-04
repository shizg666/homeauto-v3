package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.remote.WebSocketRemote;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.device.DeviceStatusDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/9/4
 */
@Service
public class DeviceStatusPushService {

    @Autowired
    private WebSocketRemote webSocketRemote;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    /**
     * 推送设备的状态信息
     *
     * @param adapterDeviceStatusUploadDTO 设备状态信息
     */
    public void pushDeviceStatus(AdapterDeviceStatusUploadDTO adapterDeviceStatusUploadDTO) {
        DeviceStatusDTO deviceStatusDTO = new DeviceStatusDTO();
        deviceStatusDTO.setFamilyId(adapterDeviceStatusUploadDTO.getFamilyId());
        deviceStatusDTO.setDeviceSn(adapterDeviceStatusUploadDTO.getDeviceSn());
        deviceStatusDTO.setCategory(familyDeviceService.getDeviceCategory(adapterDeviceStatusUploadDTO.getDeviceSn()).getCode());
        deviceStatusDTO.setAttributes(adapterDeviceStatusUploadDTO.getItems().stream().collect(Collectors.toMap(ScreenDeviceAttributeDTO::getCode, ScreenDeviceAttributeDTO::getValue)));
        webSocketRemote.push(deviceStatusDTO);

    }

}
