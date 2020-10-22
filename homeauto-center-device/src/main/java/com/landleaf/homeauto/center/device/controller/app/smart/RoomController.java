package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.config.ImagePathConfig;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRoomDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyFloorVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyRoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
    private IHomeAutoFamilyService familyService;

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
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        if (Objects.equals(FamilyReviewStatusEnum.getInstByType(familyDO.getReviewStatus()), FamilyReviewStatusEnum.AUTHORIZATION)) {
            // 如果是家庭正在授权,则直接抛出异常
            throw new BusinessException(90001, "当前家庭授权状态更改中");
        }

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
     * 获取房间图片
     *
     * @return 房间图片列表
     */
    @GetMapping("/pic/list")
    @ApiOperation("获取房间图片")
    public Response<List<String>> getRoomPic() {
        List<String> iconList = Arrays.stream(RoomTypeEnum.values()).map(room -> imagePathConfig.getContext().concat(room.getIcon())).collect(Collectors.toList());
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
    public Response<?> save(@RequestBody FamilyRoomDTO familyRoomDTO) {
        log.info("进入{}接口,请求参数为:{}", "/app/smart/room/save", familyRoomDTO);
        FamilyRoomDO familyRoomDO = new FamilyRoomDO();
        familyRoomDO.setId(familyRoomDTO.getRoomId());
        familyRoomDO.setIcon(familyRoomDTO.getRoomPic());
        familyRoomService.updateById(familyRoomDO);
        log.info("房间信息更新完成,更新后的房间信息为:{}", familyRoomService.getById(familyRoomDTO.getRoomId()));
        return returnSuccess();
    }


    // ---- 即将废弃的接口

    /**
     * 获取房间设备列表
     * <p>
     * 建议用{@link DeviceController#getRoomDevices}
     *
     * @param roomId 房间ID
     * @return 设备列表
     */
    @Deprecated
    @GetMapping("/device_list/{roomId}")
    @ApiOperation("获取房间设备列表")
    public Response<List<FamilyDeviceVO>> getRoomDevices(@PathVariable String roomId) {
        List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO> familyDeviceBOList = familyDeviceService.listRoomDevice(roomId);
        List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
        for (FamilyDeviceBO familyDeviceBO : familyDeviceBOList) {
            FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
            familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
            familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
            familyDeviceVO.setDeviceIcon(familyDeviceBO.getProductIcon());
            familyDeviceVO.setProductCode(familyDeviceBO.getProductCode());
            familyDeviceVO.setCategoryCode(familyDeviceBO.getCategoryCode());
            familyDeviceVOList.add(familyDeviceVO);
        }
        return returnSuccess(familyDeviceVOList);
    }
}
