package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.enums.property.*;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilyUncommonDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
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

    @Autowired
    private IHomeAutoProductService productService;

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilyTerminalService familyTerminalService;

    @Autowired
    private IAppService appService;

    /**
     * 获取家庭不常用的设备
     *
     * @param familyId
     * @return
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
        FamilyDeviceDO familyDevice = familyDeviceService.getById(deviceId);
        HomeAutoCategory deviceCategory = familyDeviceService.getDeviceCategory(familyDevice.getSn(), familyDevice.getFamilyId());
        Map<String, Object> attrMap = new LinkedHashMap<>();
        if (Objects.equals(CategoryEnum.PANEL_TEMP, CategoryEnum.get(Integer.valueOf(deviceCategory.getCode())))) {
            log.info("该设备为面板设备,获取暖通数据");
            // 获取温度
            Object temperature = familyDeviceService.getDeviceStatus(deviceId, ProductPropertyEnum.SETTING_TEMPERATURE.code());
            // 获取暖通其他属性
            List<String> attributions = familyDeviceStatusService.getDeviceAttributionsById(deviceId);

            HomeAutoProduct deviceProduct = familyDeviceService.getDeviceProduct(familyDevice.getSn(), familyDevice.getFamilyId());

        } else {
            log.info("该设备为普通设备,正常流程");
            List<String> attributions = familyDeviceStatusService.getDeviceAttributionsById(deviceId);
            for (String attr : attributions) {
                Object deviceStatus = familyDeviceService.getDeviceStatus(deviceId, attr);
                if (Objects.isNull(deviceStatus)) {
                    deviceStatus = defaultValue(attr);
                }
                attrMap.put(attr, deviceStatus);
            }
        }
        return returnSuccess(attrMap);
    }

    @PostMapping("/execute")
    @ApiOperation("设备执行")
    public Response<?> command(@RequestBody DeviceCommandDTO deviceCommandDTO) {
        FamilyDeviceDO familyDeviceDO = familyDeviceService.getById(deviceCommandDTO.getDeviceId());
        HomeAutoProduct product = productService.getById(familyDeviceDO.getProductId());
        HomeAutoFamilyDO familyDO = familyService.getById(familyDeviceDO.getFamilyId());
        FamilyTerminalDO familyTerminalDO = familyTerminalService.getMasterTerminal(familyDeviceDO.getFamilyId());
        AdapterDeviceControlDTO adapterDeviceControlDTO = new AdapterDeviceControlDTO();
        adapterDeviceControlDTO.setFamilyId(familyDeviceDO.getFamilyId());
        adapterDeviceControlDTO.setFamilyCode(familyDO.getCode());
        adapterDeviceControlDTO.setTerminalMac(familyTerminalDO.getMac());
        adapterDeviceControlDTO.setTime(System.currentTimeMillis());
        adapterDeviceControlDTO.setProductCode(product.getCode());
        adapterDeviceControlDTO.setDeviceSn(familyDeviceDO.getSn());
        adapterDeviceControlDTO.setData(deviceCommandDTO.getData());
        adapterDeviceControlDTO.setTerminalType(TerminalTypeEnum.getTerminal(familyTerminalDO.getType()).getCode());
        AdapterDeviceControlAckDTO adapterDeviceControlAckDTO = appService.deviceWriteControl(adapterDeviceControlDTO);
        if (Objects.isNull(adapterDeviceControlAckDTO)) {
            throw new BusinessException("设备无响应,操作失败");
        } else {
            if (Objects.equals(adapterDeviceControlAckDTO.getCode(), 200)) {
                return returnSuccess();
            } else {
                throw new BusinessException(adapterDeviceControlAckDTO.getMessage());
            }
        }
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

    /**
     * 获取属性的默认值
     *
     * @param attr
     * @return
     */
    private Object defaultValue(String attr) {
        ProductPropertyEnum propertyEnum = ProductPropertyEnum.get(attr);
        if (!Objects.isNull(propertyEnum)) {
            switch (propertyEnum) {
                case HUMIDIFICATION_ENABLE:
                    return HumidificationEnableEnum.DEFAULT.getCode();
                case SYSTEM_AIR_VOLUME:
                    return SystemAirVolumeEnum.DEFAULT.getCode();
                case ENERGY_SAVING_MODE:
                    return EnergySavingModeEnum.DEFAULT.getCode();
                case SWITCH:
                    return SwitchEnum.DEFAULT.getCode();
                case ARMING_STATE:
                    return ArmingStateEnum.DEFAULT.getCode();
                case MODE:
                    return ModeEnum.DEFAULT.getCode();
                case AIR_VOLUME:
                    return AirVolumeEnum.DEFAULT.getCode();
                case WIND_SPEED:
                    return WindSpeedEnum.DEFAULT.getCode();
//                case TEMPERATURE:
//                case RETURN_AIR_TEMPERATURE:
//                case SETTING_TEMPERATURE:
//                    return "25";
                default:
                    return null;
            }
        }
        return null;
    }

}
