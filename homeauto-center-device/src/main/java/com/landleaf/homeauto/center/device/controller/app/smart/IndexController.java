package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonSceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.model.vo.device.FamilyDeviceVO;
import com.landleaf.homeauto.model.vo.device.FamilyDevicesAndScenesForApp;
import com.landleaf.homeauto.model.vo.device.FamilySceneVO;
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
@Api(value = "首页控制器", tags = "户式化APP首页接口")
public class IndexController extends BaseController {

    private IFamilySceneService familySceneService;

    private IFamilyDeviceService familyDeviceService;

    @GetMapping
    @ApiOperation("获取常用场景和设备")
    public Response<FamilyDevicesAndScenesForApp> getFamilyCommonScenesAndDevices(@RequestParam String familyId) {
        List<FamilySceneVO> commonSceneVOList = familySceneService.getCommonScenesByFamilyId(familyId);
        List<FamilyDeviceVO> commonDevicesVOList = familyDeviceService.getCommonDevicesByFamilyId(familyId);
        return returnSuccess(new FamilyDevicesAndScenesForApp(commonSceneVOList, commonDevicesVOList));
    }

    @Autowired
    public void setFamilySceneService(IFamilySceneService familySceneService) {
        this.familySceneService = familySceneService;
    }

    @Autowired
    public void setFamilyDeviceService(IFamilyDeviceService familyDeviceService) {
        this.familyDeviceService = familyDeviceService;
    }
}
