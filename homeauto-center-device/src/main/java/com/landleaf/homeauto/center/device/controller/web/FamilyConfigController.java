package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.service.mybatis.*;
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
 * 楼栋表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/family/config/")
@Api(value = "/web/family/config/", tags = {"家庭配置接口"})
public class FamilyConfigController extends BaseController {

    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;
    @Autowired
    private IFamilyTerminalService iFamilyTerminalService;
    @Autowired
    private IFamilyFloorService iFamilyFloorService;
    @Autowired
    private IFamilyRoomService iFamilyRoomService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;


    @ApiOperation(value = "配置家庭", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("config-info/{familyId}")
    public Response<FamilyConfigDetailVO> getConfigInfo(@PathVariable("familyId") String familyId){
        FamilyConfigDetailVO result = iHomeAutoFamilyService.getConfigInfo(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "配置家庭权限判断", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("check/config-info/{familyId}")
    public Response checkFamilyConfig(@PathVariable("familyId") String familyId){
        iHomeAutoFamilyService.checkFamilyConfig(familyId);
        return returnSuccess();
    }

    @ApiOperation(value = "新增大屏/网关", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add/terminal")
    @LogAnnotation(name ="家庭新增大屏/网关")
    public Response<HouseTemplateTerminalVO> addTerminal(@RequestBody @Valid FamilyTerminalVO request){
        iFamilyTerminalService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改大屏/网关(修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/terminal")
    @LogAnnotation(name ="家庭修改大屏/网关")
    public Response updateTerminal(@RequestBody @Valid FamilyTerminalVO request){
        iFamilyTerminalService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "设置主大屏/网关", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("/terminal/switch-master")
    public Response<HouseTemplateTerminalVO> switchMaster(@RequestBody FamilyTerminalOperateVO request){
        iFamilyTerminalService.switchMaster(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除大屏/网关", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/terminal")
    @LogAnnotation(name ="家庭删除大屏/网关")
    public Response deleteTerminal(@RequestBody ProjectConfigDeleteDTO request){
        iFamilyTerminalService.delete(request);
        return returnSuccess();
    }


    @ApiOperation(value = "获取家庭网关下拉列表", notes = "")
    @GetMapping("get/terminal/list/{familyId}")
    public Response<List<SelectedVO>> getTerminalTypes(@PathVariable("familyId") String familyId){
        List<SelectedVO> result = iFamilyTerminalService.getTerminalSelects(familyId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "新增楼层", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add/floor")
    public Response addFloor(@RequestBody FamilyFloorDTO request){
        iFamilyFloorService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改楼层", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/floor")
    public Response updateFloor(@RequestBody @Valid FamilyFloorDTO request){
        iFamilyFloorService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除楼层", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/floor")
    public Response deleteFloor(@RequestBody ProjectConfigDeleteDTO request){
        iFamilyFloorService.delete(request);
        return returnSuccess();
    }

    @ApiOperation(value = "新增房间", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add/room")
    public Response addRoom(@RequestBody FamilyRoomDTO request){
        iFamilyRoomService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改房间", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/room")
    public Response updateRoom(@RequestBody @Valid FamilyRoomDTO request){
        iFamilyRoomService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除房间", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/room")
    public Response deleteRoom(@RequestBody ProjectConfigDeleteDTO request){
        iFamilyRoomService.delete(request);
        return returnSuccess();
    }

    @ApiOperation(value = "房间上移", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("room/move-up/{roomId}")
    public Response roomMoveUp(@PathVariable("roomId") String roomId){
        iFamilyRoomService.moveUp(roomId);
        return returnSuccess();
    }

    @ApiOperation(value = "房间下移", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("room/move-down/{roomId}")
    public Response roomMoveDown(@PathVariable("roomId") String roomId){
        iFamilyRoomService.moveDown(roomId);
        return returnSuccess();
    }

    @ApiOperation(value = "房间置顶", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("room/move-top/{roomId}")
    public Response roomMoveTop(@PathVariable("roomId") String roomId){
        iFamilyRoomService.moveTop(roomId);
        return returnSuccess();
    }

    @ApiOperation(value = "房间置底", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("room/move-end/{roomId}")
    public Response roomMoveEnd(@PathVariable("roomId") String roomId){
        iFamilyRoomService.moveEnd(roomId);
        return returnSuccess();
    }

    @ApiOperation(value = "设备上移", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("device/move-up/{deviceId}")
    public Response deviceMoveUp(@PathVariable("deviceId") String deviceId){
        iFamilyDeviceService.moveUp(deviceId);
        return returnSuccess();
    }

    @ApiOperation(value = "设备下移", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("device/move-down/{deviceId}")
    public Response deviceMoveDown(@PathVariable("deviceId") String deviceId){
        iFamilyDeviceService.moveDown(deviceId);
        return returnSuccess();
    }

    @ApiOperation(value = "设备置顶", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("device/move-top/{deviceId}")
    public Response deviceMoveTop(@PathVariable("deviceId") String deviceId){
        iFamilyDeviceService.moveTop(deviceId);
        return returnSuccess();
    }

    @ApiOperation(value = "设备置底", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("device/move-end/{deviceId}")
    public Response deviceMoveEnd(@PathVariable("deviceId") String deviceId){
        iFamilyDeviceService.moveEnd(deviceId);
        return returnSuccess();
    }


    @ApiOperation(value = "新增设备", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add/device")
    public Response addDevice(@RequestBody FamilyDeviceDTO request){
        iFamilyDeviceService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改设备", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/device")
    public Response updateDevice(@RequestBody @Valid FamilyDeviceUpDTO request){
        iFamilyDeviceService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除设备", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/device")
    public Response deleteDevice(@RequestBody ProjectConfigDeleteDTO request){
        iFamilyDeviceService.delete(request);
        return returnSuccess();
    }

    @ApiOperation(value = "根据房间id获取设备类列表", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/device/list/{roomId}")
    public Response<List<FamilyDevicePageVO>> getListByRoomId(@PathVariable("roomId") String roomId){
        List<FamilyDevicePageVO> result = iFamilyDeviceService.getListByRoomId(roomId);
        return returnSuccess(result);
    }






}
