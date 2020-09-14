package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.bo.FamilySimpleRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.RoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/list/{familyId}")
    @ApiOperation("获取房间列表")
    public Response<List<RoomVO>> getRoomList(@PathVariable String familyId) {
        List<FamilyRoomBO> familyRoomBOList = familyRoomService.getRoomListByFamilyId(familyId);

        // 按楼层将房间分类
        Map<String, List<FamilyRoomBO>> map = new LinkedHashMap<>();
        for (FamilyRoomBO familyRoomBO : familyRoomBOList) {
            String key = familyRoomBO.getFloorId() + "-" + familyRoomBO.getFloorName();
            if (map.containsKey(key)) {
                map.get(key).add(familyRoomBO);
            } else {
                map.put(key, CollectionUtil.list(true, familyRoomBO));
            }
        }

        // 组装
        List<RoomVO> roomVOList = new LinkedList<>();
        for (String key : map.keySet()) {
            List<FamilyRoomBO> familyRoomList = map.get(key);
            List<FamilySimpleRoomBO> familySimpleRoomBOList = new LinkedList<>();
            for (FamilyRoomBO familyRoomBO : familyRoomList) {
                FamilySimpleRoomBO familySimpleRoomBO = new FamilySimpleRoomBO();
                familySimpleRoomBO.setRoomId(familyRoomBO.getRoomId());
                familySimpleRoomBO.setRoomName(familyRoomBO.getRoomName());
                familySimpleRoomBO.setRoomPicUrl(familyRoomBO.getRoomPicUrl());
                familySimpleRoomBOList.add(familySimpleRoomBO);
            }
            String[] keySplit = key.split("-");
            RoomVO roomVO = new RoomVO();
            roomVO.setFloorId(keySplit[0]);
            roomVO.setFloorName(keySplit[1]);
            roomVO.setRoomList(familySimpleRoomBOList);
            roomVOList.add(roomVO);
        }
        return returnSuccess(roomVOList);
    }

    @GetMapping("/device_list/{roomId}")
    @ApiOperation("获取房间设备列表")
    public Response<List<DeviceSimpleVO>> getRoomDevices(@PathVariable String roomId) {
        List<FamilyDeviceBO> familyRoomBOList = familyDeviceService.getDeviceInfoListByRoomId(roomId);
        List<DeviceSimpleVO> deviceSimpleVOList = new LinkedList<>();
        for (FamilyDeviceBO familyDeviceBO : familyRoomBOList) {
            DeviceSimpleVO deviceSimpleVO = new DeviceSimpleVO();
            deviceSimpleVO.setDeviceId(familyDeviceBO.getDeviceId());
            deviceSimpleVO.setDeviceName(familyDeviceBO.getDeviceName());
            deviceSimpleVO.setDeviceIcon(familyDeviceBO.getDevicePicUrl());
            deviceSimpleVO.setCategoryCode(familyDeviceBO.getCategoryCode());
            deviceSimpleVOList.add(deviceSimpleVO);
        }
        return returnSuccess(deviceSimpleVOList);
    }

    /**
     * 保存房间信息
     * <p>
     * 针对编辑
     *
     * @param familyRoomDTO
     * @return
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
        return returnSuccess(result, "操作成功");
    }

}
