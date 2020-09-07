package com.landleaf.homeauto.center.device.controller.app.smart;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.Pm25Enum;
import com.landleaf.homeauto.center.device.model.bo.*;
import com.landleaf.homeauto.center.device.model.vo.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.IndexOfSmartVO;
import com.landleaf.homeauto.center.device.model.vo.WeatherVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.remote.WeatherRemote;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/8/19
 */
@Slf4j
@RestController
@RequestMapping("/app/smart/family")
@Api(tags = "户式化APP家庭接口")
public class FamilyController extends BaseController {

    @Autowired
    private IFamilySceneService familySceneService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilyUserService familyUserService;

    @Autowired
    private WeatherRemote weatherRemote;

    @GetMapping("/list")
    @ApiOperation("获取家庭列表")
    public Response<FamilyVO> getFamily(String userId) {
        List<FamilyBO> familyBOList = familyService.getFamilyListByUserId(userId);
        FamilyVO familyVO = new FamilyVO();
        for (FamilyBO familyBO : familyBOList) {
            SimpleFamilyBO family = new SimpleFamilyBO();
            family.setFamilyId(familyBO.getFamilyId());
            family.setFamilyName(familyBO.getFamilyName());
            if (Objects.equals(familyBO.getLastChecked(), 1)) {
                // 如果是最后一次选择的,就显示当前家庭
                // 这里做深拷贝,如果直接把family对象设值,会引起序列化问题
                SimpleFamilyBO simpleFamilyBO = new SimpleFamilyBO();
                simpleFamilyBO.setFamilyId(family.getFamilyId());
                simpleFamilyBO.setFamilyName(family.getFamilyName());
                familyVO.setCurrent(simpleFamilyBO);
            }
            if (Objects.nonNull(familyVO.getList())) {
                // 如果家庭列表不为空,就添加到
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
    public Response<IndexOfSmartVO> getFamilyCommonScenesAndDevices(@PathVariable String familyId) {
        // 把上一次的家庭切换为当前家庭
        String userId = TokenContext.getToken().getUserId();
        familyUserService.checkoutFamily(userId, familyId);

        // 常用场景
        List<FamilySceneBO> allSceneBOList = familySceneService.getAllSceneList(familyId);
        List<FamilySceneBO> commonSceneBOList = familySceneService.getCommonSceneList(familyId);
        allSceneBOList.removeIf(familySceneBO -> !commonSceneBOList.contains(familySceneBO));
        List<SceneVO> commonSceneVOList = new LinkedList<>();
        for (FamilySceneBO commonSceneBO : allSceneBOList) {
            SceneVO commonSceneVO = new SceneVO();
            commonSceneVO.setSceneId(commonSceneBO.getSceneId());
            commonSceneVO.setSceneName(commonSceneBO.getSceneName());
            commonSceneVO.setSceneIcon(commonSceneBO.getSceneIcon());
            commonSceneVO.setIndex(commonSceneBO.getIndex());
            commonSceneVOList.add(commonSceneVO);
        }

        // 常用设备
        List<FamilyDeviceWithPositionBO> allDeviceBOList = familyDeviceService.getAllDevices(familyId);
        List<FamilyDeviceWithPositionBO> commonDeviceBOList = familyDeviceService.getCommonDevices(familyId);
        allDeviceBOList.removeIf(commonDeviceBO -> !commonDeviceBOList.contains(commonDeviceBO));
        List<DeviceVO> commonDeviceVOList = new LinkedList<>();
        for (FamilyDeviceWithPositionBO commonDeviceBO : allDeviceBOList) {
            DeviceVO deviceVO = new DeviceVO();
            deviceVO.setDeviceId(commonDeviceBO.getDeviceId());
            deviceVO.setDeviceName(commonDeviceBO.getDeviceName());
            deviceVO.setPosition(getPosition(commonDeviceBO.getFloorName(), commonDeviceBO.getRoomName()));
            deviceVO.setDeviceIcon(commonDeviceBO.getDeviceIcon());
            deviceVO.setIndex(commonDeviceBO.getIndex());
            commonDeviceVOList.add(deviceVO);
        }

        // 天气
        try {
            String weatherCode = familyService.getWeatherCodeByFamilyId(familyId);
            WeatherBO weatherBO = weatherRemote.getWeatherByCode(weatherCode).getResult();
            WeatherVO weatherVO = new WeatherVO();
            if (!Objects.isNull(weatherBO)) {
                weatherVO.setWeatherStatus(weatherBO.getWeatherStatus());
                weatherVO.setTemp(weatherBO.getTemp());
                weatherVO.setMinTemp(weatherBO.getMinTemp());
                weatherVO.setMaxTemp(weatherBO.getMaxTemp());
                weatherVO.setPicUrl(weatherBO.getPicUrl());
                weatherVO.setAirQuality(Pm25Enum.getAirQualityByPm25(Integer.parseInt(weatherBO.getPm25())));
            }
            return returnSuccess(new IndexOfSmartVO(weatherVO, commonSceneVOList, commonDeviceVOList));
        } catch (Exception ex) {
            throw new BusinessException("获取天气信息异常");
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

}
