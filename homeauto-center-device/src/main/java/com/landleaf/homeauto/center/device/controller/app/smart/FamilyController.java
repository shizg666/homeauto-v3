package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.IndexVO;
import com.landleaf.homeauto.center.device.model.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.app.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.app.WeatherVO;
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

    @GetMapping
    @ApiOperation("获取家庭列表")
    public Response<FamilyVO> getFamily(String userId) {
        FamilyVO familyVO = familyService.getFamilyListByUserId(userId);
        return returnSuccess(familyVO);
    }

    @PostMapping("checkout/{familyId}")
    @ApiOperation("切换家庭")
    public Response<IndexVO> checkoutFamily(@PathVariable String familyId) {
        String userId = TokenContext.getToken().getUserId();
        if (familyUserService.isFamilyExisted(userId, familyId)) {
            familyUserService.checkoutFamily(userId, familyId);
            List<FamilySceneVO> commonSceneVOList = familySceneService.getCommonScenesByFamilyId(familyId);
            List<FamilyDeviceVO> commonDevicesVOList = familyDeviceService.getCommonDevicesByFamilyId(familyId);
            WeatherVO weatherVO = familyService.getWeatherByFamilyId(familyId);
            return returnSuccess(new IndexVO(weatherVO, commonSceneVOList, commonDevicesVOList));
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
}
