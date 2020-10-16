package com.landleaf.homeauto.center.device.controller.app.smart;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.Pm25Enum;
import com.landleaf.homeauto.center.device.model.bo.*;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyCheckoutVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyWeatherVO;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private IFamilyUserCheckoutService familyUserCheckoutService;

    @Autowired
    private IFamilyCommonSceneService familyCommonSceneService;

    @Autowired
    private IFamilyCommonDeviceService familyCommonDeviceService;

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
        // 临时修改没有
        if (CollectionUtils.isEmpty(familyBOList)) {
            familyVO.setCurrent(null);
            familyVO.setList(simpleFamilyBOList);
            return returnSuccess(familyVO);
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
    public Response<FamilyCheckoutVO> getFamilyCommonScenesAndDevices(@PathVariable String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        if (Objects.isNull(token)) {
            throw new BusinessException("TOKEN不可为空");
        }
        String userId = token.getUserId();
        log.info("更新用户最后一次切换的家庭 -> 开始");
        familyUserCheckoutService.saveOrUpdate(userId, familyId);
        log.info("更新用户最后一次切换的家庭 -> 结束");

        FamilyCheckoutVO familyCheckoutVO = new FamilyCheckoutVO();

        // 1. 获取天气信息
        FamilyWeatherVO familyWeatherVO = new FamilyWeatherVO();
        try {
            log.info("获取家庭所在城市天气信息 -> 开始");
            HomeAutoFamilyBO homeAutoFamilyBO = familyService.getOne(familyId);
            String weatherCode = homeAutoFamilyBO.getWeatherCode();
            log.info("获取到家庭所在城市编码, 家庭ID: {}, 城市编码: {}", familyId, weatherCode);
            Response<WeatherBO> weatherResponse = weatherRemote.getWeatherByCode(weatherCode);
            WeatherBO weatherBO = weatherResponse.getResult();
            if (!Objects.isNull(weatherBO)) {
                log.info("获取家庭所在城市天气信息 -> 成功");
                familyWeatherVO.setWeatherStatus(weatherBO.getWeatherStatus());
                familyWeatherVO.setTemp(weatherBO.getTemp());
                familyWeatherVO.setMinTemp(weatherBO.getMinTemp());
                familyWeatherVO.setMaxTemp(weatherBO.getMaxTemp());
                familyWeatherVO.setPicUrl(weatherBO.getPicUrl());
                familyWeatherVO.setAirQuality(Pm25Enum.getAirQualityByPm25(Integer.parseInt(weatherBO.getPm25())));
            } else {
                log.info("暂未查询到该家庭所在城市天气信息");
                log.info("获取家庭所在城市天气信息 -> 失败");
            }
        } catch (Exception ex) {
            log.error("获取家庭所在城市天气信息 -> 失败");
            log.error(ex.getMessage());
        } finally {
            familyCheckoutVO.setWeather(familyWeatherVO);
        }
        log.info("获取家庭所在城市的天气信息 -> 结束");

        // 2. 获取常用场景信息
        log.info("获取家庭常用场景列表 -> 开始");
        List<FamilySceneDO> familySceneDOList = familySceneService.listByFamilyId(familyId);
        List<FamilyCommonSceneDO> familyCommonSceneDOList = familyCommonSceneService.listByFamilyId(familyId);
        List<com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO> familySceneBOList = familySceneService.getFamilySceneWithIndex(familySceneDOList, familyCommonSceneDOList, true);
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO familySceneBO : familySceneBOList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familySceneBO.getSceneId());
            familySceneVO.setSceneName(familySceneBO.getSceneName());
            familySceneVO.setSceneIcon(familySceneBO.getSceneIcon());
            familySceneVO.setIndex(familySceneBO.getSceneIndex());
            familySceneVOList.add(familySceneVO);
        }
        familyCheckoutVO.setScenes(familySceneVOList);
        log.info("获取家庭常用场景列表 -> 结束");

        // 3. 获取常用设备信息
        log.info("获取家庭常用设备列表 -> 开始");
        List<FamilyDeviceDO> familyDeviceDOList = familyDeviceService.listReadOnlyDeviceByFamilyId(familyId);
        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = familyCommonDeviceService.listByFamilyId(familyId);
        List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO> familyDeviceBOList = familyDeviceService.getFamilyDeviceWithIndex(familyDeviceDOList, familyCommonDeviceDOList, true);
        List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
        for (FamilyDeviceBO familyDeviceBO : familyDeviceBOList) {
            FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
            familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
            familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
            familyDeviceVO.setDeviceIcon(Optional.ofNullable(familyDeviceBO.getProductIcon()).orElse(""));
            familyDeviceVO.setProductCode(familyDeviceBO.getProductCode());
            familyDeviceVO.setCategoryCode(familyDeviceBO.getCategoryCode());
            familyDeviceVO.setPosition(String.format("%sF-%s", familyDeviceBO.getFloorNum(), familyDeviceBO.getRoomName()));
            familyDeviceVO.setIndex(familyDeviceBO.getDeviceIndex());
            familyDeviceVOList.add(familyDeviceVO);
        }
        familyCheckoutVO.setDevices(familyDeviceVOList);
        log.info("获取家庭常用设备列表 -> 结束");

        return returnSuccess(familyCheckoutVO);
    }

}
