package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.IndexForSmartVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.model.vo.WeatherVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
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
 * 首页控制器
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@RestController
@RequestMapping("app/smart/index")
@Api(value = "首页控制器", tags = "户式化APP首页接口")
public class IndexController extends BaseController {

    private IFamilySceneService familySceneService;

    private IFamilyDeviceService familyDeviceService;

    private IHomeAutoFamilyService familyService;

    @GetMapping
    @ApiOperation("首页接口")
    public Response<IndexForSmartVO> getFamilyCommonScenesAndDevices(@RequestParam String familyId) {
        List<SceneVO> commonSceneVOList = familySceneService.getCommonScenesByFamilyId(familyId);
        List<DeviceVO> commonDevicesVOList = familyDeviceService.getCommonDevicesByFamilyId(familyId);
        WeatherVO weatherVO = familyService.getWeatherByFamilyId(familyId);
        return returnSuccess(new IndexForSmartVO(weatherVO, commonSceneVOList, commonDevicesVOList));
    }

    @Autowired
    public void setFamilySceneService(IFamilySceneService familySceneService) {
        this.familySceneService = familySceneService;
    }

    @Autowired
    public void setFamilyDeviceService(IFamilyDeviceService familyDeviceService) {
        this.familyDeviceService = familyDeviceService;
    }

    @Autowired
    public void setFamilyService(IHomeAutoFamilyService familyService) {
        this.familyService = familyService;
    }
}
