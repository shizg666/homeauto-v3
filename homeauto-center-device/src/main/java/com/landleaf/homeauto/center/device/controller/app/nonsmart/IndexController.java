package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.device.model.HchoEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.vo.EnvironmentVO;
import com.landleaf.homeauto.center.device.model.vo.IndexForNonSmartVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 首页控制器
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@RestController("IndexForNonSmartController")
@RequestMapping("app/non-smart/index")
@Api(value = "首页控制器", tags = "自由方舟APP首页接口")
@AllArgsConstructor
public class IndexController extends BaseController {

    private IFamilySceneService familySceneService;

    private IFamilyDeviceService familyDeviceService;

    private IFamilyCommonSceneService familyCommonSceneService;

    private IFamilyCommonDeviceService familyCommonDeviceService;

    private IFamilyRoomService familyRoomService;

    @GetMapping
    @ApiOperation("首页接口")
    public Response<IndexForNonSmartVO> getFamilyCommonScenesAndDevices(@RequestParam String familyId) {
        // 1. 获取室内环境参数
        //// 1.1 获取甲醛
        DeviceSensorBO hchoSensor = familyDeviceService.getHchoSensor(familyId);
        String hcho = HchoEnum.getAqi(Float.parseFloat(familyDeviceService.getDeviceStatus(hchoSensor, "formaldehyde").toString()));

        //// 1.2 获取pm2.5
        DeviceSensorBO pm25Sensor = familyDeviceService.getPm25Sensor(familyId);
        String pm25Value = familyDeviceService.getDeviceStatus(pm25Sensor, "pm25").toString();

        //// 1.3 获取全参传感器
        DeviceSensorBO sensor = familyDeviceService.getAllParamSensor(familyId);
        if (Objects.isNull(sensor)) {
            // 如果家里没有全参传感器则获取多餐传感器
            sensor = familyDeviceService.getMultiParamSensor(familyId);
        }
        String temp = familyDeviceService.getDeviceStatus(sensor, "temperature").toString();
        String humidity = familyDeviceService.getDeviceStatus(sensor, "humidity").toString();
        String co2 = familyDeviceService.getDeviceStatus(sensor, "co2").toString();
        EnvironmentVO environmentVO = new EnvironmentVO(temp, humidity, pm25Value, hcho, co2);

        // 2. 获取常用场景
        List<String> commonSceneIdList = familyCommonSceneService.getCommonSceneIdListByFamilyId(familyId);
        List<FamilySceneDO> sceneDOList = CollectionUtil.list(true, familySceneService.listByIds(commonSceneIdList));
        List<SceneVO> sceneVOList = CollectionUtil.list(true);
        for (FamilySceneDO familySceneDO : sceneDOList) {
            SceneVO sceneVO = new SceneVO();
            sceneVO.setSceneId(familySceneDO.getId());
            sceneVO.setSceneName(familySceneDO.getName());
            sceneVO.setSceneIcon(familySceneDO.getIcon());
            sceneVOList.add(sceneVO);
        }

        // 3. 获取常用设备
        List<String> commonDeviceIdList = familyCommonDeviceService.getCommonDeviceIdListByFamilyId(familyId);
        List<FamilyDeviceDO> deviceDOList = familyDeviceService.getDeviceListByIds(commonDeviceIdList);
        List<DeviceVO> commonDeviceVOList = CollectionUtil.list(true);
        for (FamilyDeviceDO familyDeviceDO : deviceDOList) {
            commonDeviceVOList.add(toDeviceVO(familyDeviceDO));
        }

        // 4. 获取各个房间的设备
        List<FamilyRoomDO> roomList = familyRoomService.getRoom(familyId);
        Map<String, List<DeviceVO>> roomDeviceMap = CollectionUtil.newHashMap();
        for (FamilyRoomDO familyRoomDO : roomList) {
            String position = familyRoomService.getPosition(familyRoomDO.getId());
            List<FamilyDeviceDO> deviceList = familyDeviceService.getDeviceListByRoomId(familyRoomDO.getId());
            List<DeviceVO> deviceVOList = CollectionUtil.list(true);
            for (FamilyDeviceDO familyDeviceDO : deviceList) {
                deviceVOList.add(toDeviceVO(familyDeviceDO));
            }
            roomDeviceMap.put(position, deviceVOList);
        }
        return returnSuccess(new IndexForNonSmartVO(environmentVO, sceneVOList, commonDeviceVOList, roomDeviceMap));
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
        deviceVO.setPosition(familyDeviceService.getDevicePositionById(familyDeviceDO.getId()));
        deviceVO.setDeviceIcon(familyDeviceService.getDeviceIconById(familyDeviceDO.getId()));
        deviceVO.setDeviceSwitch(Objects.equals(familyDeviceService.getDeviceStatus(familyDeviceDO.getId(), "switch"), "on") ? 1 : 0);
        return deviceVO;
    }
}
