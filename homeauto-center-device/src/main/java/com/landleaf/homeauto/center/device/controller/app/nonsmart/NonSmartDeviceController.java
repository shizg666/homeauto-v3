package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DevicePositionVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyTerminalService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 设备控制器
 *
 * @author Yujiumin
 * @version 2020/8/31
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/app/non-smart/device")
@Api(tags = "自由方舟APP设备接口")
public class NonSmartDeviceController extends BaseController {

    private IFamilyDeviceStatusService familyDeviceStatusService;

    private IFamilyDeviceService familyDeviceService;

    private IFamilyRoomService familyRoomService;

    /**
     * 自由方舟 APP 查看设备状态接口
     * 自由方舟只有面板设备:
     * - 总控面板: 位置在客厅的面板
     * - 分控面板: 位置不在客厅的面板
     * <p>
     * -- 1. 总控面板:
     * ---- 1.1 查询面板的温度
     * ---- 1.2 查询暖通的属性
     * ---- 1.3 查询暖通的开关状态
     * <p>
     * -- 2. 分控面板:
     * ---- 2.1 查询面板的温度
     * ---- 2.2 查询面板的开关状态
     *
     * @param deviceId 设备 ID
     * @return 设备的状态
     */
    @GetMapping("/status/{deviceId}")
    @ApiOperation("查看设备状态")
    public Response<Map<String, Object>> getDeviceStatus(@PathVariable String deviceId) {
        log.info("查询设备状态,设备ID为:{}", deviceId);

        DeviceBO deviceBO = familyDeviceService.getDeviceById(deviceId);
        Map<String, Object> attrMap = new LinkedHashMap<>();

        // 获取温度
        log.info("获取设备的温度属性,设备ID为:{}", deviceId);
        Object temperature = familyDeviceService.getDeviceStatus(deviceId, ProductPropertyEnum.SETTING_TEMPERATURE.code());
        log.info("温度获取成功,设备ID为:{}, 温度值为:{}, 即将进入精度处理", deviceId, temperature);
        temperature = familyDeviceService.handleParamValue(deviceBO.getProductCode(), ProductPropertyEnum.SETTING_TEMPERATURE.code(), temperature);
        log.info("精度处理完成, 处理后的温度值为:{}", temperature);
        attrMap.put(ProductPropertyEnum.SETTING_TEMPERATURE.code(), temperature);

        // 获取设备开关
        log.info("获取设备的开关属性,设备ID为:{}", deviceId);
        Object switchStatus = familyDeviceService.getDeviceStatus(deviceId, ProductPropertyEnum.SWITCH.code());
        log.info("开关状态获取成功,设备ID为:{}, 开关状态值为:{}", deviceId, switchStatus);
        attrMap.put(ProductPropertyEnum.SWITCH.code(), switchStatus);

        // 如果面板是客厅的,则进入分支
        if (Objects.equals(deviceBO.getRoomType(), RoomTypeEnum.LIVINGROOM)) {
            // 客厅的面板,获取暖通的设备状态
            log.info("检测到该面板挂载在客厅下, 即将附属暖通设备状态");
            FamilyDeviceDO familyHvacDevice = familyDeviceService.getFamilyHvacDevice(deviceBO.getFamilyId());
            // 该家庭下的暖通设备
            String hvacDeviceId = familyHvacDevice.getId();
            log.info("获取暖通设备状态成功, 设备ID为:{}", hvacDeviceId);
            List<String> hvacAttributeList = familyDeviceStatusService.getDeviceAttributionsById(hvacDeviceId);
            log.info("获取暖通属性成功, 设备ID为:{}, 属性列表为:{}", hvacDeviceId, hvacAttributeList);
            for (String attr : hvacAttributeList) {
                Object deviceStatus = familyDeviceService.getDeviceStatus(hvacDeviceId, attr);
                log.info("属性值为:{}, 属性为:{}, 设备 ID 为:{}", deviceStatus, attr, hvacDeviceId);
                if (Objects.isNull(deviceStatus)) {
                    // 设备的属性值为空,返回默认值
                    log.info("暖通的属性值为空, 即将获取默认值, 设备 ID 为: {}, 属性为: {}", hvacDeviceId, attr);
                    deviceStatus = familyDeviceStatusService.getDefaultValue(attr);
                    log.info("默认值为:{}", deviceStatus);
                } else {
                    // 设备属性值不为空
                    log.info("{} 属性的值为 {}, 即将进入精度处理", attr, deviceStatus);
                    deviceStatus = familyDeviceService.handleParamValue(deviceBO.getProductCode(), attr, deviceStatus);
                    log.info("{} 属性精度处理后的值为: {}", attr, deviceStatus);
                }
                attrMap.put(attr, deviceStatus);
            }
        }

        return returnSuccess(attrMap);
    }

