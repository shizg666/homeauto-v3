package com.landleaf.homeauto.center.device.controller;

import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    private IAppService iAppService;

    @GetMapping("/deviceControl")
    public void deviceControl(){

        AdapterDeviceControlDTO dto = new AdapterDeviceControlDTO();
        dto.setDeviceSn("001");
        dto.setFamilyCode("family003");
        dto.setTerminalMac("xxxxx");
        dto.setTerminalType(TerminalTypeEnum.SCREEN.getCode().intValue());
        dto.setMessageName(AdapterMessageNameEnum.TAG_DEVICE_WRITE.getName());
        iAppService.deviceWriteControl(dto);
    }
}
