package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.vo.device.DeviceMangeFamilyPageVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceManageQryDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DeviceController
 * @Description: TODO
 * @Author shizg
 * @Date 2021/1/28
 * @Version V1.0
 **/
@RestController
@RequestMapping("/web/device-manage")
@Api(value = "/web/device-manag/", tags = {"设备管理"})
public class DeviceManagerController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @ApiOperation(value = "设备列表查询", consumes = "application/json")
    @GetMapping("list")
    public  Response<BasePageVO<DeviceMangeFamilyPageVO>> getListDeviceMangeFamilyPage(DeviceManageQryDTO deviceManageQryDTO) {
        BasePageVO<DeviceMangeFamilyPageVO> data = iHomeAutoFamilyService.getListDeviceMangeFamilyPage(deviceManageQryDTO);
        return returnSuccess(data);
    }
}
