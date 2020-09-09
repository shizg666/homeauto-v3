package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneFloorVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneHvacDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.ScenePageVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDTO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
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
@RequestMapping("/web/house-template/scene/")
@Api(value = "/web/house-template/", tags = {"户型场景接口"})
public class HouseTemplateSceneController extends BaseController {
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IHouseTemplateTerminalService iTemplateTerminalService;
    @Autowired
    private IHouseTemplateFloorService iHouseTemplateFloorService;
    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    @Autowired
    private IHouseTemplateSceneService iHouseTemplateSceneService;


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

    @ApiOperation(value = "查询场景集合", notes = "根据户型id楼层房间设备集合")
    @GetMapping("/list/{templageId}")
    public Response<List<ScenePageVO>> getListScene(@PathVariable("templageId") String templageId){
        List<ScenePageVO> result = iHouseTemplateSceneService.getListScene(templageId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "查看场景", notes = "根据户型id楼层房间设备集合")
    @GetMapping("/detail/{sceneId}")
    public Response<List<ScenePageVO>> getSceneDetail(@PathVariable("sceneId") String sceneId){
        List<ScenePageVO> result = iHouseTemplateSceneService.getSceneDetail(sceneId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户型id获取面板下拉列表", notes = "获取协议下拉列表")
    @GetMapping("get/panels/{templageId}")
    public Response<List<SelectedVO>> getListPanelSelects(@PathVariable("templageId") String templageId){
        List<SelectedVO> result = iHouseTemplateDeviceService.getListPanelSelects(templageId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户型id获取暖通设备信息集合", notes = "根据户型id获取暖通设备信息集合")
    @GetMapping("get/list-hvac/{templageId}")
    public Response<List<SceneHvacDeviceVO>> getListHvacInfo(@PathVariable("templageId") String templageId){
        List<SceneHvacDeviceVO> result = iHouseTemplateDeviceService.getListHvacInfo(templageId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户型id获取面板设置温度的属性范围（目前只考虑一户家庭中有一种类型的面板，假如有多个则随便取一个）", notes = "")
    @GetMapping("get/panel-attr-scope/{templateId}")
    public Response<AttributeScopeVO> getPanelSettingTemperature(@PathVariable("templateId") String templateId){
        AttributeScopeVO result = iHouseTemplateDeviceService.getPanelSettingTemperature(templateId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据户型id获取楼层房间设备属性集合", notes = "根据户型id楼层房间设备集合")
    @GetMapping("get/device/list/{templageId}")
    public Response<List<SceneFloorVO>> getListdeviceInfo(@PathVariable("templageId") String templageId){
        List<SceneFloorVO> result = iHouseTemplateDeviceService.getListdeviceInfo(templageId);
        return returnSuccess(result);
    }




}
