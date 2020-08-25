package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.vo.FamilyRoomVO;
import com.landleaf.homeauto.center.device.model.vo.FamilySimpleDeviceVO;
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
    public Response<List<FamilyRoomVO>> getRoomList(@PathVariable String familyId) {
        List<FamilyRoomVO> familyRoomVOList = familyRoomService.getRoomListByFamilyId(familyId);
        return returnSuccess(familyRoomVOList);
    }

    @GetMapping("device_list/{roomId}")
    @ApiOperation("获取房间设备列表")
    public Response<List<FamilySimpleDeviceVO>> getRoomDevices(@PathVariable String roomId) {
        List<FamilySimpleDeviceVO> familySimpleDeviceVOList = familyRoomService.getDeviceListByRoomId(roomId);
        return returnSuccess(familySimpleDeviceVOList);
    }

    @Autowired
    public void setFamilyRoomService(IFamilyRoomService familyRoomService) {
        this.familyRoomService = familyRoomService;
    }
}
