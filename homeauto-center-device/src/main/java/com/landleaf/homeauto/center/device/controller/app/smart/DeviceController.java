package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilyDevicesExcludeCommonVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yujiumin
 * @version 2020/8/17
 */
@RestController
@RequestMapping("app/smart/device")
@Api(value = "设备控制器", tags = "户式化APP设备接口")
public class DeviceController extends BaseController {

    private IFamilyDeviceService familyDeviceService;

    private IFamilyDeviceStatusService familyDeviceStatusService;

    @GetMapping("uncommon")
    @ApiOperation("获取不常用的设备")
    public Response<List<FamilyDevicesExcludeCommonVO>> getUncommonDevices(@RequestParam String familyId) {
        List<FamilyDevicesExcludeCommonVO> familyDevicesExcludeCommonVOList = familyDeviceService.getUncommonDevicesByFamilyId(familyId);
        return returnSuccess(familyDevicesExcludeCommonVOList);
    }

    @PostMapping("common/save")
    @ApiOperation("保存常用设备")
    public Response<?> addFamilyDeviceCommon(@RequestBody FamilyDeviceCommonDTO familyDeviceCommonDTO) {
        familyDeviceService.insertFamilyDeviceCommon(familyDeviceCommonDTO);
        return returnSuccess();
    }

    @GetMapping("status/{deviceId}")
    @ApiOperation("查看设备状态")
    public Response<Map<String, Object>> getDeviceStatus(@PathVariable String deviceId) {
        String deviceSn = familyDeviceService.getById(deviceId).getSn();
        List<FamilyDeviceStatusDO> familyDeviceStatusDOList = familyDeviceStatusService.getDeviceAttributionsBySn(deviceSn);
        Map<String, Object> attrMap = new LinkedHashMap<>();
        for (FamilyDeviceStatusDO familyDeviceStatusDO : familyDeviceStatusDOList) {
            String statusCode = familyDeviceStatusDO.getStatusCode();
            String statusValue = familyDeviceStatusDO.getStatusValue();
            attrMap.put(statusCode, statusValue);
        }
        return returnSuccess(attrMap);
    }

    @Autowired
    public void setFamilyDeviceService(IFamilyDeviceService familyDeviceService) {
        this.familyDeviceService = familyDeviceService;
    }

    @Autowired
    public void setFamilyDeviceStatusService(IFamilyDeviceStatusService familyDeviceStatusService) {
        this.familyDeviceStatusService = familyDeviceStatusService;
    }
}
