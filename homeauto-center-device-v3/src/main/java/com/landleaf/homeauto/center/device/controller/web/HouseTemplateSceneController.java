package com.landleaf.homeauto.center.device.controller.web;


import com.alibaba.nacos.common.utils.ConvertUtils;
import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.vo.scene.HouseFloorRoomListVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.WebSceneDetailVO;
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
    private IHouseTemplateSceneService iHouseTemplateSceneService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;


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

    @ApiOperation(value = "查询户型场景集合", notes = "根据户型id楼层房间设备集合")
    @GetMapping("get/list/{templateId}")
    public Response<List<HouseScenePageVO>> getListScene(@PathVariable("templateId") Long templateId){
        List<HouseScenePageVO> result = iHouseTemplateSceneService.getListScene(templateId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户型id获取楼层房间设备属性集合(添加场景设备)", notes = "")
    @GetMapping("get/device/list/{templateId}")
    public Response<List<SceneDeviceVO>> getListDevice(@PathVariable("templateId") Long templateId){
        List<SceneDeviceVO> result = iHouseTemplateDeviceService.getListDeviceScene(templateId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "查看场景", notes = "")
    @PostMapping("/detail/{sceneId}")
    public Response<WebSceneDetailVO> getSceneDetail(@PathVariable("sceneId") Long sceneId){
        WebSceneDetailVO result = iHouseTemplateSceneService.getSceneDetail(sceneId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户型id获取楼层集合和房间集合--新增场景设备使用", notes = "根据户型id获取楼层集合和房间集合")
    @GetMapping("get/floor-room/list/{templateId}")
    public Response<HouseFloorRoomListVO> getListFloorRooms(@PathVariable("templateId") Long templateId){                    HouseFloorRoomListVO result = iHouseTemplateDeviceService.getListFloorRooms(templateId);
        return returnSuccess(result);
    }

//    @ApiOperation(value = "查看场景下某一设备的配置（修改场景动作）", notes = "", consumes = "application/json")
//    @PostMapping(value = "/device-action")
//    public Response<HouseSceneDeviceConfigVO> getDeviceAction(@RequestBody SceneAcionQueryVO requestObject) {
//        HouseSceneDeviceConfigVO result = iTemplateSceneActionConfigService.getDeviceAction(requestObject);
//        return returnSuccess(result);
//    }

    //    @ApiOperation(value = "根据场景id查询场景详细信息", notes = "根据户型id楼层房间设备集合")
//    @GetMapping("get/detail/{templateId}")
//    public Response<List<HouseScenePageVO>> getDetailByTempalteId(@PathVariable("templateId") Long templateId){
//        List<HouseScenePageVO> result = iHouseTemplateSceneService.getDetailByTemplateId(templateId);
//        return returnSuccess(result);
//    }


//    @ApiOperation(value = "添加场景动作", notes = "添加", consumes = "application/json")
//    @PostMapping(value = "/add/scene/action")
//    public Response addSceneAction(@RequestBody @Valid HouseSceneInfoDTO requestObject) {
//        iTemplateSceneActionConfigService.addSeceneAction(requestObject);
//        return returnSuccess();
//    }

//    @ApiOperation(value = "修改场景动作", notes = "", consumes = "application/json")
//    @PostMapping(value = "/update/scene/action")
//    public Response updateSecneAction(@RequestBody @Valid HouseSceneInfoDTO requestObject) {
//        iTemplateSceneActionConfigService.updateSecneAction(requestObject);
//        return returnSuccess();
//    }

//    @ApiOperation(value = "删除场景动作", notes = "删除", consumes = "application/json")
//    @PostMapping("/delete/scene/action")
//    public Response deleteSecneAction(@RequestBody HouseSceneDeleteDTO requestVO) {
//        iTemplateSceneActionConfigService.deleteSecneAction(requestVO);
//        return returnSuccess();
//    }

//
//    @ApiOperation(value = "根据户型id获取楼层集合和房间集合", notes = "根据户型id获取楼层集合和房间集合")
//    @GetMapping("get/floor-room/list/{templateId}")
//    public Response<HouseFloorRoomListVO> getListFloorRooms(@PathVariable("templateId") String templateId){                    HouseFloorRoomListVO result = iHouseTemplateDeviceService.getListFloorRooms(templateId);
//        return returnSuccess(result);
//    }


}
