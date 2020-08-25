package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.IndexForSmartVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyVO;
import com.landleaf.homeauto.center.device.model.vo.WeatherVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
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
@RequestMapping("app/smart/myfamily")
@Api(tags = "户式化APP我的家庭模块接口")
public class MyFamilyController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IFamilyUserService familyUserService;




    @GetMapping("list")
    @ApiOperation("获取我的家庭家庭列表")
    public Response<MyFamilyVO> getListFamily(String userId) {
        return returnSuccess();
    }


}
