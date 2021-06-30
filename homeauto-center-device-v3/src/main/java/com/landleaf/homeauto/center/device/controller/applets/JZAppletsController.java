package com.landleaf.homeauto.center.device.controller.applets;

import com.landleaf.homeauto.center.device.model.dto.jhappletes.*;
import com.landleaf.homeauto.center.device.service.mybatis.IJHAppletsrService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName JHAppletsController
 * @Description: 常州嘉宏小程序接口
 * @Author shizg
 * @Date 2021/6/22
 * @Version V1.0
 **/
@RestController
@RequestMapping("/jh/applets")
@Api(description = "常州嘉宏小程序接口")
public class JZAppletsController extends BaseController {

    @Autowired
    private IJHAppletsrService ijhAppletsrService;

    @ApiOperation(value = "获取websocket地址", notes = "")
    @GetMapping("get/webSocket/address")
    public Response<String> getWebSocketAddress(@Valid JZFamilyQryDTO request){
        String path = ijhAppletsrService.getWebSocketAddress(request);
        return returnSuccess(path);
    }

    @ApiOperation(value = "家庭成员变更", notes = "")
    @PostMapping("update/family-users")
    public Response updateFamilyUser(@RequestBody @Valid JZFamilyUserDTO request){
        ijhAppletsrService.updateFamilyUser(request);
        return returnSuccess();
    }

    @ApiOperation(value = "管理员转让", notes = "")
    @PostMapping("transfer/admin")
    public Response transferFamilyAdmin(@RequestBody @Valid JZFamilyUserAdminDTO request){
        ijhAppletsrService.transferFamilyAdmin(request);
        return returnSuccess();
    }

    @ApiOperation(value = "获取家庭室外天气", notes = "")
    @GetMapping("outDoor/weather")
    public Response<OutDoorWeatherVO> getOutDoorWeather(@RequestBody @Valid JZFamilyQryDTO request){
        OutDoorWeatherVO weatherVO = ijhAppletsrService.getOutDoorWeather(request);
        return returnSuccess(weatherVO);
    }

    @ApiOperation(value = "获取家庭室内环境信息", notes = "")
    @GetMapping("inDoor/weather")
    public Response<InDoorWeatherVO> getInDoorWeather(@RequestBody @Valid JZFamilyQryDTO request){
        InDoorWeatherVO weatherVO = ijhAppletsrService.getInDoorWeather(request);
        return returnSuccess(weatherVO);
    }


    @ApiOperation(value = "获取房间信息", notes = "")
    @GetMapping("get/room-info")
    public Response<JZFamilyRoomInfoVO> getListRooms(@RequestBody @Valid JZFamilyQryDTO request){
        JZFamilyRoomInfoVO roomInfoVO = ijhAppletsrService.getListRooms(request);
        return returnSuccess(roomInfoVO);
    }


    @ApiOperation(value = "修改房间名称", notes = "")
    @PostMapping("update/room-name")
    public Response updateRoomName(@RequestBody @Valid JZRoomInfoVO request){
        ijhAppletsrService.updateRoomName(request);
        return returnSuccess();
    }

    @GetMapping("get/scene-list")
    @ApiOperation(value = "获取场景列表")
    public Response<List<JZFamilySceneVO>> getListScene(@RequestParam JZFamilyQryDTO request) {
        return returnSuccess(ijhAppletsrService.getListScene(request));
    }

    @DeleteMapping("remove/scene/{sceneId}")
    @ApiOperation(value = "删除场景")
    public Response removeSceneById(@PathVariable("sceneId") Long sceneId) {
        ijhAppletsrService.removeSceneById(sceneId);
        return returnSuccess();
    }

    @PostMapping("add/scene")
    @ApiOperation(value = "新增场景")
    public Response<Long> addScene(@RequestBody JZFamilySceneDTO request) {
        ijhAppletsrService.addScene(request);
        return returnSuccess();
    }

    @PostMapping("update/scene")
    @ApiOperation(value = "修改场景")
    public Response updateScene(@RequestBody JZUpdateSceneDTO request) {
        ijhAppletsrService.updateScene(request);
        return returnSuccess();
    }


    @GetMapping("detail/{sceneId}")
    @ApiOperation(value = "查看场景详情")
    public Response<JZSceneDetailVO> getDetailSceneById(@PathVariable("sceneId") Long sceneId) {
        JZSceneDetailVO detailVO = ijhAppletsrService.getDetailSceneById(sceneId);
        return returnSuccess(detailVO);
    }

    @GetMapping("get/scene-config/data")
    @ApiOperation(value = "获取家庭楼层-房间-设备-属性信息")
    public Response<JZSceneConfigDataVO> getRoomDeviceAttrInfo(@RequestBody @Valid JZFamilyQryDTO request) {
        JZSceneConfigDataVO configDataVO = ijhAppletsrService.getRoomDeviceAttrInfo(request);
        return returnSuccess(configDataVO);
    }

    @GetMapping("get/device-status/total")
    @ApiOperation(value = "设备运行状态统计")
    public Response<JZDeviceStatusTotalVO> getDeviceStatusTotal(@RequestBody @Valid JZFamilyQryDTO request) {
        JZDeviceStatusTotalVO detailVO = ijhAppletsrService.getDeviceStatusTotal(request);
        return returnSuccess(detailVO);
    }

    @GetMapping("get/device-status/category")
    @ApiOperation(value = "查看品类下设备状态")
    public Response<JZDeviceStatusCategoryVO> getDeviceStatusByCategoryCode(@RequestBody @Valid JZDeviceStatusQryDTO request) {
        JZDeviceStatusCategoryVO detailVO = ijhAppletsrService.getDeviceStatusByCategoryCode(request);
        return returnSuccess(detailVO);
    }

    @PostMapping("/device/execute")
    @ApiOperation(value = "设备: 设备控制")
    public Response command(@RequestBody JzDeviceCommandDTO request) {
        ijhAppletsrService.deviceCommand(request);
        return returnSuccess();
    }

    @GetMapping("get/alarm")
    @ApiOperation(value = "报警信息获取")
    public Response<List<JZAlarmMessageVO>> getListAlarm(JZFamilyQryDTO request) {
        List<JZAlarmMessageVO> data = ijhAppletsrService.getListAlarm(request);
        return returnSuccess(data);
    }


    @GetMapping("clear/alarm")
    @ApiOperation(value = "清除报警信息")
    public Response<List<JZAlarmMessageVO>> clearAlarms(JZFamilyQryDTO request) {
        List<JZAlarmMessageVO> data = ijhAppletsrService.getListAlarm(request);
        return returnSuccess(data);
    }



}
