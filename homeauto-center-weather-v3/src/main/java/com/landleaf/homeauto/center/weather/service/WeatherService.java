package com.landleaf.homeauto.center.weather.service;

import com.landleaf.homeauto.center.weather.model.AirQualityEnum;
import com.landleaf.homeauto.center.weather.model.bo.WeatherBO;
import com.landleaf.homeauto.center.weather.model.vo.WeatherForAppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
@Service
public class WeatherService {

    private Map<String, WeatherBO> cityWeatherMap;

    private Map<Object, Object> cityCodeMap;

    /**
     * 通过城市名称获取天气数据
     *
     * @param cityName 城市名称
     * @return 天气数据
     */
    public WeatherForAppVO getWeatherByCityNameForApp(String cityName) {
        WeatherBO weatherBO = getWeatherByCityName(cityName);
        WeatherForAppVO weatherForAppVO = new WeatherForAppVO();
        weatherForAppVO.setWeatherStatus(weatherBO.getWeatherStatus());
        weatherForAppVO.setTemp(weatherBO.getTemp());
        weatherForAppVO.setMinTemp(weatherBO.getMinTemp());
        weatherForAppVO.setMaxTemp(weatherBO.getMaxTemp());
        weatherForAppVO.setPicUrl(weatherBO.getPicUrl());
        weatherForAppVO.setAirQuality(AirQualityEnum.getAirQualityByPm25(Integer.parseInt(weatherBO.getPm25())).getLevel());
        return weatherForAppVO;
    }

    /**
     * 通过城市编码获取天气信息
     *
     * @param cityCode 城市编码
     * @return 天气信息
     */
    public WeatherBO getWeatherBoByCityCode(String cityCode) {
        if (!cityCodeMap.containsKey(cityCode)) {
            throw new RuntimeException("暂未查询到该城市信息");
        }
        String cityName = cityCodeMap.get(cityCode).toString();
        return getWeatherByCityName(cityName);
    }

    /**
     * 通过城市名称获取天气
     *
     * @param cityName 城市名称
     * @return 天气信息
     */
    public WeatherBO getWeatherByCityName(String cityName) {
        WeatherBO weatherBO = null;
        if (cityWeatherMap.containsKey(cityName)) {
            weatherBO = cityWeatherMap.get(cityName);
        } else {
            // TODO: 去Redis中查
        }
        if (Objects.isNull(weatherBO)) {
            throw new RuntimeException("暂未查询到该城市信息");
        }
        return weatherBO;
    }

    @Autowired
    @Qualifier("cityWeather")
    public void setCityWeatherMap(Map<String, WeatherBO> cityWeatherMap) {
        this.cityWeatherMap = cityWeatherMap;
    }

    @Autowired
    @Qualifier("cityCode")
    public void setCityCodeMap(Map<Object, Object> cityCodeMap) {
        this.cityCodeMap = cityCodeMap;
    }


}
