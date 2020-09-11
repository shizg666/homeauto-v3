package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDetailQryDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilyScenePageVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 项目户型表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/family/scene/")
@Api(value = "/web/family/scene/", tags = {"家庭场景接口"})
public class FamilySceneController extends BaseController {


    @Autowired
    private IFamilySceneService iFamilySceneService;

    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;


    @ApiOperation(value = "新增家庭场景", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增家庭场景")
    public Response add(@RequestBody @Valid FamilySceneDTO request){
        iFamilySceneService.add(request);
        return returnSuccess();
    }


    @ApiOperation(value = "修改家庭场景（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改家庭场景")
    public Response update(@RequestBody @Valid FamilySceneDTO request){
        iFamilySceneService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改app/大屏场景修改标志", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/AppOrScreen-flag")
    public Response updateAppOrScreenFlag(@RequestBody SwitchSceneUpdateFlagDTO request){
        iFamilySceneService.updateAppOrScreenFlag(request);
        return returnSuccess();
    }




    @ApiOperation(value = "删除家庭场景", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete")
    @LogAnnotation(name ="删除家庭场景")
    public Response delete(@RequestBody ProjectConfigDeleteDTO request){
        iFamilySceneService.delete(request);
        return returnSuccess();
    }

    @ApiOperation(value = "查询场景集合", notes = "根据户型id楼层房间设备集合")
    @GetMapping("/list/{familyId}")
    public Response<List<FamilyScenePageVO>> getListScene(@PathVariable("familyId") String familyId){
        List<FamilyScenePageVO> result = iFamilySceneService.getListScene(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "查看场景", notes = "根据户型id楼层房间设备集合")
    @PostMapping("/detail")
    public Response<WebSceneDetailDTO> getSceneDetail(@RequestBody FamilySceneDetailQryDTO request){
        WebSceneDetailDTO result = iFamilySceneService.getSceneDetail(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据家庭id获取面板下拉列表", notes = "获取协议下拉列表")
    @GetMapping("get/panels/{familyId}")
    public Response<List<SelectedVO>> getListPanelSelects(@PathVariable("familyId") String familyId){
        List<SelectedVO> result = iFamilyDeviceService.getListPanelSelects(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户家庭d获取暖通设备信息集合", notes = "根据户型id获取暖通设备信息集合")
    @GetMapping("get/list-hvac/{familyId}")
    public Response<List<SceneHvacDeviceVO>> getListHvacInfo(@PathVariable("familyId") String familyId){
        List<SceneHvacDeviceVO> result = iFamilyDeviceService.getListHvacInfo(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据家庭id获取面板设置温度的属性范围（目前只考虑一户家庭中有一种类型的面板，假如有多个则随便取一个）", notes = "")
    @GetMapping("get/panel-attr-scope/{familyId}")
    public Response<AttributeScopeVO> getPanelSettingTemperature(@PathVariable("familyId") String familyId){
        AttributeScopeVO result = iFamilyDeviceService.getPanelSettingTemperature(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据家庭id获取楼层房间非暖通设备属性集合", notes = "根据户型id楼层房间设备集合")
    @GetMapping("get/device/list/{familyId}")
    public Response<List<SceneFloorVO>> getListdeviceInfo(@PathVariable("familyId") String familyId){
        List<SceneFloorVO> result = iFamilyDeviceService.getListdeviceInfo(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户型id获取楼层房间非暖通设备属性集合(不包含层级关系)", notes = "")
    @GetMapping("get/device/list/{templateId}")
    public Response<List<SceneDeviceVO>> getListDevice(@PathVariable("familyId") String familyId){
        List<SceneDeviceVO> result = iFamilyDeviceService.getListDevice(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户型id获取楼层集合和房间集合", notes = "根据户型id获取楼层集合和房间集合")
    @GetMapping("get/floor-room/list/{templateId}")
    public Response<HouseFloorRoomListDTO> getListFloorRooms(@PathVariable("templateId") String templateId){                    HouseFloorRoomListDTO result = iFamilyDeviceService.getListFloorRooms(templateId);
        return returnSuccess(result);
    }

}
