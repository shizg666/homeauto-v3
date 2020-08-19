package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.FamilyDevicesExcludeCommonVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/17
 */
@RestController
@RequestMapping("app/smart/device")
@Api(value = "设备控制器", tags = "户式化APP设备接口")
public class DeviceController extends BaseController {

    private IFamilyDeviceService familyDeviceService;

    @GetMapping
    @ApiOperation("获取不常用的设备")
    public Response<List<FamilyDevicesExcludeCommonVO>> getUncommonDevices(@RequestParam String familyId) {
        List<FamilyDevicesExcludeCommonVO> familyDevicesExcludeCommonVOList = familyDeviceService.getUncommonDevicesByFamilyId(familyId);
        return returnSuccess(familyDevicesExcludeCommonVOList);
    }

    @Autowired
    public void setFamilyDeviceService(IFamilyDeviceService familyDeviceService) {
        this.familyDeviceService = familyDeviceService;
    }
}
