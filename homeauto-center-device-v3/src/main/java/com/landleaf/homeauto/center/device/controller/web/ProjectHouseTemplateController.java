package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.center.device.model.vo.project.ProjectHouseTemplateDTO;
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
@RequestMapping("/web/house-template")
@Api(value = "/web/house-template/", tags = {"项目户型配置接口"})
public class ProjectHouseTemplateController extends BaseController {
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IDeviceAttrInfoService iDeviceAttrInfoService;
    @Autowired
    private IDeviceMessageService iDeviceMessageService;


    @ApiOperation(value = "新增户型", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增户型")
    public Response add(@RequestBody @Valid ProjectHouseTemplateDTO request){
        iProjectHouseTemplateService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "户型楼层类型下拉列表获取", notes = "项目状态下拉列表获取")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @GetMapping("status")
    public Response<List<SelectedIntegerVO>> getTemplateTypes() {
        List<SelectedIntegerVO> result = iProjectHouseTemplateService.getTemplateTypeSelects();
        return returnSuccess(result);
    }

//    @ApiOperation(value = "复制户型", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("copy")
//    @LogAnnotation(name ="复制户型")
//    public Response copy(@RequestBody  ProjectHouseTemplateDTO request){
//        iProjectHouseTemplateService.copy(request);
//        return returnSuccess();
//    }

    @ApiOperation(value = "修改户型（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改户型")
    public Response update(@RequestBody @Valid ProjectHouseTemplateDTO request){
        iProjectHouseTemplateService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "查看户型配置", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("detail/{id}")
    public Response<HouseTemplateDetailVO> delete(@PathVariable("id") Long id){
        HouseTemplateDetailVO result = iProjectHouseTemplateService.getDeatil(id);
        return returnSuccess(result);
    }

    @ApiOperation(value = "删除户型", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete")
    @LogAnnotation(name ="删除户型")
    public Response delete(@RequestBody ProjectConfigDeleteDTO request){
        iProjectHouseTemplateService.delete(request);
        return returnSuccess();
    }


    @ApiOperation(value = "根据项目id获取户型列表", notes = "根据项目id获取户型列表")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("list/{id}")
    public Response<List<HouseTemplatePageVO>> getListByProjectId(@PathVariable("id") Long id){
        List<HouseTemplatePageVO> result = iProjectHouseTemplateService.getListByProjectId(id);
        return returnSuccess(result);
    }



//    @ApiOperation(value = "根据项目id获取楼层列表", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @GetMapping("list/floor/{id}")
//    public Response<List<HouseTemplatePageVO>> getListFloorByProjectId(@PathVariable("id") String id){
//        List<HouseTemplatePageVO> result = iHouseTemplateFloorService.getListByProjectId(id);
//        return returnSuccess(result);
//    }

    @ApiOperation(value = "获取房间类型下拉选项", notes = "获取房间类型下拉选项")
    @GetMapping("get/room/types")
    public Response<List<SelectedIntegerVO>> getRoomTypeSelects(){
        List<SelectedIntegerVO> result = iHouseTemplateRoomService.getRoomTypeSelects();
        return returnSuccess(result);
    }

//    @ApiOperation(value = "房间下拉列表", notes = "房间下拉列表")
//    @GetMapping("get/rooms/{tempalteId}")
//    public Response<List<SelectedVO>> getRoomSelects(@PathVariable("tempalteId") String tempalteId){
//        List<SelectedVO> result = iHouseTemplateRoomService.getRoomSelects(tempalteId);
//        return returnSuccess(result);
//    }

    @ApiOperation(value = "新增房间", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add/room")
    public Response addRoom(@RequestBody TemplateRoomDTO request){
        iHouseTemplateRoomService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改房间", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/room")
    public Response updateRoom(@RequestBody @Valid TemplateRoomDTO request){
        iHouseTemplateRoomService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除房间", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/room")
    public Response deleteRoom(@RequestBody ProjectConfigDeleteDTO request){
        iHouseTemplateRoomService.delete(request);
        return returnSuccess();
    }

    @ApiOperation(value = "新增设备", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add/device")
    public Response addDevice(@RequestBody @Valid TemplateDeviceAddDTO request){
        TemplateDeviceDO deviceDO =iHouseTemplateDeviceService.add(request);
        DeviceOperateEvent deviceOperateEvent = DeviceOperateEvent.builder().deviceId(deviceDO.getId()).deviceCode(deviceDO.getCode()).templateId(deviceDO.getHouseTemplateId()).type(1).build();
        iDeviceMessageService.sendDeviceOperaMessage(deviceOperateEvent);
        return returnSuccess();
    }

    @ApiOperation(value = "修改设备", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/device")
    public Response updateDevice(@RequestBody @Valid TemplateDeviceAddDTO request){
        List<String> errorAttrCodes = iDeviceAttrInfoService.getAttrErrorCodeListByDeviceId(request.getId());
        iHouseTemplateDeviceService.update(request);
        //变更缓存
        TemplateDeviceDO deviceDO = iHouseTemplateDeviceService.getById(request.getId());
        DeviceOperateEvent deviceOperateEvent = DeviceOperateEvent.builder().deviceId(deviceDO.getId()).deviceCode(deviceDO.getCode()).templateId(deviceDO.getHouseTemplateId()).type(2).errorAttrCodes(errorAttrCodes).build();
        iDeviceMessageService.sendDeviceOperaMessage(deviceOperateEvent);
        return returnSuccess();
    }

    @ApiOperation(value = "删除设备", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/device")
    public Response deleteDevice(@RequestBody ProjectConfigDeleteDTO request){
//        List<String> errorAttrCodes = iDeviceAttrInfoService.getAttrErrorCodeListByDeviceId(request.getId());
        TemplateDeviceDO deviceDO = iHouseTemplateDeviceService.getById(request.getId());
        iHouseTemplateDeviceService.delete(request);
        //变更缓存
//        DeviceOperateEvent deviceOperateEvent = DeviceOperateEvent.builder().deviceId(deviceDO.getId()).deviceCode(deviceDO.getCode()).templateId(deviceDO.getHouseTemplateId()).type(3).errorAttrCodes(errorAttrCodes).build();
//        iDeviceMessageService.sendDeviceOperaMessage(deviceOperateEvent);
        return returnSuccess();
    }

    @ApiOperation(value = "设备详情", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("device/detail/{deviceId}")
    public Response<TemplateDeviceDetailVO> deleteDevice(@PathVariable String deviceId){
        TemplateDeviceDetailVO templateDeviceDetailVO = iHouseTemplateDeviceService.detailById(deviceId);
        return returnSuccess(templateDeviceDetailVO);
    }

//    @ApiOperation(value = "根据房间id获取设备列表", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @GetMapping("get/device/list/{roomId}")
//    public Response<List<TemplateDevicePageVO>> getListByRoomId(@PathVariable("roomId") String roomId){
//        List<TemplateDevicePageVO> result = iHouseTemplateDeviceService.getListByRoomId(roomId);
//        return returnSuccess(result);
//    }

    @ApiOperation(value = "户型设备列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/device/list/{templateId}")
    public Response<List<TemplateDevicePageVO>> getListDeviceByTemplateId(@PathVariable("templateId") String templateId){
        List<TemplateDevicePageVO> result = iHouseTemplateDeviceService.getListByTemplateId(templateId);
        return returnSuccess(result);
    }





}
