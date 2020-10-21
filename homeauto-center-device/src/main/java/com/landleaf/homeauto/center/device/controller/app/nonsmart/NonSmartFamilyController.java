package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.*;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.dto.energy.EnergyModeDTO;
import com.landleaf.homeauto.center.device.model.vo.EnvironmentVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.IndexOfNonSmartVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.NonSmartRoomDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    private IFamilyUserCheckoutService familyUserCheckoutService;

    @Autowired
    private IFamilyRoomService familyRoomService;

    @Autowired
    private IFamilyUserService familyUserService;

    @Autowired
    private IEnergyModeService energyModeService;

    /**
     * 获取用户家庭列表
     *
     * @param userId 用户id
     * @return 家庭列表
     */
    @GetMapping("/list/{userId}")
    @ApiOperation("获取家庭列表")
    public Response<FamilyVO> getFamily(@PathVariable String userId) {
        List<FamilyBO> familyBOList = familyService.getFamilyListByUserId(userId);

        FamilyVO familyVO = new FamilyVO();
        List<SimpleFamilyBO> simpleFamilyBOList = Lists.newArrayList();
        for (FamilyBO familyBO : familyBOList) {
            SimpleFamilyBO simpleFamilyBO = new SimpleFamilyBO();
            simpleFamilyBO.setFamilyId(familyBO.getFamilyId());
            simpleFamilyBO.setFamilyName(familyBO.getFamilyName());
            simpleFamilyBOList.add(simpleFamilyBO);
        }
        // 临时修改没有
        if(CollectionUtils.isEmpty(familyBOList)){
            familyVO.setCurrent(null);
            familyVO.setList(simpleFamilyBOList);
            return returnSuccess(familyVO);
        }
        FamilyUserCheckout familyUserCheckout = familyUserCheckoutService.getByUserId(userId);
        SimpleFamilyBO simpleFamilyBO = new SimpleFamilyBO();
        if (Objects.isNull(familyUserCheckout)) {
            simpleFamilyBO = simpleFamilyBOList.get(0);
        } else {
            HomeAutoFamilyDO familyDO = familyService.getById(familyUserCheckout.getFamilyId());
            simpleFamilyBO.setFamilyId(familyDO.getId());
            simpleFamilyBO.setFamilyName(familyDO.getName());
        }
        familyVO.setCurrent(simpleFamilyBO);
        familyVO.setList(simpleFamilyBOList);
        return returnSuccess(familyVO);
    }


    /**
     * 切换家庭
     *
     * @param familyId
     * @return
     */
    @GetMapping("/checkout/{familyId}")
    @ApiOperation("切换家庭")
    public Response<IndexOfNonSmartVO> getFamilyCommonScenesAndDevices(@PathVariable String familyId) {
        // 把上一次的家庭切换为当前家庭
        log.info("进入切换家庭接口, 即将切换的家庭ID为:{}", familyId);
        HomeAutoToken homeAutoToken = TokenContext.getToken();
        if (Objects.isNull(homeAutoToken)) {
            throw new BusinessException("用户token信息为空");
        }
//        familyService.c1heckoutFamily(homeAutoToken.getUserId(), familyId);

        // 1. 获取室内环境参数
        //// 1.1 获取全参数传感器
        log.info("获取全参数传感器信息, 家庭ID为:{}", familyId);
        Map<String, Object> environmentMap = new LinkedHashMap<>();
        FamilyDeviceDO allParamSensor = familyDeviceService.getSensorDevice(familyId, CategoryEnum.ALL_PARAM_SENSOR);

        // 如果传感器不为空, 则进入分支
        if (!Objects.isNull(allParamSensor)) {
            String deviceId = allParamSensor.getId();
            log.info("全参数传感器设备ID为:{}", deviceId);

            // 获取设备的属性信息
            List<ProductAttributeDO> deviceAttributes = familyDeviceService.getDeviceAttributes(deviceId);
            List<String> attributeList = deviceAttributes.stream().map(ProductAttributeDO::getCode).collect(Collectors.toList());

            // 如果设备的属性不为空, 则进入
            if (!CollectionUtils.isEmpty(attributeList)) {
                log.info("获取传感器属性成功, 属性列表:{}", attributeList);
                for (String attribute : attributeList) {
                    log.info("即将获取设备属性, 设备ID为:{}, 属性Code为:{}", deviceId, attribute);
                    Object deviceStatus = familyDeviceService.getDeviceStatus(deviceId, attribute);
                    if (!Objects.isNull(deviceStatus)) {
                        log.info("属性获取成功, 属性值为:{}, 即将进入精度处理", deviceStatus);
                        HomeAutoProduct deviceProduct = familyDeviceService.getDeviceProduct(allParamSensor.getSn(), allParamSensor.getFamilyId());
                        deviceStatus = familyDeviceService.handleParamValue(deviceProduct.getCode(), attribute, deviceStatus);
                        log.info("精度处理完成, 属性值为:{}", deviceStatus);
                    } else {
                        log.info("属性值为空");
                    }
                    environmentMap.put(attribute, deviceStatus);
                }
            } else {
                log.info("传感器属性列表为空, 设备ID为:{}", deviceId);
            }
        } else {
            log.info("全参数传感器不存在, 家庭ID为:{}", familyId);
        }

        //// 1.2 获取甲醛传感器
        log.info("获取甲醛传感器信息, 家庭ID为:{}", familyId);
        FamilyDeviceDO hchoSensor = familyDeviceService.getSensorDevice(familyId, CategoryEnum.HCHO_SENSOR);
        if (!Objects.isNull(hchoSensor)) {
            String deviceId = hchoSensor.getId();
            log.info("获取甲醛传感器成功, 传感器设备ID为:{}, 即将获取甲醛数值", deviceId);
            Object hchoValue = familyDeviceService.getDeviceStatus(deviceId, "formaldehyde");
            if (!Objects.isNull(hchoValue)) {
                log.info("甲醛数值获取成功, 属性值为:{}, 即将进入精度处理", hchoValue);
                HomeAutoProduct deviceProduct = familyDeviceService.getDeviceProduct(hchoSensor.getSn(), familyId);
                hchoValue = familyDeviceService.handleParamValue(deviceProduct.getCode(), "formaldehyde", hchoValue);
                log.info("精度处理完成, 属性值为:{}", hchoValue);
            } else {
                log.info("甲醛数值为空, 设备ID为:{}", deviceId);
            }
            environmentMap.put("formaldehyde", hchoValue);
        } else {
            log.info("甲醛传感器不存在, 家庭ID为:{}", familyId);
        }

        //// 获取pm2.5
        log.info("获取pm2.5传感器信息, 家庭ID为:{}", familyId);
        FamilyDeviceDO pm25Sensor = familyDeviceService.getSensorDevice(familyId, CategoryEnum.PM25_SENSOR);
        if (!Objects.isNull(pm25Sensor)) {
            String deviceId = pm25Sensor.getId();
            log.info("获取pm2.5传感器成功, 传感器设备ID为:{}", deviceId);
            Object pm25Value = familyDeviceService.getDeviceStatus(deviceId, "pm25");
            if (!Objects.isNull(pm25Value)) {
                log.info("pm2.5数值获取成功, 属性值为:{}, 即将进入精度处理", pm25Value);
                HomeAutoProduct deviceProduct = familyDeviceService.getDeviceProduct(pm25Sensor.getSn(), familyId);
                pm25Value = familyDeviceService.handleParamValue(deviceProduct.getCode(), "pm25", pm25Value);
                log.info("精度处理完成, 属性值为:{}", pm25Value);
            } else {
                log.info("pm2.5数值为空, 设备ID为:{}", deviceId);
            }
            environmentMap.replace("pm25", pm25Value);
        } else {
            log.info("pm2.5传感器不存在, 家庭id为:{}", familyId);
        }

        EnvironmentVO environmentVO = new EnvironmentVO();
        environmentVO.setPm25(Objects.toString(environmentMap.get("pm25")));
        environmentVO.setHcho(Objects.toString(environmentMap.get("formaldehyde")));
        environmentVO.setTemperature(Objects.toString(environmentMap.get("temperature")));
        environmentVO.setHumidity(Objects.toString(environmentMap.get("humidity")));
        environmentVO.setCo2(Objects.toString(environmentMap.get("co2")));

        log.info("环境参数获取完毕, 开始获取常用场景");

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

        log.info("常用场景获取完毕, 开始获取房间设备");

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
        List<FamilyRoomDO> roomList = familyRoomService.getRoomExcludeWhole(familyId);
        for (FamilyRoomDO familyRoomDO : roomList) {
            // 遍历房间
            String position = familyRoomService.getById(familyRoomDO.getId()).getName();
            List<FamilyDeviceDO> deviceList = familyDeviceService.getDeviceListByRoomId(familyRoomDO.getId());
            List<DeviceVO> deviceVOList = CollectionUtil.list(true);
            for (FamilyDeviceDO familyDeviceDO : deviceList) {
                DeviceBO deviceBO = familyDeviceService.getDeviceById(familyDeviceDO.getId());
                String productCode = familyDeviceService.getDeviceProduct(familyDeviceDO.getSn(), familyDeviceDO.getFamilyId()).getCode();
                DeviceVO deviceVO = new DeviceVO();
                deviceVO.setDeviceId(familyDeviceDO.getId());
                deviceVO.setDeviceName(familyDeviceDO.getName());
                deviceVO.setPosition(familyRoomService.getById(familyDeviceDO.getRoomId()).getName());
                deviceVO.setDeviceIcon(familyDeviceService.getDeviceIconById(familyDeviceDO.getId()));
                deviceVO.setProductCode(productCode);
                deviceVO.setFlag(Objects.equals(deviceBO.getRoomType(), RoomTypeEnum.LIVINGROOM) ? 1 : 0);
                deviceVOList.add(deviceVO);
            }
            NonSmartRoomDeviceVO nonSmartRoomDeviceVO = new NonSmartRoomDeviceVO();
            nonSmartRoomDeviceVO.setRoomName(position);
            nonSmartRoomDeviceVO.setDevices(deviceVOList);
            roomDeviceVOList.add(nonSmartRoomDeviceVO);
        }

        log.info("房间设备获取完成, 开始获取暖通设备");

        //// 获取家庭的暖通设备
        FamilyDeviceDO familyHvacDevice = familyDeviceService.getFamilyHvacDevice(familyId);
        EnergyModeDTO energyModeValue = energyModeService.getEnergyModeValue(familyId);
        HvacDeviceBO hvacDeviceBO = new HvacDeviceBO();
        hvacDeviceBO.setDeviceId(familyHvacDevice.getId());
        hvacDeviceBO.setMode(energyModeValue.getValue());

        return returnSuccess(new IndexOfNonSmartVO(hvacDeviceBO, environmentVO, sceneVOList, roomDeviceVOList));
    }

}
