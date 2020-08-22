package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateDetailVO;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplatePageVO;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplateTerminalVO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDeviceDTO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectHouseTemplateDTO;
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
    private IHouseTemplateTerminalService iTemplateTerminalService;
    @Autowired
    private IHouseTemplateFloorService iHouseTemplateFloorService;
    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;


    @ApiOperation(value = "新增户型", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    @LogAnnotation(name ="新增户型")
    public Response add(@RequestBody @Valid ProjectHouseTemplateDTO request){
        iProjectHouseTemplateService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改户型（修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    @LogAnnotation(name ="修改户型")
    public Response update(@RequestBody @Valid ProjectHouseTemplateDTO request){
        iProjectHouseTemplateService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "查看户型", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("detail/{id}")
    public Response<HouseTemplateDetailVO> delete(@PathVariable("id") String id){
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
    public Response<List<HouseTemplatePageVO>> getListByProjectId(@PathVariable("id") String id){
        List<HouseTemplatePageVO> result = iProjectHouseTemplateService.getListByProjectId(id);
        return returnSuccess(result);
    }


    @ApiOperation(value = "新增大屏/网关", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add/terminal")
    @LogAnnotation(name ="户型新增大屏/网关")
    public Response<HouseTemplateTerminalVO> addTerminal(@RequestBody @Valid HouseTemplateTerminalVO request){
        iTemplateTerminalService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改大屏/网关(修改id必传）", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/terminal")
    @LogAnnotation(name ="户型修改大屏/网关")
    public Response updateTerminal(@RequestBody @Valid HouseTemplateTerminalVO request){
        iTemplateTerminalService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除大屏/网关", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/terminal")
    @LogAnnotation(name ="户型删除大屏/网关")
    public Response deleteTerminal(@RequestBody ProjectConfigDeleteDTO request){
        iTemplateTerminalService.delete(request);
        return returnSuccess();
    }

    @ApiOperation(value = "获取网关类型下拉选项", notes = "获取网关类型下拉选项")
    @GetMapping("get/terminal/types")
    public Response<List<SelectedIntegerVO>> getTerminalSelects(){
        List<SelectedIntegerVO> result = iTemplateTerminalService.getTerminalTypes();
        return returnSuccess(result);
    }

    @ApiOperation(value = "获取户型网关下拉列表", notes = "")
    @GetMapping("get/terminal/list/{houseTemplateId}")
    public Response<List<SelectedVO>> getTerminalTypes(@PathVariable("houseTemplateId") String houseTemplateId){
        List<SelectedVO> result = iTemplateTerminalService.getTerminalSelects(houseTemplateId);
        return returnSuccess(result);
    }

    @ApiOperation(value = "新增楼层", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add/floor")
    public Response addFloor(@RequestBody TemplateFloorDTO request){
        iHouseTemplateFloorService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改楼层", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/floor")
    public Response updateFloor(@RequestBody @Valid TemplateFloorDTO request){
        iHouseTemplateFloorService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除楼层", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/floor")
    public Response deleteFloor(@RequestBody ProjectConfigDeleteDTO request){
        iHouseTemplateFloorService.delete(request);
        return returnSuccess();
    }

//    @ApiOperation(value = "根据项目id获取楼层列表", notes = "")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @GetMapping("list/floor/{id}")
//    public Response<List<HouseTemplatePageVO>> getListFloorByProjectId(@PathVariable("id") String id){
//        List<HouseTemplatePageVO> result = iHouseTemplateFloorService.getListByProjectId(id);
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
    public Response addDevice(@RequestBody TemplateDeviceDTO request){
        iHouseTemplateDeviceService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改设备", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update/device")
    public Response updateDevice(@RequestBody @Valid TemplateDeviceDTO request){
        iHouseTemplateDeviceService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "删除设备", notes = "")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/device")
    public Response deleteDevice(@RequestBody ProjectConfigDeleteDTO request){
        iHouseTemplateDeviceService.delete(request);
        return returnSuccess();
    }

}
