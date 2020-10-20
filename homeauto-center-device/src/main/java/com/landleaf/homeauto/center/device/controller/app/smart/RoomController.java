package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.config.ImagePathConfig;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRoomDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyFloorVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyRoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
    private IFamilyFloorService familyFloorService;

    @Autowired
    private IFamilyRoomService familyRoomService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private ImagePathConfig imagePathConfig;

    /**
     * 获取家庭下的所有楼层以及楼层下的房间信息
     *
     * @param familyId 家庭ID
     * @return 楼层下的房间信息
     */
    @GetMapping("/list/{familyId}")
    @ApiOperation("获取房间列表")
    public Response<List<FamilyFloorVO>> listFloorAndRoom(@PathVariable String familyId) {
        List<FamilyFloorDO> familyFloorDOList = familyFloorService.getFloorByFamilyId(familyId);
        List<FamilyFloorVO> familyFloorVOList = new LinkedList<>();
        for (FamilyFloorDO familyFloorDO : familyFloorDOList) {
            List<FamilyRoomBO> familyRoomBOList = familyRoomService.listFloorRoom(familyFloorDO.getId());

            List<FamilyRoomVO> familyRoomVOList = new LinkedList<>();
            for (FamilyRoomBO familyRoomBO : familyRoomBOList) {
                FamilyRoomVO familyRoomVO = new FamilyRoomVO();
                familyRoomVO.setRoomId(familyRoomBO.getRoomId());
                familyRoomVO.setRoomName(familyRoomBO.getRoomName());
                familyRoomVO.setRoomIcon(familyRoomBO.getRoomIcon1());
                familyRoomVOList.add(familyRoomVO);
            }

            FamilyFloorVO familyFloorVO = new FamilyFloorVO();
            familyFloorVO.setFloorId(familyFloorDO.getId());
            familyFloorVO.setFloorName(String.format("%sF", familyFloorDO.getFloor()));
            familyFloorVO.setRoomList(familyRoomVOList);
            familyFloorVOList.add(familyFloorVO);
        }
        return returnSuccess(familyFloorVOList);
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
        // TODO: 修改接口逻辑
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
        List<String> iconList = Arrays.stream(RoomTypeEnum.values()).map(room -> {
            return imagePathConfig.getContext().concat(room.getIcon());
        }).collect(Collectors.toList());
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
