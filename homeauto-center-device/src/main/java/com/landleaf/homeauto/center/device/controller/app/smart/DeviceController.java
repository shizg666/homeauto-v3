package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.HchoEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilyUncommonDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    /**
     * 获取家庭不常用的设备
     *
     * @param familyId 家庭ID
     * @return 不常用设备列表
     */
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
        log.info("进入{}接口,请求参数为{}", "/app/smart/device/status/{deviceId}", deviceId);
        DeviceBO deviceBO = familyDeviceService.getDeviceById(deviceId);
        Map<String, Object> attrMap = new LinkedHashMap<>();
        if (Objects.equals(CategoryEnum.PANEL_TEMP, CategoryEnum.get(Integer.valueOf(deviceBO.getCategoryCode())))) {
            // 获取温度
            Object temperature = familyDeviceService.getDeviceStatus(deviceId, ProductPropertyEnum.SETTING_TEMPERATURE.code());
            attrMap.put(ProductPropertyEnum.SETTING_TEMPERATURE.code(), familyDeviceService.handleParamValue(deviceBO.getProductCode(), ProductPropertyEnum.SETTING_TEMPERATURE.code(), temperature));

            if (Objects.equals(deviceBO.getRoomType(), RoomTypeEnum.LIVINGROOM)) {
                log.info("该设备为客厅的面板设备");
                // 获取家庭暖通设备
                FamilyDeviceDO familyHvacDevice = familyDeviceService.getFamilyHvacDevice(deviceBO.getFamilyId());
                deviceBO.setDeviceAttributeList(familyDeviceStatusService.getDeviceAttributionsById(familyHvacDevice.getId()));
            } else {
                log.info("该设备为非客厅的面板设备");
                deviceBO.setDeviceAttributeList(familyDeviceStatusService.getDeviceAttributionsById(deviceId));
            }
            deviceBO.getDeviceAttributeList().remove(ProductPropertyEnum.SETTING_TEMPERATURE.code());
        }

        for (String attr : deviceBO.getDeviceAttributeList()) {
            Object deviceStatus = familyDeviceService.getDeviceStatus(deviceId, attr);
            if (!Objects.isNull(deviceStatus)) {
                deviceStatus = familyDeviceService.handleParamValue(deviceBO.getProductCode(), attr, deviceStatus);
                if (Objects.equals(attr, "formaldehyde")) {
                    deviceStatus = HchoEnum.getAqi(Float.parseFloat(Objects.toString(deviceStatus)));
                }
            } else {
                deviceStatus = familyDeviceStatusService.getDefaultValue(attr);
            }

            attrMap.put(attr, deviceStatus);
        }
        return returnSuccess(attrMap);
    }

    /**
     * 户式化 APP 设备控制接口
     *
     * @param deviceCommandDTO 指令
     * @return 执行结果
     */
    @PostMapping("/execute")
    @ApiOperation("设备执行")
    public Response<?> command(@RequestBody DeviceCommandDTO deviceCommandDTO) {
        String deviceId = deviceCommandDTO.getDeviceId();
        List<ScreenDeviceAttributeDTO> data = deviceCommandDTO.getData();
        log.info("进入户式化设备控制接口,设备ID为:{}, 控制信息为:{}", deviceId, data);

        log.info("获取设备信息, 设备ID为:{}", deviceId);
        FamilyDeviceDO familyDeviceDO = familyDeviceService.getById(deviceId);

        String familyId = familyDeviceDO.getFamilyId();
        String deviceSn = familyDeviceDO.getSn();
        log.info("获取设备信息成功,家庭ID为:{}, 设备SN号为:{}", familyId, deviceSn);

        familyDeviceService.sendCommand(familyDeviceDO, data);
        return returnSuccess();
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
