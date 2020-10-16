package com.landleaf.homeauto.center.device.service.common;

import com.landleaf.homeauto.center.device.model.bo.WeatherBO;

/**
 * @author Yujiumin
 * @version 2020/10/16
 */
public interface FamilyWeatherService {

    /**
     * 通过weatherCode获取天气信息
     *
     * @param weatherCode 气象局给出的城市天气码
     * @return 天气信息
     */
    WeatherBO getWeatherByWeatherCode(String weatherCode);

}
