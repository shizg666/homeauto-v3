package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.HchoEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyBO;
import com.landleaf.homeauto.center.device.model.bo.SimpleFamilyBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.vo.EnvironmentVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.IndexOfNonSmartVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.NonSmartRoomDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionQryDTO;
import com.landleaf.homeauto.common.enums.category.PrecisionEnum;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/8/31
 */
@Slf4j
@RestController
@RequestMapping("/app/non-smart/family")
@Api(tags = "自由方舟APP家庭接口")
public class NonSmartFamilyController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilySceneService familySceneService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IFamilyCommonSceneService familyCommonSceneService;

    @Autowired
    private IFamilyCommonDeviceService familyCommonDeviceService;

    @Autowired
    private IFamilyRoomService familyRoomService;

    @Autowired
    private IProductAttributeErrorService productAttributeErrorService;

    @GetMapping("/list/{userId}")
    @ApiOperation("获取家庭列表")
    public Response<FamilyVO> getFamily(@PathVariable String userId) {
        List<FamilyBO> familyBOList = familyService.getFamilyListByUserId(userId);
        FamilyVO familyVO = new FamilyVO();
        for (FamilyBO familyBO : familyBOList) {
            SimpleFamilyBO family = new SimpleFamilyBO();
            family.setFamilyId(familyBO.getFamilyId());
            family.setFamilyName(familyBO.getFamilyName());
            if (Objects.equals(familyBO.getLastChecked(), 1)) {
                SimpleFamilyBO simpleFamilyBO = new SimpleFamilyBO();
                simpleFamilyBO.setFamilyId(family.getFamilyId());
                simpleFamilyBO.setFamilyName(family.getFamilyName());
                familyVO.setCurrent(simpleFamilyBO);
            }
            if (Objects.nonNull(familyVO.getList())) {
                familyVO.getList().add(family);
            } else {
                List<SimpleFamilyBO> tmpList = Lists.newArrayList();
                SimpleFamilyBO tmpBo = new SimpleFamilyBO();
                BeanUtils.copyProperties(family, tmpBo);
                tmpList.add(tmpBo);
                familyVO.setList(tmpList);
            }
        }
        return returnSuccess(familyVO);
    }

    @GetMapping("/checkout/{familyId}")
    @ApiOperation("切换家庭")
    public Response<IndexOfNonSmartVO> getFamilyCommonScenesAndDevices(@PathVariable String familyId) {
        // 1. 获取室内环境参数
        //// 获取全参数传感器
        Map<String, Object> environmentMap = new LinkedHashMap<>();
        DeviceSensorBO allParamSensor = familyDeviceService.getAllParamSensor(familyId);
        if (!Objects.isNull(allParamSensor)) {
            // 全参传感器不为空
            List<String> attributeList = allParamSensor.getAttributeList();
            if (!CollectionUtils.isEmpty(attributeList)) {
                for (String attribute : attributeList) {
                    Object deviceStatus = familyDeviceService.getDeviceStatus(allParamSensor, attribute);
                    environmentMap.put(attribute, handleParamValue(allParamSensor.getProductCode(), attribute, deviceStatus));
                }
            }
        }

        //// 获取甲醛传感器
        DeviceSensorBO hchoSensor = familyDeviceService.getHchoSensor(familyId);
        if (!Objects.isNull(hchoSensor)) {
            Object formaldehyde = familyDeviceService.getDeviceStatus(hchoSensor, "formaldehyde");
            environmentMap.replace("formaldehyde", handleParamValue(hchoSensor.getProductCode(), "formaldehyde", formaldehyde));
        }

        //// 获取pm2.5
        DeviceSensorBO pm25Sensor = familyDeviceService.getPm25Sensor(familyId);
        if (!Objects.isNull(pm25Sensor)) {
            Object pm25 = familyDeviceService.getDeviceStatus(pm25Sensor, "pm25");
            environmentMap.replace("pm25", handleParamValue(pm25Sensor.getProductCode(), "pm25", pm25));
        }

        EnvironmentVO environmentVO = new EnvironmentVO();
        environmentVO.setPm25(Objects.toString(environmentMap.get("pm25")));
        environmentVO.setHcho(Objects.toString(environmentMap.get("formaldehyde")));
        environmentVO.setTemperature(Objects.toString(environmentMap.get("temperature")));
        environmentVO.setHumidity(Objects.toString(environmentMap.get("humidity")));
        environmentVO.setCo2(Objects.toString(environmentMap.get("co2")));

        // 2. 获取常用场景
        List<String> commonSceneIdList = familyCommonSceneService.getCommonSceneIdListByFamilyId(familyId);
        List<SceneVO> sceneVOList = CollectionUtil.list(true);
        if (!CollectionUtils.isEmpty(commonSceneIdList)) {
            List<FamilySceneDO> sceneDOList = CollectionUtil.list(true, familySceneService.listByIds(commonSceneIdList));
            for (FamilySceneDO familySceneDO : sceneDOList) {
                SceneVO sceneVO = new SceneVO();
                sceneVO.setSceneId(familySceneDO.getId());
                sceneVO.setSceneName(familySceneDO.getName());
                sceneVO.setSceneIcon(familySceneDO.getIcon());
                sceneVOList.add(sceneVO);
            }
        }

        // 4. 获取各个房间的设备
        List<NonSmartRoomDeviceVO> roomDeviceVOList = new LinkedList<>();

        //// 获取常用设备 => 暂时不做
//        List<String> commonDeviceIdList = familyCommonDeviceService.getCommonDeviceIdListByFamilyId(familyId).stream().map(FamilyCommonDeviceDO::getDeviceId).collect(Collectors.toList());
//        List<DeviceVO> commonDeviceVOList = CollectionUtil.list(true);
//        if (!CollectionUtil.isEmpty(commonDeviceIdList)) {
//            List<FamilyDeviceDO> deviceDOList = CollectionUtil.list(true, familyDeviceService.listByIds(commonDeviceIdList));
//            for (FamilyDeviceDO familyDeviceDO : deviceDOList) {
//                commonDeviceVOList.add(toDeviceVO(familyDeviceDO));
//            }
//        }
//        NonSmartRoomDeviceVO commonDevice = new NonSmartRoomDeviceVO();
//        commonDevice.setRoomName("常用设备");
//        commonDevice.setDevices(commonDeviceVOList);
//        roomDeviceVOList.add(commonDevice);

        //// 获取房间设备
        List<FamilyRoomDO> roomList = familyRoomService.getRoom(familyId);
        for (FamilyRoomDO familyRoomDO : roomList) {
            String position = familyRoomService.getById(familyRoomDO.getId()).getName();
            List<FamilyDeviceDO> deviceList = familyDeviceService.getDeviceListByRoomId(familyRoomDO.getId());
            List<DeviceVO> deviceVOList = CollectionUtil.list(true);
            for (FamilyDeviceDO familyDeviceDO : deviceList) {
                deviceVOList.add(toDeviceVO(familyDeviceDO));
            }
            NonSmartRoomDeviceVO nonSmartRoomDeviceVO = new NonSmartRoomDeviceVO();
            nonSmartRoomDeviceVO.setRoomName(position);
            nonSmartRoomDeviceVO.setDevices(deviceVOList);
            roomDeviceVOList.add(nonSmartRoomDeviceVO);
        }
        return returnSuccess(new IndexOfNonSmartVO(environmentVO, sceneVOList, roomDeviceVOList));
    }

    /**
     * 把数据库对象转化为视图对象
     *
     * @param familyDeviceDO 数据库对象
     * @return 视图对象
     */
    private DeviceVO toDeviceVO(FamilyDeviceDO familyDeviceDO) {
        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setDeviceId(familyDeviceDO.getId());
        deviceVO.setDeviceName(familyDeviceDO.getName());
        deviceVO.setPosition(familyRoomService.getById(familyDeviceDO.getRoomId()).getName());
        deviceVO.setDeviceIcon(familyDeviceService.getDeviceIconById(familyDeviceDO.getId()));
        deviceVO.setCategoryCode(familyDeviceService.getDeviceCategory(familyDeviceDO.getSn(),familyDeviceDO.getFamilyId()).getCode());
        return deviceVO;
    }

    private Object handleParamValue(String productCode, String attributeCode, Object value) {
        AttributePrecisionQryDTO attributePrecisionQryDTO = new AttributePrecisionQryDTO();
        attributePrecisionQryDTO.setProductCode(productCode);
        attributePrecisionQryDTO.setCode(attributeCode);
        List<AttributePrecisionDTO> attributePrecision = productAttributeErrorService.getAttributePrecision(attributePrecisionQryDTO);
        AttributePrecisionDTO attributePrecisionDTO = attributePrecision.get(0);
        return PrecisionEnum.getInstByType(attributePrecisionDTO.getPrecision()).parse(value);
    }
}
