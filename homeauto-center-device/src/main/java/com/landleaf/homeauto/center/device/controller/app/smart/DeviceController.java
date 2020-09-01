package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilyUncommonDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Yujiumin
 * @version 2020/8/17
 */
@Slf4j
@RestController
@RequestMapping("/app/smart/device")
@Api(value = "设备控制器", tags = "户式化APP设备接口")
public class DeviceController extends BaseController {

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IFamilyDeviceStatusService familyDeviceStatusService;

    @Autowired
    private IFamilyCommonDeviceService familyCommonDeviceService;

    @GetMapping("/uncommon")
    @ApiOperation("获取不常用的设备")
    public Response<List<FamilyUncommonDeviceVO>> getUncommonDevices(@RequestParam String familyId) {
        // 获取家庭所有的设备
        List<FamilyDeviceWithPositionBO> allDeviceList = familyDeviceService.getAllDevices(familyId);
        Map<String, List<FamilyDeviceWithPositionBO>> map = new LinkedHashMap<>();
        // 先将所有的设备按位置分类
        for (FamilyDeviceWithPositionBO familyDeviceWithPositionBO : allDeviceList) {
            // 位置信息: 楼层-房间
            String position = getPosition(familyDeviceWithPositionBO.getFloorName(), familyDeviceWithPositionBO.getRoomName());
            if (map.containsKey(position)) {
                map.get(position).add(familyDeviceWithPositionBO);
            } else {
                map.put(position, CollectionUtil.list(true, familyDeviceWithPositionBO));
            }
        }
        // 到这里,设备已经按房间分好类

        // 获取家庭常用设备
        List<FamilyDeviceWithPositionBO> commonDeviceList = familyDeviceService.getCommonDevices(familyId);
        for (FamilyDeviceWithPositionBO commonDevice : commonDeviceList) {
            // 从全部设备中移除所有常用设备
            map.get(getPosition(commonDevice.getFloorName(), commonDevice.getRoomName())).remove(commonDevice);
        }

        // 现在这里的只有不常用的设备了,即使是房间内没有设备,也会显示空数组
        List<FamilyUncommonDeviceVO> familyUncommonDeviceVOList = new LinkedList<>();
        for (String key : map.keySet()) {
            List<DeviceVO> deviceVOList = new LinkedList<>();
            List<FamilyDeviceWithPositionBO> familyDeviceBOList = map.get(key);
            for (FamilyDeviceWithPositionBO familyDeviceWithPositionBO : familyDeviceBOList) {
                DeviceVO deviceVO = new DeviceVO();
                deviceVO.setDeviceId(familyDeviceWithPositionBO.getDeviceId());
                deviceVO.setDeviceName(familyDeviceWithPositionBO.getDeviceName());
                deviceVO.setDeviceIcon(familyDeviceWithPositionBO.getDeviceIcon());
                deviceVO.setIndex(familyDeviceWithPositionBO.getIndex());
                deviceVO.setPosition(getPosition(familyDeviceWithPositionBO.getFloorName(), familyDeviceWithPositionBO.getRoomName()));
                deviceVOList.add(deviceVO);
            }
            FamilyUncommonDeviceVO familyUncommonDeviceVO = new FamilyUncommonDeviceVO();
            familyUncommonDeviceVO.setPositionName(key);
            familyUncommonDeviceVO.setDevices(deviceVOList);
            familyUncommonDeviceVOList.add(familyUncommonDeviceVO);
        }
        return returnSuccess(familyUncommonDeviceVOList);
    }

    @PostMapping("/common/save")
    @ApiOperation("保存常用设备")
    @Transactional(rollbackFor = Exception.class)
    public Response<?> addFamilyDeviceCommon(@RequestBody FamilyDeviceCommonDTO familyDeviceCommonDTO) {
        // 先删除原来的常用设备
        QueryWrapper<FamilyCommonDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyDeviceCommonDTO.getFamilyId());
        familyCommonDeviceService.remove(queryWrapper);

        // 再把新的常用设备添加进去
        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = new LinkedList<>();
        for (String deviceId : familyDeviceCommonDTO.getDevices()) {
            FamilyCommonDeviceDO familyCommonSceneDO = new FamilyCommonDeviceDO();
            familyCommonSceneDO.setFamilyId(familyDeviceCommonDTO.getFamilyId());
            familyCommonSceneDO.setDeviceId(deviceId);
            familyCommonSceneDO.setSortNo(0);
            familyCommonDeviceDOList.add(familyCommonSceneDO);
        }
        familyCommonDeviceService.saveBatch(familyCommonDeviceDOList);
        return returnSuccess();
    }

    @GetMapping("/status/{deviceId}")
    @ApiOperation("查看设备状态")
    public Response<Map<String, Object>> getDeviceStatus(@PathVariable String deviceId) {
        String deviceSn = familyDeviceService.getById(deviceId).getSn();
        List<FamilyDeviceStatusDO> familyDeviceStatusDOList = familyDeviceStatusService.getDeviceAttributionsBySn(deviceSn);
        Map<String, Object> attrMap = new LinkedHashMap<>();
        for (FamilyDeviceStatusDO familyDeviceStatusDO : familyDeviceStatusDOList) {
            String statusCode = familyDeviceStatusDO.getStatusCode();
            String statusValue = familyDeviceStatusDO.getStatusValue();
            attrMap.put(statusCode, statusValue);
        }
        return returnSuccess(attrMap);
    }

    /**
     * 获取设备位置
     *
     * @param floorName 楼层
     * @param roomName  房间
     * @return 房间位置
     */
    public String getPosition(String floorName, String roomName) {
        return String.format("%s-%s", floorName, roomName);
    }

}
