package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.RoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@RestController
@RequestMapping("app/smart/room")
@Api(tags = "户式化APP房间接口")
public class RoomController extends BaseController {

    private IFamilyRoomService familyRoomService;

    @GetMapping("list/{familyId}")
    @ApiOperation("获取房间列表")
    public Response<List<RoomVO>> getRoomList(@PathVariable String familyId) {
        List<RoomVO> roomVOList = familyRoomService.getRoomListByFamilyId(familyId);
        return returnSuccess(roomVOList);
    }

    @GetMapping("device_list/{roomId}")
    @ApiOperation("获取房间设备列表")
    public Response<List<DeviceSimpleVO>> getRoomDevices(@PathVariable String roomId) {
        List<DeviceSimpleVO> deviceSimpleVOList = familyRoomService.getDeviceListByRoomId(roomId);
        return returnSuccess(deviceSimpleVOList);
    }

    @Autowired
    public void setFamilyRoomService(IFamilyRoomService familyRoomService) {
        this.familyRoomService = familyRoomService;
    }
}
