package com.landleaf.homeauto.center.device.controller.app.smart;

import com.landleaf.homeauto.center.device.model.Pm25Enum;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyCheckoutVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyWeatherVO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户切换家庭接口(暂时不开放)
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Slf4j
@RestController
@RequestMapping("family-checkout")
@Api(tags = "户式化APP家庭切换控制器")
public class FamilyCheckoutController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilySceneService familySceneService;

    @Autowired
    private IFamilyCommonSceneService familyCommonSceneService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IFamilyCommonDeviceService familyCommonDeviceService;

    @Autowired
    private IFamilyUserCheckoutService familyUserCheckoutService;

    @Autowired
    private WeatherRemote weatherRemote;

    @PostMapping("/{familyId}")
    @ApiOperation("用户在首页切换家庭时, 调用此接口")
    public Response<FamilyCheckoutVO> checkout(@PathVariable String familyId) {
        HomeAutoToken token = TokenContext.getToken();
        if (Objects.isNull(token)) {
            throw new BusinessException("用户信息不可为空");
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
        List<FamilySceneBO> familySceneBOList = familySceneService.getFamilySceneWithIndex(familySceneDOList, familyCommonSceneDOList, true);
        List<FamilySceneVO> familySceneVOList = new LinkedList<>();
        for (FamilySceneBO familySceneBO : familySceneBOList) {
            FamilySceneVO familySceneVO = new FamilySceneVO();
            familySceneVO.setSceneId(familySceneBO.getSceneId());
            familySceneVO.setSceneName(familySceneBO.getSceneName());
            familySceneVO.setSceneIcon(familySceneBO.getSceneIcon());
            familySceneVO.setSceneIndex(familySceneBO.getSceneIndex());
            familySceneVOList.add(familySceneVO);
        }
        familyCheckoutVO.setCommonSceneList(familySceneVOList);
        log.info("获取家庭常用场景列表 -> 结束");

        // 3. 获取常用设备信息
        log.info("获取家庭常用设备列表 -> 开始");
        List<FamilyDeviceDO> familyDeviceDOList = familyDeviceService.listReadOnlyDeviceByFamilyId(familyId);
        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = familyCommonDeviceService.listByFamilyId(familyId);
        List<FamilyDeviceBO> familyDeviceBOList = familyDeviceService.getFamilyDeviceWithIndex(familyDeviceDOList, familyCommonDeviceDOList, true);
        List<FamilyDeviceVO> familyDeviceVOList = new LinkedList<>();
        for (FamilyDeviceBO familyDeviceBO : familyDeviceBOList) {
            FamilyDeviceVO familyDeviceVO = new FamilyDeviceVO();
            familyDeviceVO.setDeviceId(familyDeviceBO.getDeviceId());
            familyDeviceVO.setDeviceName(familyDeviceBO.getDeviceName());
            familyDeviceVO.setDeviceImage(Optional.ofNullable(familyDeviceBO.getProductImage()).orElse(""));
            familyDeviceVO.setDeviceIndex(familyDeviceBO.getDeviceIndex());
            familyDeviceVOList.add(familyDeviceVO);
        }
        familyCheckoutVO.setCommonDeviceList(familyDeviceVOList);
        log.info("获取家庭常用设备列表 -> 结束");

        return returnSuccess(familyCheckoutVO);

    }

}