    /**
     * 自由方舟 APP 设备控制接口
     * 自由方舟只有面板设备, 逻辑上分为总控面板和分控面板
     * 本质上它们都属于面板,只是由于位置的不同而导致功能不同
     * 部署在客厅的面板属于总控面板, 否则为分控面板
     * <p>
     * -- APP控制设备逻辑:
     * ---- 1. 总控面板:
     * ------ 1.1 温度控制: 上传面板的设备ID
     * ------ 1.2 开关控制: 上传暖通的设备ID
     * ------ 1.3 其他控制: 上传暖通的设备ID
     * <p>
     * ---- 2. 分控面板:
     * ------ 2.1 温度控制: 上传面板的设备ID
     * ------ 2.2 开关控制: 上传面板的设备ID
     *
     * @param deviceCommandDTO 设备控制指令
     * @return 执行结果
     */
    @PostMapping("/execute")
    @ApiOperation("设备执行")
    public Response<?> command(@RequestBody DeviceCommandDTO deviceCommandDTO) {
        String deviceId = deviceCommandDTO.getDeviceId();
        List<ScreenDeviceAttributeDTO> data = deviceCommandDTO.getData();
        log.info("进入自由方舟设备控制接口,设备ID为:{}, 控制信息为:{}", deviceId, data);

        log.info("获取设备信息, 设备ID为:{}", deviceId);
        FamilyDeviceDO familyDeviceDO = familyDeviceService.getById(deviceId);

        String familyId = familyDeviceDO.getFamilyId();
        String deviceSn = familyDeviceDO.getSn();
        log.info("获取设备信息成功,家庭ID为:{}, 设备SN号为:{}", familyId, deviceSn);

        familyDeviceService.sendCommand(familyDeviceDO, data);
        return returnSuccess();
    }

    /**
     * 获取家庭下的设备列表(按房间分类)
     *
     * @param familyId 家庭ID
     * @return 设备列表
     */
    @GetMapping("/list/{familyId}")
    @ApiOperation("获取家庭设备列表")
    public Response<List<DevicePositionVO>> getFamilyDevices(@PathVariable String familyId) {
        List<DevicePositionVO> devicePositionVOList = CollectionUtil.list(true);
        List<FamilyRoomDO> roomList = familyRoomService.getRoom(familyId);
        for (FamilyRoomDO familyRoomDO : roomList) {
            DevicePositionVO devicePositionVO = new DevicePositionVO();
            devicePositionVO.setRoom(familyRoomDO.getName());
            List<FamilyDeviceDO> deviceList = familyDeviceService.getDeviceListByRoomId(familyRoomDO.getId());
            List<DeviceVO> deviceVOList = CollectionUtil.list(true);
            for (FamilyDeviceDO familyDeviceDO : deviceList) {
                DeviceVO deviceVO = new DeviceVO();
                deviceVO.setDeviceId(familyDeviceDO.getId());
                deviceVO.setDeviceName(familyDeviceDO.getName());
                deviceVO.setDeviceIcon(familyDeviceService.getDeviceIconById(familyDeviceDO.getId()));
                deviceVOList.add(deviceVO);
            }
            devicePositionVO.setDevices(deviceVOList);
            devicePositionVOList.add(devicePositionVO);
        }
        return returnSuccess(devicePositionVOList);
    }

}
