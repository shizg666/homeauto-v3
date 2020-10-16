package com.landleaf.homeauto.center.device.service.common.impl;

import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.remote.WeatherRemote;
import com.landleaf.homeauto.center.device.service.common.FamilyWeatherService;
import com.landleaf.homeauto.common.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/10/16
 */
@Slf4j
@Service
public class FamilyWeatherServiceImpl implements FamilyWeatherService {

    @Autowired
    private WeatherRemote weatherRemote;

    @Override
    public WeatherBO getWeatherByWeatherCode(String weatherCode) {
        log.info("获取城市天气信息, 城市天气编码: {}", weatherCode);
        try {
            Response<WeatherBO> response = weatherRemote.getWeatherByCode(weatherCode);
            WeatherBO weatherBO = response.getResult();
            if (!Objects.isNull(weatherBO)) {
                log.info("获取家庭所在城市天气信息 -> 成功");
                return weatherBO;
            }
            log.info("暂未查询到该家庭所在城市天气信息");
        } catch (Exception ex) {
            log.info("获取家庭所在城市天气信息 -> 失败");
        }
        return null;
    }
}
