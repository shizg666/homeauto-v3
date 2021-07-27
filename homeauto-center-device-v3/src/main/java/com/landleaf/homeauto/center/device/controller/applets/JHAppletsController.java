package com.landleaf.homeauto.center.device.controller.applets;

import com.landleaf.homeauto.center.device.model.dto.jhappletes.*;
import com.landleaf.homeauto.center.device.service.mybatis.IJHAppletsrService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.util.BeanUtil;
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
public class JHAppletsController extends BaseController {

    @Autowired
    private IJHAppletsrService ijhAppletsrService;

    @ApiOperation(value = "获取websocket地址", notes = "")
    @GetMapping("get/webSocket/address")
    public Response<String> getWebSocketAddress(@Valid JZFamilyQryDTO request,@RequestParam("appKey") String appKey){
        String path = ijhAppletsrService.getWebSocketAddress(request,appKey);
        return returnSuccess(path);
    }

    @ApiOperation(value = "家庭成员变更", notes = "")
    @PostMapping("update/family-users")
    public Response updateFamilyUser(@RequestBody @Valid JZFamilyUserDTO request,@RequestParam("appKey") String appKey){
        ijhAppletsrService.updateFamilyUser(request,appKey);
        return returnSuccess();
    }

    @ApiOperation(value = "管理员转让", notes = "")
    @PostMapping("transfer/admin")
    public Response transferFamilyAdmin(@RequestBody @Valid JZFamilyUserAdminDTO request,@RequestParam("appKey") String appKey){
        ijhAppletsrService.transferFamilyAdmin(request,appKey);
        return returnSuccess();
    }

    @ApiOperation(value = "获取家庭室外天气", notes = "")
    @GetMapping("outDoor/weather")
    public Response<OutDoorWeatherVO> getOutDoorWeather(@Valid JZFamilyQryDTO request,@RequestParam("appKey") String appKey){
        OutDoorWeatherVO weatherVO = ijhAppletsrService.getOutDoorWeather(request,appKey);
        return returnSuccess(weatherVO);
    }

    @ApiOperation(value = "获取家庭室内环境信息", notes = "")
    @GetMapping("inDoor/weather")
    public Response<InDoorWeatherVO> getInDoorWeather(@Valid JZFamilyQryDTO request,@RequestParam("appKey") String appKey){
        InDoorWeatherVO weatherVO = ijhAppletsrService.getInDoorWeather(request,appKey);
        return returnSuccess(weatherVO);
    }


    @ApiOperation(value = "获取房间信息", notes = "")
    @GetMapping("get/room-info")
    public Response<JZFamilyRoomInfoVO> getListRooms(@Valid JZFamilyQryDTO request,@RequestParam("appKey") String appKey){
        JZFamilyRoomInfoVO roomInfoVO = ijhAppletsrService.getListRooms(request,appKey);
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
    public Response<List<JZFamilySceneVO>> getListScene(JZFamilyQryDTO request,@RequestParam("appKey") String appKey) {
        return returnSuccess(ijhAppletsrService.getListScene(request,appKey));
    }

    @PostMapping("remove/scene")
    @ApiOperation(value = "删除场景")
    public Response removeSceneById(@RequestBody JZDelFamilySceneDTO request) {
        ijhAppletsrService.removeSceneById(request);
        return returnSuccess();
    }

    @PostMapping("add/scene")
    @ApiOperation(value = "新增场景")
    public Response<Long> addScene(@RequestBody JZFamilySceneDTO request,@RequestParam("appKey") String appKey) {
        Long sceneId = ijhAppletsrService.addScene(request,appKey);
        return returnSuccess(sceneId);
    }

    @PostMapping("update/scene")
    @ApiOperation(value = "修改场景")
    public Response updateScene(@RequestBody JZFamilySceneDTO request,@RequestParam("appKey") String appKey) {
        ijhAppletsrService.updateScene(request,appKey);
        return returnSuccess();
    }

    @GetMapping("detail")
    @ApiOperation(value = "查看场景详情")
    public Response<JZSceneDetailVO> getDetailSceneById(@RequestParam("sceneId") Long sceneId) {
        JZSceneDetailVO detailVO = ijhAppletsrService.getDetailSceneById(sceneId);
        return returnSuccess(detailVO);
    }

    /**
     * 执行场景
     *
     * @param request
     * @return Null
     */
    @PostMapping("/scene/execute")
    @ApiOperation("场景: 手动触发执行场景")
    public Response<?> execute(@RequestBody JZSceneExecDTO request,@RequestParam("appKey") String appKey) {
        ijhAppletsrService.executeScene(request,appKey);
        return returnSuccess();
    }

    @GetMapping("get/scene-config/data")
    @ApiOperation(value = "获取家庭楼层-房间-设备-属性信息")
    public Response<JZSceneConfigDataVO> getRoomDeviceAttrInfo(@Valid JZFamilyQryDTO request,@RequestParam("appKey") String appKey) {
        JZSceneConfigDataVO configDataVO = ijhAppletsrService.getRoomDeviceAttrInfo(request,appKey);
        return returnSuccess(configDataVO);
    }

    @GetMapping("get/device-status/total")
    @ApiOperation(value = "设备运行状态统计")
    public Response<List<JZDeviceStatusTotalVO>> getDeviceStatusTotal(@Valid JZFamilyQryDTO request,@RequestParam("appKey") String appKey) {
        List<JZDeviceStatusTotalVO> detailVO = ijhAppletsrService.getDeviceStatusTotal(request,appKey);
        return returnSuccess(detailVO);
    }

    @GetMapping("get/device-status/category")
    @ApiOperation(value = "查看品类下设备状态")
    public Response<JZDeviceStatusCategoryVO> getDeviceStatusByCategoryCode(@Valid JZDeviceStatusQryDTO request,@RequestParam("appKey") String appKey) {
        JZDeviceStatusCategoryVO detailVO = ijhAppletsrService.getDeviceStatusByCategoryCode(request,appKey);
        return returnSuccess(detailVO);
    }

    @GetMapping("get/device-status/room-category")
    @ApiOperation(value = "查看某一房间某一品类下设备状态")
    public Response<JZRoomDeviceStatusCategoryVO> getDeviceStatusByRoomIdAndCategoryCode(@Valid JZDeviceStatusQryDTO request,@RequestParam("appKey") String appKey) {
        JZRoomDeviceStatusCategoryVO detailVO = ijhAppletsrService.getDeviceStatusByRIdAndCategory(request,appKey);
        return returnSuccess(detailVO);
    }

    @PostMapping("/device/execute")
    @ApiOperation(value = "设备: 设备控制")
    public Response command(@RequestBody JzDeviceCommandDTO request,@RequestParam("appKey") String appKey) {
        ijhAppletsrService.deviceCommand(request,appKey);
        return returnSuccess();
    }

    @GetMapping("get/alarm")
    @ApiOperation(value = "报警信息获取")
    public Response<List<JZAlarmMessageVO>> getListAlarm(JZFamilyQryDTO request,@RequestParam("appKey") String appKey) {
        List<JZAlarmMessageVO> data = ijhAppletsrService.getListAlarm(request,appKey);
        return returnSuccess(data);
    }


    @PostMapping("clear/alarm")
    @ApiOperation(value = "清除报警信息")
    public Response clearAlarms(@RequestBody JZFamilyQryDTO request,@RequestParam("appKey") String appKey) {
        ijhAppletsrService.clearAlarms(request,appKey);
        return returnSuccess();
    }



}
