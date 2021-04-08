package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.vo.scene.HouseFloorRoomListDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.SwitchSceneUpdateFlagDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateSceneService;
import com.landleaf.homeauto.center.device.service.mybatis.ITemplateSceneActionConfigService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
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
@RequestMapping("/web/house-template/scene/")
@Api(value = "/web/house-template/scene/", tags = {"户型场景接口"})
public class HouseTemplateSceneController extends BaseController {

    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;

    @Autowired
    private IHouseTemplateSceneService iHouseTemplateSceneService;
    @Autowired
    private ITemplateSceneActionConfigService iTemplateSceneActionConfigService;


    @ApiOperation(value = "新增场景", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增户型场景")
    public Response add(@RequestBody @Valid HouseSceneDTO request){
        iHouseTemplateSceneService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改户型场景（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改户型场景")
    public Response update(@RequestBody @Valid HouseSceneDTO request){
        iHouseTemplateSceneService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除户型场景", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete")
    @LogAnnotation(name ="删除户型场景")
    public Response delete(@RequestBody ProjectConfigDeleteDTO request){
        iHouseTemplateSceneService.delete(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改场景是否可修改标志", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("switch-updateFlag/{sceneId}")
    public Response switchUpdateFlagStatus(@PathVariable("sceneId") Long sceneId){
        iHouseTemplateSceneService.switchUpdateFlagStatus(sceneId);
        return returnSuccess();
    }

    @ApiOperation(value = "查询场景集合", notes = "根据户型id楼层房间设备集合")
    @GetMapping("/list/{templateId}")
    public Response<List<HouseScenePageVO>> getListScene(@PathVariable("templateId") String templateId){
        List<HouseScenePageVO> result = iHouseTemplateSceneService.getListScene(templateId);
        return returnSuccess(result);
    }


    @ApiOperation(value = "添加场景动作", notes = "添加", consumes = "application/json")
    @PostMapping(value = "/add/scene/action")
    public Response addSceneAction(@RequestBody @Valid HouseSceneInfoDTO requestObject) {
        iTemplateSceneActionConfigService.addSeceneAction(requestObject);
        return returnSuccess();
    }

    @ApiOperation(value = "修改场景动作", notes = "", consumes = "application/json")
    @PostMapping(value = "/add/scene/action")
    public Response updateSecneAction(@RequestBody @Valid HouseSceneInfoDTO requestObject) {
        iTemplateSceneActionConfigService.updateSecneAction(requestObject);
        return returnSuccess();
    }

    @ApiOperation(value = "删除场景动作", notes = "删除", consumes = "application/json")
    @PostMapping("/delete/scene/action")
    public Response deleteSecneAction(@RequestBody HouseSceneDeleteDTO requestVO) {
        iTemplateSceneActionConfigService.deleteSecneAction(requestVO);
        return returnSuccess();
    }




    @ApiOperation(value = "查看场景", notes = "根据户型id楼层房间设备集合")
    @PostMapping("/detail")
    public Response<WebSceneDetailDTO> getSceneDetail(@RequestBody SceneDetailQryDTO request){
        WebSceneDetailDTO result = iHouseTemplateSceneService.getSceneDetail(request);
        return returnSuccess(result);
    }

//    @ApiOperation(value = "根据户型id获取面板下拉列表", notes = "获取协议下拉列表")
//    @GetMapping("get/panels/{templateId}")
//    public Response<List<SelectedVO>> getListPanelSelects(@PathVariable("templateId") String templateId){
//        List<SelectedVO> result = iHouseTemplateDeviceService.getListPanelSelects(templateId);
//        return returnSuccess(result);
//    }

//    @ApiOperation(value = "根据户型id获取暖通设备信息集合", notes = "根据户型id获取暖通设备信息集合")
//    @GetMapping("get/list-hvac/{templateId}")
//    public Response<List<SceneHvacDeviceVO>> getListHvacInfo(@PathVariable("templateId") String templateId){
//        List<SceneHvacDeviceVO> result = iHouseTemplateDeviceService.getListHvacInfo(templateId);
//        return returnSuccess(result);
//    }

//    @ApiOperation(value = "根据户型id获取面板设置温度的属性范围（目前只考虑一户家庭中有一种类型的面板，假如有多个则随便取一个）", notes = "")
//    @GetMapping("get/panel-attr-scope/{templateId}")
//    public Response<AttributeScopeVO> getPanelSettingTemperature(@PathVariable("templateId") String templateId){
//        AttributeScopeVO result = iHouseTemplateDeviceService.getPanelSettingTemperature(templateId);
//        return returnSuccess(result);
//    }

//    @ApiOperation(value = "根据户型id获取楼层房间非暖通设备属性集合(层级关系)", notes = "")
//    @GetMapping("get/device-cascade/list/{templateId}")
//    public Response<List<SceneFloorVO>> getListDeviceInfo(@PathVariable("templateId") String templateId){
//        List<SceneFloorVO> result = iHouseTemplateDeviceService.getListdeviceInfo(templateId);
//        return returnSuccess(result);
//    }

//    @ApiOperation(value = "根据户型id获取楼层房间非暖通设备属性集合(不包含层级关系)", notes = "")
//    @GetMapping("get/device/list/{templateId}")
//    public Response<List<SceneDeviceVO>> getListDevice(@PathVariable("templateId") String templateId){
//        List<SceneDeviceVO> result = iHouseTemplateDeviceService.getListDevice(templateId);
//        return returnSuccess(result);
//    }

    @ApiOperation(value = "根据户型id获取楼层集合和房间集合", notes = "根据户型id获取楼层集合和房间集合")
    @GetMapping("get/floor-room/list/{templateId}")
    public Response<HouseFloorRoomListDTO> getListFloorRooms(@PathVariable("templateId") String templateId){                    HouseFloorRoomListDTO result = iHouseTemplateDeviceService.getListFloorRooms(templateId);
        return returnSuccess(result);
    }


}
