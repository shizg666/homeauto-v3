package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.IndexForSmartVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.WeatherVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/19
 */
@RestController
@RequestMapping("app/smart/family")
@Api(tags = "户式化APP家庭接口")
public class FamilyController extends BaseController {

    private IHomeAutoFamilyService familyService;

    private IFamilyUserService familyUserService;

    private IFamilySceneService familySceneService;

    private IFamilyDeviceService familyDeviceService;

    @GetMapping("list")
    @ApiOperation("获取家庭列表")
    public Response<FamilyVO> getFamily(String userId) {
        FamilyVO familyVO = familyService.getFamilyListByUserId(userId);
        return returnSuccess(familyVO);
    }

    @PostMapping("checkout/{familyId}")
    @ApiOperation("切换家庭")
    public Response<IndexForSmartVO> checkoutFamily(@PathVariable String familyId) {
        String userId = TokenContext.getToken().getUserId();
        if (familyUserService.isFamilyExisted(userId, familyId)) {
            familyUserService.checkoutFamily(userId, familyId);
            List<SceneVO> commonSceneVOList = familySceneService.getCommonScenesByFamilyId(familyId);
            List<DeviceVO> commonDevicesVOList = familyDeviceService.getCommonDevicesByFamilyId(familyId);
            WeatherVO weatherVO = familyService.getWeatherByFamilyId(familyId);
            return returnSuccess(new IndexForSmartVO(weatherVO, commonSceneVOList, commonDevicesVOList));
        }
        throw new BusinessException("需要切换的家庭不存在");
    }

    @Autowired
    public void setFamilyService(IHomeAutoFamilyService familyService) {
        this.familyService = familyService;
    }

    @Autowired
    public void setFamilyUserService(IFamilyUserService familyUserService) {
        this.familyUserService = familyUserService;
    }

    @Autowired
    public void setFamilySceneService(IFamilySceneService familySceneService) {
        this.familySceneService = familySceneService;
    }

    @Autowired
    public void setFamilyDeviceService(IFamilyDeviceService familyDeviceService) {
        this.familyDeviceService = familyDeviceService;
    }

    //*********************我的家庭相关************************/
    @GetMapping("my/list")
    @ApiOperation("获取我的家庭家庭列表")
    public Response<List<MyFamilyInfoVO>> getListFamily() {
        List<MyFamilyInfoVO> familyVOS = familyService.getListFamily();
        return returnSuccess();
    }

}
