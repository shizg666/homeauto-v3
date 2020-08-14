package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonSceneService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.model.vo.device.FamilyCommonDeviceVO;
import com.landleaf.homeauto.model.vo.device.FamilyCommonDevicesAndScenesForApp;
import com.landleaf.homeauto.model.vo.device.FamilyCommonSceneVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页控制器
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@RestController
@RequestMapping("smart/index")
@Api(value = "首页控制器", tags = "户式化APP首页控制器")
public class IndexController extends BaseController {

    private IFamilyCommonSceneService familyCommonSceneService;

    private IFamilyCommonDeviceService familyCommonDeviceService;

    @GetMapping
    @ApiOperation("获取常用场景和设备")
    public Response<FamilyCommonDevicesAndScenesForApp> getFamilyCommonScenesAndDevices(@RequestParam String familyId) {
        List<FamilyCommonSceneVO> commonSceneVOList = familyCommonSceneService.getCommonScenesByFamilyId(familyId);
        List<FamilyCommonDeviceVO> commonDevicesVOList = familyCommonDeviceService.getCommonDevicesByFamilyId(familyId);
        return returnSuccess(new FamilyCommonDevicesAndScenesForApp(commonSceneVOList, commonDevicesVOList));
    }

    @Autowired
    public void setFamilyCommonSceneService(IFamilyCommonSceneService familyCommonSceneService) {
        this.familyCommonSceneService = familyCommonSceneService;
    }

    @Autowired
    public void setFamilyCommonDeviceService(IFamilyCommonDeviceService familyCommonDeviceService) {
        this.familyCommonDeviceService = familyCommonDeviceService;
    }
}
