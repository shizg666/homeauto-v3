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

        Map<String, Object> attributes = new LinkedHashMap<>();
        for (ScreenDeviceAttributeDTO attributeDTO : adapterDeviceStatusUploadDTO.getItems()) {
            attributes.put(attributeDTO.getCode(), attributeDTO.getValue());
        }

        DeviceStatusDTO deviceStatusDTO = new DeviceStatusDTO();
        deviceStatusDTO.setFamilyId(adapterDeviceStatusUploadDTO.getFamilyId());
        deviceStatusDTO.setDeviceSn(adapterDeviceStatusUploadDTO.getDeviceSn());
        deviceStatusDTO.setCategory(familyDeviceService.getDeviceCategory(adapterDeviceStatusUploadDTO.getDeviceSn()).getCode());
        deviceStatusDTO.setAttributes(attributes);
        webSocketRemote.push(deviceStatusDTO);

    }

}
