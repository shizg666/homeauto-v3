package com.landleaf.homeauto.center.device;

import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.service.DeviceStatusPushService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DeviceStatusTest {

    private DeviceStatusPushService deviceStatusPushService;

    @Autowired
    private IFamilyDeviceStatusService familyDeviceStatusService;

    @Test
    public void testPushDeviceStatus() {
        AdapterDeviceStatusUploadDTO adapterDeviceStatusUploadDTO = new AdapterDeviceStatusUploadDTO();
        adapterDeviceStatusUploadDTO.setFamilyId("a7c6e89a01bc4107b67a99fdd1cd0805");
        adapterDeviceStatusUploadDTO.setFamilyCode("31011001AA101");
        adapterDeviceStatusUploadDTO.setTerminalType(1);
        adapterDeviceStatusUploadDTO.setTerminalMac("1234567");
        adapterDeviceStatusUploadDTO.setProductCode("5001");
        adapterDeviceStatusUploadDTO.setDeviceSn("1");

        ScreenDeviceAttributeDTO attributeDTO = new ScreenDeviceAttributeDTO();
        attributeDTO.setCode("switch");
        attributeDTO.setValue("off");

        adapterDeviceStatusUploadDTO.setItems(Collections.singletonList(attributeDTO));

        deviceStatusPushService.pushDeviceStatus(adapterDeviceStatusUploadDTO);
    }


    @Test
    public void testInsertBatchDeviceStatus() {
        List<DeviceStatusBO> deviceStatusBOList = new LinkedList<>();
    }
    @Autowired
    public void setDeviceStatusPushService(DeviceStatusPushService deviceStatusPushService) {
        this.deviceStatusPushService = deviceStatusPushService;
    }
}
