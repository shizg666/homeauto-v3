package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.*;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluserDeleteVO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
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

    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;

    @Autowired
    private IFamilyRoomService iFamilyRoomService;

    @Autowired
    private IFamilyAuthorizationService iFamilyAuthorizationService;

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
        return returnSuccess(familyVOS);
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

    @PostMapping("update/family")
    @ApiOperation("修改家庭名称")
    public Response updateFamilyName(@RequestBody FamilyUpdateVO request) {
        familyService.updateFamilyName(request);
        return returnSuccess();
    }

    @PostMapping("update/device")
    @ApiOperation("修改设备名称")
    public Response updateDeviceName(@RequestBody FamilyUpdateVO request) {
        iFamilyDeviceService.updateDeviceName(request);
        return returnSuccess();
    }

    @PostMapping("update/room")
    @ApiOperation("修改房间名称")
    public Response updateRoomName(@RequestBody FamilyUpdateVO request) {
        iFamilyRoomService.updateRoomName(request);
        return returnSuccess();
    }

    @ApiOperation(value = "添加授权", notes = "添加", consumes = "application/json")
    @GetMapping(value = "/authorization/{familyId}")
    public Response authorization(@PathVariable("familyId") String familyId) {
        iFamilyAuthorizationService.authorization(familyId);
        return returnSuccess();
    }

}
