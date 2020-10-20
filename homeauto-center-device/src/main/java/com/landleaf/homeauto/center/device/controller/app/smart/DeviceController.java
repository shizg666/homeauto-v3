package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.enums.property.SwitchEnum;
import com.landleaf.homeauto.center.device.model.HchoEnum;
import com.landleaf.homeauto.center.device.model.constant.DeviceNatureEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyUncommonDeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
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

    @Autowired
    private IFamilyRoomService familyRoomService;

    /**
     * 通过roomId获取设备列表
     *
     * @param roomId 房间ID
     * @return 设备列表
     */
    @GetMapping("/list/{roomId}")
    @ApiOperation(value = "获取房间设备列表", notes = "点击房间后, 进入房间设备页面时调用这个接口")
    public Response<List<FamilyDeviceVO>> getRoomDevices(@PathVariable String roomId) {
        List<FamilyDeviceBO> familyDeviceBOList = familyDeviceService.listRoomDevice(roomId);
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


    /**
     * 保存常用设备
     *
     * @param familyDeviceCommonDTO 常用设备列表
     * @return 操作结果
     */
    @PostMapping("/common/save")
    @ApiOperation(value = "保存常用设备", notes = "在首页点击添加常用设备后, 点击保存时调用这个接口")
    public Response<Boolean> addFamilyDeviceCommon(@RequestBody FamilyDeviceCommonDTO familyDeviceCommonDTO) {
        familyCommonDeviceService.saveCommonDeviceList(familyDeviceCommonDTO.getFamilyId(), familyDeviceCommonDTO.getDevices());
        return returnSuccess(true);
    }

    /**
     * 获取家庭不常用的设备
     *
     * @param familyId 家庭ID
     * @return 不常用设备列表
     */
    @GetMapping("/uncommon")
    @ApiOperation(value = "获取不常用的设备", notes = "用户从首页点击添加常用设备后获取不常用的设备")
    public Response<List<FamilyUncommonDeviceVO>> getUncommonDevices(@RequestParam String familyId) {
        List<FamilyDeviceDO> familyDeviceDOList = familyDeviceService.listDeviceByFamilyIdAndNature(familyId, DeviceNatureEnum.CONTROLLABLE);
        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = familyCommonDeviceService.listByFamilyId(familyId);
        List<FamilyDeviceBO> uncommonDeviceBOList = familyDeviceService.getFamilyDeviceWithIndex(familyDeviceDOList, familyCommonDeviceDOList, false);
        Map<String, List<FamilyDeviceBO>> familyDeviceMap = uncommonDeviceBOList.stream().collect(Collectors.groupingBy(FamilyDeviceBO::getDevicePosition));
        List<FamilyUncommonDeviceVO> familyUncommonDeviceVOList = new LinkedList<>();
        for (String position : familyDeviceMap.keySet()) {
            List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
            for (FamilyDeviceBO familyDeviceBO : familyDeviceMap.get(position)) {
                FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
                familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
                familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
                familyDeviceVO.setDeviceIcon(familyDeviceBO.getProductIcon());
                familyDeviceVO.setPosition(familyDeviceBO.getDevicePosition());
                familyDeviceVO.setIndex(familyDeviceBO.getDeviceIndex());
                familyDeviceVOList.add(familyDeviceVO);
            }

            FamilyUncommonDeviceVO familyUncommonDeviceVO = new FamilyUncommonDeviceVO();
            familyUncommonDeviceVO.setPositionName(position);
            familyUncommonDeviceVO.setDevices(familyDeviceVOList);
            familyUncommonDeviceVOList.add(familyUncommonDeviceVO);
        }

        // 没有设备的房间也要返回
        List<FamilyRoomBO> familyRoomBOList = familyRoomService.listFamilyRoom(familyId);
        for (FamilyRoomBO familyRoomBO : familyRoomBOList) {
            String position = String.format("%sF-%s", familyRoomBO.getFloorNum(), familyRoomBO.getRoomName());
            if (!familyDeviceMap.containsKey(position)) {
                FamilyUncommonDeviceVO familyUncommonDeviceVO = new FamilyUncommonDeviceVO();
                familyUncommonDeviceVO.setPositionName(position);
                familyUncommonDeviceVO.setDevices(Collections.emptyList());
                familyUncommonDeviceVOList.add(familyUncommonDeviceVO);
            }
        }
        return returnSuccess(familyUncommonDeviceVOList);
    }


    /**
     * 查询设备当前运行状态
     *
     * @param deviceId 设备ID
     * @return 设备状态信息
     */
    @GetMapping("/status/{deviceId}")
    @ApiOperation(value = "查看设备状态", notes = "点击设备后, 进入设备详情页面展示设备当前运行状态")
    public Response<Map<String, Object>> getDeviceStatus(@PathVariable String deviceId) {
        log.info("进入{}接口,请求参数为{}", "/app/smart/device/status/{deviceId}", deviceId);
        FamilyDeviceBO familyDeviceBO = familyDeviceService.detailDeviceById(deviceId);
        Map<String, Object> attrMap = new LinkedHashMap<>();
        if (Objects.equals(CategoryEnum.PANEL_TEMP, CategoryEnum.get(Integer.valueOf(familyDeviceBO.getCategoryCode())))) {
            // 获取温度
            Object temperature = familyDeviceService.getDeviceStatus(deviceId, ProductPropertyEnum.SETTING_TEMPERATURE.code());
            attrMap.put(ProductPropertyEnum.SETTING_TEMPERATURE.code(), familyDeviceService.handleParamValue(familyDeviceBO.getProductCode(), ProductPropertyEnum.SETTING_TEMPERATURE.code(), temperature));

            if (Objects.equals(familyDeviceBO.getRoomType(), RoomTypeEnum.LIVINGROOM)) {
                log.info("该设备为客厅的面板设备");
                // 获取家庭暖通设备
                FamilyDeviceDO familyHvacDevice = familyDeviceService.getFamilyHvacDevice(familyDeviceBO.getFamilyId());
                familyDeviceBO.setDeviceAttributeList(familyDeviceStatusService.getDeviceAttributionsById(familyHvacDevice.getId()));
            } else {
                log.info("该设备为非客厅的面板设备");
                familyDeviceBO.setDeviceAttributeList(familyDeviceStatusService.getDeviceAttributionsById(deviceId));
            }
            familyDeviceBO.getDeviceAttributeList().remove(ProductPropertyEnum.SETTING_TEMPERATURE.code());
        }

        for (String attr : familyDeviceBO.getDeviceAttributeList()) {
            Object deviceStatus = familyDeviceService.getDeviceStatus(deviceId, attr);
            if (!Objects.isNull(deviceStatus)) {
                deviceStatus = familyDeviceService.handleParamValue(familyDeviceBO.getProductCode(), attr, deviceStatus);
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
    @ApiOperation(value = "设备控制", notes = "用户更改设备状态时, 调用这个接口")
    public Response<?> command(@RequestBody DeviceCommandDTO deviceCommandDTO) {
        String deviceId = deviceCommandDTO.getDeviceId();
        String sourceDeviceId = deviceCommandDTO.getSourceDeviceId();
        FamilyDeviceBO sourceDeviceBO = familyDeviceService.detailDeviceById(sourceDeviceId);
        if (Objects.equals(CategoryEnum.get(Integer.parseInt(sourceDeviceBO.getCategoryCode())), CategoryEnum.PANEL_TEMP)) {
            String targetDeviceId = deviceCommandDTO.getTargetDeviceId();

            // 如果是面板类型的设备, 还要去查暖通设备
            ScreenDeviceAttributeDTO attributeDTO = deviceCommandDTO.getData().get(0);

            // 查询暖通设备的开关状态
            Object switchStatus = familyDeviceService.getDeviceStatus(targetDeviceId, ProductPropertyEnum.SWITCH.code());
            SwitchEnum switchEnum = SwitchEnum.getByCode(Objects.toString(switchStatus));

            // 想要控制开关
            boolean isWantSwitch = Objects.equals(attributeDTO.getCode(), ProductPropertyEnum.SWITCH.code());
            // 想要把开关打开
            boolean isWantOn = Objects.equals(attributeDTO.getValue(), SwitchEnum.ON.getCode());
            // 暖通现在是关的
            boolean isHvacOff = Objects.equals(switchEnum, SwitchEnum.OFF);

            if (isWantSwitch && isWantOn && !isHvacOff) {
                // 想要把开关打开, 但是暖通已经开着
                throw new BusinessException(90002, "暖通已经打开了");
            } else if (isHvacOff) {
                // 想要控制其他的属性, 但是暖通却是关闭状态
                throw new BusinessException(90002, "请先打开暖通");
            }
        }

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

}
