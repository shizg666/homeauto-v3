package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.vo.DeviceInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.DevicePositionVO;
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
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/app/non-smart/device")
@Api(tags = "自由方舟APP设备接口")
public class NonSmartDeviceController extends BaseController {

    @Autowired
    private IFamilyDeviceStatusService familyDeviceStatusService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IHomeAutoProductService productService;

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilyTerminalService familyTerminalService;

    @Autowired
    private IFamilyRoomService familyRoomService;

    @Autowired
    private IAppService appService;

    @GetMapping("/status/{deviceId}")
    @ApiOperation("获取设备状态")
    public Response<Map<String, Object>> getDeviceStatus(@PathVariable String deviceId) {
        String deviceSn = familyDeviceService.getById(deviceId).getSn();
        List<FamilyDeviceStatusDO> deviceStatusDOList = familyDeviceStatusService.getDeviceAttributionsBySn(deviceSn);
        Map<String, Object> attrMap = new LinkedHashMap<>();
        for (FamilyDeviceStatusDO familyDeviceStatusDO : deviceStatusDOList) {
            String statusCode = familyDeviceStatusDO.getStatusCode();
            String statusValue = familyDeviceStatusDO.getStatusValue();
            attrMap.put(statusCode, statusValue);
        }
        return returnSuccess(attrMap);
    }

    @PostMapping("/execute")
    @ApiOperation("设备执行")
    public Response<?> command(@RequestBody DeviceCommandDTO deviceCommandDTO) {
        FamilyDeviceDO familyDeviceDO = familyDeviceService.getById(deviceCommandDTO.getDeviceId());
        HomeAutoProduct product = productService.getById(familyDeviceDO.getProductId());
        HomeAutoFamilyDO familyDO = familyService.getById(familyDeviceDO.getFamilyId());
        FamilyTerminalDO familyTerminalDO = familyTerminalService.getById(familyDeviceDO.getTerminalId());
        AdapterDeviceControlDTO adapterDeviceControlDTO = new AdapterDeviceControlDTO();
        adapterDeviceControlDTO.setFamilyId(familyDeviceDO.getFamilyId());
        adapterDeviceControlDTO.setFamilyCode(familyDO.getCode());
        adapterDeviceControlDTO.setTerminalMac(familyTerminalDO.getMac());
        adapterDeviceControlDTO.setTime(System.currentTimeMillis());
        adapterDeviceControlDTO.setProductCode(product.getCode());
        adapterDeviceControlDTO.setDeviceSn(familyDeviceDO.getSn());
        adapterDeviceControlDTO.setData(deviceCommandDTO.getData());
        adapterDeviceControlDTO.setTerminalType(TerminalTypeEnum.getTerminal(familyTerminalDO.getType()).getCode());
        adapterDeviceControlDTO.setTerminalMac(familyTerminalDO.getMac());
        AdapterDeviceControlAckDTO adapterDeviceControlAckDTO = appService.deviceWriteControl(adapterDeviceControlDTO);
        if (Objects.equals(adapterDeviceControlAckDTO.getCode(), 200)) {
            return returnSuccess();
        } else {
            throw new BusinessException(adapterDeviceControlAckDTO.getMessage());
        }
    }

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
