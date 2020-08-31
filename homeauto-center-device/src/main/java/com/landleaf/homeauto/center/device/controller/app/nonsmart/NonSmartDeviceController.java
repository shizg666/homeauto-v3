package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yujiumin
 * @version 2020/8/31
 */
@RestController
@RequestMapping("app/non-smart/device")
@Api(tags = "自由方舟APP设备接口")
public class NonSmartDeviceController extends BaseController {

    @Autowired
    private IFamilyDeviceStatusService familyDeviceStatusService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @GetMapping("status/{deviceId}")
    @ApiOperation("获取设备状态")
    public Response<Map<String, Object>> getDeviceStatus(@PathVariable String deviceId) {
        String deviceSn = familyDeviceService.getById(deviceId).getSn();
        List<FamilyDeviceStatusDO> deviceStatusDOList = familyDeviceStatusService.getDeviceAttributionsBySn(deviceSn);
        Map<String, Object> attrMap = new LinkedHashMap<>();
        for (FamilyDeviceStatusDO familyDeviceStatusDO : deviceStatusDOList) {
            String statusCode = familyDeviceStatusDO.getStatusCode();
            String statusValue = familyDeviceStatusDO.getStatusValue();
            attrMap.put(statusCode, statusValue);
        }
        return returnSuccess(attrMap);
    }

}
