package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.*;
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
@RequestMapping("app/smart/family-manager")
@Api(tags = "户式化APP我的家庭管理接口")
public class FamilyManagerController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IFamilyUserService familyUserService;

    @GetMapping("my/list")
    @ApiOperation("获取我的家庭家庭列表")
    public Response<List<MyFamilyInfoVO>> getListFamily() {
        List<MyFamilyInfoVO> familyVOS = familyService.getListFamily();
        return returnSuccess(familyVOS);
    }

    @GetMapping("my/info/{familyId}")
    @ApiOperation("根据家庭id获取家庭信息")
    public Response<MyFamilyDetailInfoVO> getMyFamilyInfo(@PathVariable("familyId") String familyId) {
        MyFamilyDetailInfoVO familyVOS = familyService.getMyFamilyInfo(familyId);
        return returnSuccess();
    }

    @PostMapping("delete/member")
    @ApiOperation("移除家庭成员")
    public Response deleteFamilyMember(@RequestBody FamiluserDeleteVO request) {
        familyUserService.deleteFamilyMember(request);
        return returnSuccess();
    }

    @PostMapping("quit/family/{familyId}")
    @ApiOperation("退出家庭")
    public Response quitFamily(@PathVariable("familyId") String familyId) {
        familyUserService.quitFamily(familyId);
        return returnSuccess();
    }

    @PostMapping("add/{familyId}")
    @ApiOperation("绑定家庭")
    public Response addFamilyMember(@PathVariable("familyId") String familyId) {
        familyUserService.addFamilyMember(familyId);
        return returnSuccess();
    }

}
