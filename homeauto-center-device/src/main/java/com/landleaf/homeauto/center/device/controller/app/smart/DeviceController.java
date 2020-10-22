package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.hutool.core.util.NumberUtil;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.enums.property.ModeEnum;
import com.landleaf.homeauto.center.device.enums.property.SwitchEnum;
import com.landleaf.homeauto.center.device.model.HchoEnum;
import com.landleaf.homeauto.center.device.model.VocEnum;
import com.landleaf.homeauto.center.device.model.constant.DeviceNatureEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyDeviceCommonDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.*;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyUncommonDeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.StringUtil;
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

    @Autowired
    private IProductAttributeService productAttributeService;

    @Autowired
    private IProductAttributeInfoScopeService productAttributeInfoScopeService;

    @Autowired
    private IHomeAutoFamilyService familyService;

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
    public Response<?> addFamilyDeviceCommon(@RequestBody FamilyDeviceCommonDTO familyDeviceCommonDTO) {
        familyCommonDeviceService.saveCommonDeviceList(familyDeviceCommonDTO.getFamilyId(), familyDeviceCommonDTO.getDevices());
        return returnSuccess();
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
        log.info("检查家庭的审核状态, 家庭ID: {}", familyId);
        FamilyReviewStatusEnum familyReviewStatusEnum = familyService.getFamilyReviewStatus(familyId);
        if (Objects.equals(familyReviewStatusEnum, FamilyReviewStatusEnum.AUTHORIZATION)) {
            throw new BusinessException(90001, "当前家庭授权状态更改中");
        }

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
        log.info("户式化APP: 查看设备状态 -> 开始");
        log.info("设备ID: {}", deviceId);
        FamilyDeviceBO familyDeviceBO = familyDeviceService.detailDeviceById(deviceId);
        CategoryEnum categoryEnum = CategoryEnum.get(familyDeviceBO.getCategoryCode());
        Map<String, Object> deviceStatusMap = new LinkedHashMap<>();
        if (Objects.equals(categoryEnum, CategoryEnum.PANEL_TEMP)) {
            // 如果设备是温控面板
            log.info("该设备为温控面板设备");
            ProductPropertyEnum settingTemperature = ProductPropertyEnum.SETTING_TEMPERATURE;
            // 1. 获取温度: 温度挂载在温控面板下面, 以温控面板设备ID查询温度
            Object temperatureValue = familyDeviceService.getDeviceStatus(deviceId, settingTemperature);
            temperatureValue = familyDeviceService.handleParamValue(familyDeviceBO.getProductCode(), settingTemperature, temperatureValue);
            temperatureValue = Objects.isNull(temperatureValue) ? familyDeviceStatusService.getDefaultValue(settingTemperature.code()) : temperatureValue;
            deviceStatusMap.put(settingTemperature.code(), temperatureValue);

            // 2. 查询家庭的唯一的暖通, 以暖通的设备ID查询暖通的属性
            FamilyDeviceBO hvacDevice = familyDeviceService.getHvacDevice(familyDeviceBO.getFamilyId());
            List<ProductAttributeDO> productAttributeDOList = productAttributeService.listByProductCode(hvacDevice.getProductCode());
            for (ProductAttributeDO productAttributeDO : productAttributeDOList) {
                String attributeCode = productAttributeDO.getCode();
                Object attributeValue = familyDeviceService.getDeviceStatus(hvacDevice.getDeviceId(), attributeCode);
                attributeValue = familyDeviceService.handleParamValue(hvacDevice.getProductCode(), attributeCode, attributeValue);

                // 获取属性的取值范围
                List<ProductAttributeValueScopeBO> productAttributeValueScopeBOList = productAttributeInfoScopeService.getByProductAttributeId(productAttributeDO.getId());
                for (ProductAttributeValueScopeBO productAttributeValueScopeBO : productAttributeValueScopeBOList) {
                    if (Objects.equals(productAttributeValueScopeBO.getAttributeValue(), attributeValue)) {
                        String minValue = productAttributeValueScopeBO.getMinValue();
                        if (!StringUtil.isEmpty(minValue)) {
                            deviceStatusMap.put("minValue", minValue);
                        }

                        String maxValue = productAttributeValueScopeBO.getMaxValue();
                        if (!StringUtil.isEmpty(maxValue)) {
                            deviceStatusMap.put("maxValue", maxValue);
                        }
                        attributeValue = handleOutOfRangeValue(productAttributeValueScopeBO, attributeValue);
                        break;
                    }
                }
                deviceStatusMap.put(attributeCode, attributeValue);
            }
        } else {
            // 如果不是温控面板, 正常查询设备的属性及其状态
            log.info("该设备不是温控面板设备");
            List<ProductAttributeDO> productAttributeDOList = productAttributeService.listByProductCode(familyDeviceBO.getProductCode());
            for (ProductAttributeDO productAttributeDO : productAttributeDOList) {
                String attributeCode = productAttributeDO.getCode();
                Object attributeValue = familyDeviceService.getDeviceStatus(deviceId, attributeCode);
                attributeValue = familyDeviceService.handleParamValue(familyDeviceBO.getProductCode(), attributeCode, attributeValue);
                attributeValue = Objects.isNull(attributeValue) ? familyDeviceStatusService.getDefaultValue(productAttributeDO.getCode()) : attributeValue;

                // 获取属性的取值范围
                List<ProductAttributeValueScopeBO> productAttributeValueScopeBOList = productAttributeInfoScopeService.getByProductAttributeId(productAttributeDO.getId());
                for (ProductAttributeValueScopeBO productAttributeValueScopeBO : productAttributeValueScopeBOList) {
                    if (Objects.equals(productAttributeValueScopeBO.getAttributeValue(), attributeValue)) {
                        String minValue = productAttributeValueScopeBO.getMinValue();
                        if (!StringUtil.isEmpty(minValue)) {
                            deviceStatusMap.put("minValue", minValue);
                        }

                        String maxValue = productAttributeValueScopeBO.getMaxValue();
                        if (!StringUtil.isEmpty(maxValue)) {
                            deviceStatusMap.put("maxValue", maxValue);
                        }
                        attributeValue = handleOutOfRangeValue(productAttributeValueScopeBO, attributeValue);
                        break;
                    }
                }
                deviceStatusMap.put(attributeCode, attributeValue);
            }
        }

        log.info("设备属性查询完毕, 开始对属性值做空气质量处理");
        for (String attribute : deviceStatusMap.keySet()) {
            Object deviceStatus = deviceStatusMap.get(attribute);
            if (Objects.equals(attribute, ProductPropertyEnum.HCHO.code())) {
                deviceStatus = HchoEnum.getAqi(Float.parseFloat(Objects.toString(deviceStatus)));
            } else if (Objects.equals(attribute, ProductPropertyEnum.VOC.code())) {
                deviceStatus = VocEnum.getAqi(Float.parseFloat(Objects.toString(deviceStatus)));
            }
            deviceStatusMap.replace(attribute, deviceStatus);
        }
        return returnSuccess(deviceStatusMap);
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
        FamilyDeviceBO familyDeviceBO = familyDeviceService.detailDeviceById(deviceId);
        List<ScreenDeviceAttributeDTO> data = deviceCommandDTO.getData();
        if (Objects.equals(CategoryEnum.get(familyDeviceBO.getCategoryCode()), CategoryEnum.PANEL_TEMP)) {
            // 如果设备是温控面板设备, 则要检查暖通状态
            FamilyDeviceBO hvacDevice = familyDeviceService.getHvacDevice(familyDeviceBO.getFamilyId());
            // 查询暖通的开关状态
            Object switchStatus = familyDeviceService.getDeviceStatus(hvacDevice.getDeviceId(), ProductPropertyEnum.SWITCH.code());
            SwitchEnum switchEnum = SwitchEnum.getByCode(Objects.toString(switchStatus));

            // 查询即将发送的指令信息
            ScreenDeviceAttributeDTO attributeDTO = data.get(0);
            String attributeCode = attributeDTO.getCode();
            ProductPropertyEnum propertyEnum = ProductPropertyEnum.get(attributeCode);

            if (!Objects.isNull(propertyEnum)) {
                // 只有已知属性可以操作
                if (Objects.equals(switchEnum, SwitchEnum.ON)) {
                    // 如果暖通开着, 直接发送指令
                    if (Objects.equals(propertyEnum, ProductPropertyEnum.MODE) || Objects.equals(propertyEnum, ProductPropertyEnum.WIND_SPEED)) {
                        // 如果是控制模式或者风速, 则使用暖通的设备ID
                        familyDeviceService.sendCommand(familyDeviceService.getById(hvacDevice.getDeviceId()), data);
                    } else {
                        // 如果不是控制模式或者风速, 则使用面板的设备ID
                        familyDeviceService.sendCommand(familyDeviceService.getById(deviceId), data);
                    }
                } else if (Objects.equals(switchEnum, SwitchEnum.OFF)) {
                    // 如果暖通关着, 并且操作并不是打开开关
                    SwitchEnum targetSwitchEnum = SwitchEnum.getByCode(attributeDTO.getValue());
                    if (Objects.equals(propertyEnum, ProductPropertyEnum.SWITCH) && Objects.equals(targetSwitchEnum, SwitchEnum.ON)) {
                        familyDeviceService.sendCommand(familyDeviceService.getById(hvacDevice.getDeviceId()), data);
                    }
                    throw new BusinessException(90000, "请先打开暖通");
                } else {
                    // 暖通既没有开着, 也没有关着(奇怪吧!!)
                    log.info("暖通既不是开着, 也不是关着的状态");
                }
            } else {
                throw new BusinessException(90000, "未知操作");
            }
        } else {
            // 如果不是温控面板设备, 则把该设备ID以及指令发出去
            FamilyDeviceDO familyDeviceDO = familyDeviceService.getById(deviceId);
            familyDeviceService.sendCommand(familyDeviceDO, data);
        }
        return returnSuccess();
    }

    private Object handleOutOfRangeValue(ProductAttributeValueScopeBO productAttributeValueScopeBO, Object value) {
        String minValue = productAttributeValueScopeBO.getMinValue();
        String maxValue = productAttributeValueScopeBO.getMaxValue();
        if (NumberUtil.isNumber(Objects.toString(value))) {
            float floatValue = Float.parseFloat(Objects.toString(value));
            if (!StringUtil.isEmpty(minValue)) {
                // 如果最小值不为空
                float floatMinValue = Float.parseFloat(minValue);
                value = Math.max(floatValue, floatMinValue);
            }

            if (!StringUtil.isEmpty(maxValue)) {
                // 如果最大值不为空
                float floatMinValue = Float.parseFloat(minValue);
                value = Math.min(floatValue, floatMinValue);
            }
        }
        return value;
    }
}
