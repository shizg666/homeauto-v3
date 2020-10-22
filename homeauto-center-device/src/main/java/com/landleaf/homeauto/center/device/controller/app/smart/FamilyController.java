package com.landleaf.homeauto.center.device.controller.app.smart;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.model.Pm25Enum;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.constant.DeviceNatureEnum;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO;
import com.landleaf.homeauto.center.device.model.smart.vo.*;
import com.landleaf.homeauto.center.device.service.common.FamilyWeatherService;
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
    private FamilyWeatherService familyWeatherService;

    /**
     * 获取用户家庭列表
     *
     * @param userId 用户id
     * @return 家庭列表
     */
    @GetMapping("/list")
    @ApiOperation("获取家庭列表")
    public Response<FamilySelectVO> listFamily(@RequestParam String userId) {
        log.info("户式化APP查询用户家庭列表 -> 开始");

        FamilySelectVO familySelectVO = new FamilySelectVO();

        // 查询用户所有绑定的家庭
        log.info("查询用户所有绑定的家庭");
        List<HomeAutoFamilyBO> homeAutoFamilyBOList = familyService.listByUserId(userId, FamilyReviewStatusEnum.REVIEW, FamilyReviewStatusEnum.AUTHORIZATION);
        if (CollectionUtil.isEmpty(homeAutoFamilyBOList)) {
            // 家庭列表为空, 用户没有绑定任何家庭
            log.info("用户未绑定任何家庭, 用户ID: {}", userId);
            familySelectVO.setCurrent(null);
            familySelectVO.setList(CollectionUtil.list(true));
            return returnSuccess(familySelectVO);
        } else {
            // 家庭列表不为空
            log.info("查询到用户所有绑定的家庭");
            List<HomeAutoFamilyVO> homeAutoFamilyVOList = new LinkedList<>();
            for (HomeAutoFamilyBO homeAutoFamilyBO : homeAutoFamilyBOList) {
                log.info("家庭ID: {}, 家庭信息: {}", homeAutoFamilyBO.getFamilyId(), homeAutoFamilyBO);
                HomeAutoFamilyVO homeAutoFamilyVO = new HomeAutoFamilyVO();
                homeAutoFamilyVO.setFamilyId(homeAutoFamilyBO.getFamilyId());
                homeAutoFamilyVO.setFamilyName(homeAutoFamilyBO.getFamilyName());
                homeAutoFamilyVOList.add(homeAutoFamilyVO);
            }
            familySelectVO.setList(homeAutoFamilyVOList);
        }

        // 查询用户上一次切换的家庭
        log.info("查询用户上一次切换的家庭");
        HomeAutoFamilyVO homeAutoFamilyVO = new HomeAutoFamilyVO();
        FamilyUserCheckout familyUserCheckout = familyUserCheckoutService.getByUserId(userId);
        if (Objects.isNull(familyUserCheckout)) {
            // 用户还没有切换过家庭, 用家庭列表的第一个作为默认切换家庭
            log.info("未查询到用户上一次切换的家庭, 默认使用第一个家庭作为展示家庭, 用户ID为: {}", userId);
            HomeAutoFamilyBO homeAutoFamilyBO = homeAutoFamilyBOList.get(0);
            homeAutoFamilyVO.setFamilyId(homeAutoFamilyBO.getFamilyId());
            homeAutoFamilyVO.setFamilyName(homeAutoFamilyBO.getFamilyName());
        } else {
            // 用户已经有切换家庭的记录了
            log.info("查询到用户上一次切换的家庭, 用户ID为: {}, 家庭ID为: {}", userId, familyUserCheckout.getFamilyId());
            HomeAutoFamilyDO homeAutoFamilyDO = familyService.getById(familyUserCheckout.getFamilyId());
            homeAutoFamilyVO.setFamilyId(homeAutoFamilyDO.getId());
            homeAutoFamilyVO.setFamilyName(homeAutoFamilyDO.getName());
        }
        familySelectVO.setCurrent(homeAutoFamilyVO);
        log.info("户式化APP查询用户家庭列表 -> 结束");

        return returnSuccess(familySelectVO);
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
        log.info("户式化APP切换家庭 -> 开始");

        log.info("检查家庭的审核状态, 家庭ID: {}", familyId);
        FamilyReviewStatusEnum familyReviewStatusEnum = familyService.getFamilyReviewStatus(familyId);
        if (Objects.equals(familyReviewStatusEnum, FamilyReviewStatusEnum.AUTHORIZATION)) {
            throw new BusinessException(90001, "当前家庭授权状态更改中");
        }

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

        log.info("获取家庭所在城市天气信息 -> 开始");
        HomeAutoFamilyBO homeAutoFamilyBO = familyService.getOne(familyId);
        String weatherCode = homeAutoFamilyBO.getWeatherCode();
        log.info("获取到家庭所在城市编码, 家庭ID: {}, 城市编码: {}", familyId, weatherCode);
        WeatherBO weatherBO = familyWeatherService.getWeatherByWeatherCode(weatherCode);
        if (!Objects.isNull(weatherBO)) {
            familyWeatherVO.setWeatherStatus(weatherBO.getWeatherStatus());
            familyWeatherVO.setTemp(weatherBO.getTemp());
            familyWeatherVO.setMinTemp(weatherBO.getMinTemp());
            familyWeatherVO.setMaxTemp(weatherBO.getMaxTemp());
            familyWeatherVO.setPicUrl(weatherBO.getPicUrl());
            familyWeatherVO.setAirQuality(Pm25Enum.getAirQualityByPm25(Integer.parseInt(weatherBO.getPm25())));
        }
        familyCheckoutVO.setWeather(familyWeatherVO);
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
        List<FamilyDeviceDO> familyDeviceDOList = familyDeviceService.listDeviceByFamilyIdAndNature(familyId, DeviceNatureEnum.CONTROLLABLE);
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
            familyDeviceVO.setDeviceSwitch(Objects.equals(Objects.toString(familyDeviceService.getDeviceStatus(familyDeviceBO.getDeviceId(), ProductPropertyEnum.SWITCH.code())), "on") ? 1 : 0);
            familyDeviceVO.setPosition(String.format("%sF-%s", familyDeviceBO.getFloorNum(), familyDeviceBO.getRoomName()));
            familyDeviceVO.setIndex(familyDeviceBO.getDeviceIndex());
            familyDeviceVOList.add(familyDeviceVO);
        }
        familyCheckoutVO.setDevices(familyDeviceVOList);
        log.info("获取家庭常用设备列表 -> 结束");
        log.info("户式化APP切换家庭 -> 结束");
        return returnSuccess(familyCheckoutVO);
    }

}
