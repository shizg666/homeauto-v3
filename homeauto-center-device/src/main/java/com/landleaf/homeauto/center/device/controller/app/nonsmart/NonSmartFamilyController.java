package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.HchoEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyBO;
import com.landleaf.homeauto.center.device.model.bo.SimpleFamilyBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.vo.EnvironmentVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.IndexOfNonSmartVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.Response;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        //// 1.1 获取甲醛
        DeviceSensorBO hchoSensor = familyDeviceService.getHchoSensor(familyId);
        String hcho = null;
        if (!Objects.isNull(hchoSensor)) {
            hcho = HchoEnum.getAqi(Float.parseFloat(familyDeviceService.getDeviceStatus(hchoSensor, "formaldehyde").toString()));
        }

        //// 1.2 获取pm2.5
        DeviceSensorBO pm25Sensor = familyDeviceService.getPm25Sensor(familyId);
        String pm25Value = null;
        if (!Objects.isNull(pm25Sensor)) {
            pm25Value = familyDeviceService.getDeviceStatus(pm25Sensor, "pm25").toString();
        }

        //// 1.3 获取全/多参数传感器
        DeviceSensorBO sensor = familyDeviceService.getParamSensor(familyId);
        String temp = null;
        String humidity = null;
        String co2 = null;
        if (!Objects.isNull(sensor)) {
            temp = familyDeviceService.getDeviceStatus(sensor, "temperature").toString();
            humidity = familyDeviceService.getDeviceStatus(sensor, "humidity").toString();
            co2 = familyDeviceService.getDeviceStatus(sensor, "co2").toString();
        }
        EnvironmentVO environmentVO = new EnvironmentVO(temp, humidity, pm25Value, hcho, co2);

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

        // 3. 获取常用设备
        List<String> commonDeviceIdList = familyCommonDeviceService.getCommonDeviceIdListByFamilyId(familyId);
        List<DeviceVO> commonDeviceVOList = CollectionUtil.list(true);
        if (!CollectionUtil.isEmpty(commonDeviceIdList)) {
            List<FamilyDeviceDO> deviceDOList = CollectionUtil.list(true, familyDeviceService.listByIds(commonDeviceIdList));
            for (FamilyDeviceDO familyDeviceDO : deviceDOList) {
                commonDeviceVOList.add(toDeviceVO(familyDeviceDO));
            }
        }

        // 4. 获取各个房间的设备
        List<FamilyRoomDO> roomList = familyRoomService.getRoom(familyId);
        Map<String, List<DeviceVO>> roomDeviceMap = CollectionUtil.newHashMap();
        for (FamilyRoomDO familyRoomDO : roomList) {
            String position = familyRoomService.getById(familyRoomDO.getId()).getName();
            List<FamilyDeviceDO> deviceList = familyDeviceService.getDeviceListByRoomId(familyRoomDO.getId());
            List<DeviceVO> deviceVOList = CollectionUtil.list(true);
            for (FamilyDeviceDO familyDeviceDO : deviceList) {
                deviceVOList.add(toDeviceVO(familyDeviceDO));
            }
            roomDeviceMap.put(position, deviceVOList);
        }
        return returnSuccess(new IndexOfNonSmartVO(environmentVO, sceneVOList, commonDeviceVOList, roomDeviceMap));
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
        return deviceVO;
    }

}
