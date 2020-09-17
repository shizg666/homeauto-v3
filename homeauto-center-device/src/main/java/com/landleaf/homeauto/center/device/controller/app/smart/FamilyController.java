package com.landleaf.homeauto.center.device.controller.app.smart;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.Pm25Enum;
import com.landleaf.homeauto.center.device.model.bo.*;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserCheckout;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.vo.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.IndexOfSmartVO;
import com.landleaf.homeauto.center.device.model.vo.WeatherVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.remote.WeatherRemote;
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
import org.springframework.web.bind.annotation.*;

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
    private IFamilyUserCheckoutService familyUserCheckoutService;

    @Autowired
    private WeatherRemote weatherRemote;

    /**
     * 获取用户家庭列表
     *
     * @param userId 用户id
     * @return 家庭列表
     */
    @GetMapping("/list")
    @ApiOperation("获取家庭列表")
    public Response<FamilyVO> getFamily(@RequestParam String userId) {
        List<FamilyBO> familyBOList = familyService.getFamilyListByUserId(userId);

        FamilyVO familyVO = new FamilyVO();
        List<SimpleFamilyBO> simpleFamilyBOList = Lists.newArrayList();
        for (FamilyBO familyBO : familyBOList) {
            SimpleFamilyBO simpleFamilyBO = new SimpleFamilyBO();
            simpleFamilyBO.setFamilyId(familyBO.getFamilyId());
            simpleFamilyBO.setFamilyName(familyBO.getFamilyName());
            simpleFamilyBOList.add(simpleFamilyBO);
        }

        FamilyUserCheckout familyUserCheckout = familyUserCheckoutService.getFamilyUserCheckout(userId);
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
     * @param familyId 家庭id
     * @return 家庭首页信息
     */
    @GetMapping("/checkout/{familyId}")
    @ApiOperation("切换家庭")
    public Response<IndexOfSmartVO> getFamilyCommonScenesAndDevices(@PathVariable String familyId) {
        log.info("进入{}接口, 入参为[{}]", "/app/smart/family/checkout/{familyId}", familyId);
        // 把上一次的家庭切换为当前家庭
        HomeAutoToken homeAutoToken = TokenContext.getToken();
        if (Objects.isNull(homeAutoToken)) {
            throw new BusinessException("用户token信息为空");
        }
        String userId = homeAutoToken.getUserId();
        log.info("用户id为: {}", userId);
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
            deviceVO.setPosition(String.format("%s-%s", commonDeviceBO.getFloorName(), commonDeviceBO.getRoomName()));
            deviceVO.setDeviceIcon(commonDeviceBO.getDeviceIcon());
            deviceVO.setIndex(commonDeviceBO.getIndex());
            commonDeviceVOList.add(deviceVO);
        }

        // 天气
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
    }

}
