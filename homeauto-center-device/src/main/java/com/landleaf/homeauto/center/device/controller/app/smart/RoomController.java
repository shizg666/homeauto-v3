package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.RoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRoomDTO;
import com.landleaf.homeauto.center.device.model.bo.FamilyFloorBO;
import com.landleaf.homeauto.center.device.model.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.room.RoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.model.vo.room.FloorRoomVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Slf4j
@RestController
@RequestMapping("/app/smart/room")
@Api(tags = "户式化APP房间接口")
public class RoomController extends BaseController {

    @Autowired
    private IFamilyRoomService familyRoomService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    /**
     * 获取家庭下的所有楼层以及楼层下的房间信息
     *
     * @param familyId 家庭ID
     * @return 楼层下的房间信息
     */
    @GetMapping("/list/{familyId}")
    @ApiOperation("获取房间列表")
    public Response<List<FloorRoomVO>> getRoomList(@PathVariable String familyId) {

        // 1. 查询家庭的房间信息
        List<FamilyRoomDO> familyRoomDOList = familyRoomService.getRoom(familyId);
        List<String> roomIdList = familyRoomDOList.stream().map(FamilyRoomDO::getId).collect(Collectors.toList());
        List<RoomBO> roomBOList = familyRoomService.listRoomDetail(roomIdList).stream().sorted(Comparator.comparing(RoomBO::getFloorNum)).collect(Collectors.toList());

        // 2. 按照楼层给房间分类
        Map<FamilyFloorBO, List<RoomBO>> floorRoomMap = new LinkedHashMap<>();
        for (RoomBO roomBO : roomBOList) {
            FamilyFloorBO familyFloorBO = new FamilyFloorBO(roomBO.getFloorId(), roomBO.getFloorNum(), roomBO.getFloorName());
            if (floorRoomMap.containsKey(familyFloorBO)) {
                floorRoomMap.get(familyFloorBO).add(roomBO);
            } else {
                floorRoomMap.put(familyFloorBO, CollectionUtil.list(true, roomBO));
            }
        }

        // 3. 按楼层组装房间信息
        List<FloorRoomVO> floorRoomVOList = new LinkedList<>();
        for (FamilyFloorBO familyFloorBO : floorRoomMap.keySet()) {
            List<RoomBO> floorRoomBOList = floorRoomMap.get(familyFloorBO);

            List<RoomVO> roomVOList = new LinkedList<>();
            for (RoomBO roomBO : floorRoomBOList) {
                RoomVO roomVO = new RoomVO();
                roomVO.setRoomId(roomBO.getRoomId());
                roomVO.setRoomName(roomBO.getRoomName());
                roomVO.setRoomIcon(roomBO.getRoomIcon1());
                roomVOList.add(roomVO);
            }

            FloorRoomVO floorRoomVO = new FloorRoomVO();
            floorRoomVO.setFloorId(familyFloorBO.getFloorId());
            floorRoomVO.setFloorName(familyFloorBO.getFloorName());
            floorRoomVO.setFloorNum(familyFloorBO.getFloorNum());
            floorRoomVO.setRoomList(roomVOList);

            floorRoomVOList.add(floorRoomVO);
        }
        return returnSuccess(floorRoomVOList);
    }

    /**
     * 获取房间设备列表
     *
     * @param roomId 房间ID
     * @return 设备列表
     */
    @GetMapping("/device_list/{roomId}")
    @ApiOperation("获取房间设备列表")
    public Response<List<DeviceSimpleVO>> getRoomDevices(@PathVariable String roomId) {
        List<FamilyDeviceBO> familyRoomBOList = familyDeviceService.getDeviceInfoListByRoomId(roomId);
        List<DeviceSimpleVO> deviceSimpleVOList = new LinkedList<>();
        for (FamilyDeviceBO familyDeviceBO : familyRoomBOList) {
            DeviceBO deviceBO = familyDeviceService.getDeviceById(familyDeviceBO.getDeviceId());
            DeviceSimpleVO deviceSimpleVO = new DeviceSimpleVO();
            deviceSimpleVO.setDeviceId(familyDeviceBO.getDeviceId());
            deviceSimpleVO.setDeviceName(familyDeviceBO.getDeviceName());
            deviceSimpleVO.setDeviceIcon(familyDeviceBO.getDevicePicUrl());
            deviceSimpleVO.setProductCode(familyDeviceBO.getProductCode());
            deviceSimpleVO.setCategoryCode(familyDeviceBO.getCategoryCode());
            deviceSimpleVO.setPosition(String.format("%sF-%s", deviceBO.getFloorNum(), deviceBO.getRoomName()));
            deviceSimpleVOList.add(deviceSimpleVO);
        }
        return returnSuccess(deviceSimpleVOList);
    }

    /**
     * 获取房间图片
     *
     * @return 房间图片列表
     */
    @GetMapping("/pic/list")
    @ApiOperation("获取房间图片")
    public Response<List<String>> getRoomPic() {
        List<String> iconList = Arrays.stream(RoomTypeEnum.values()).map(RoomTypeEnum::getIcon).collect(Collectors.toList());
        return returnSuccess(iconList);
    }

    /**
     * 保存房间信息(针对编辑)
     *
     * @param familyRoomDTO 房间信息
     * @return 操作结果
     */
    @PostMapping("/save")
    @ApiOperation("保存房间信息")
    public Response<Boolean> save(@RequestBody FamilyRoomDTO familyRoomDTO) {
        log.info("进入{}接口,请求参数为:{}", "/app/smart/room/save", familyRoomDTO);
        FamilyRoomDO familyRoomDO = new FamilyRoomDO();
        familyRoomDO.setId(familyRoomDTO.getRoomId());
        familyRoomDO.setIcon(familyRoomDTO.getRoomPic());
        boolean result = familyRoomService.updateById(familyRoomDO);
        log.info("房间信息更新完成,更新后的房间信息为:{}", familyRoomService.getById(familyRoomDTO.getRoomId()));
        return returnSuccess(result);
    }

}
