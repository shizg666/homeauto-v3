package com.landleaf.homeauto.center.weather.service;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.weather.model.AirQualityEnum;
import com.landleaf.homeauto.center.weather.model.bo.WeatherBO;
import com.landleaf.homeauto.center.weather.model.vo.WeatherForAppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
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

    private File tempDir;

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
        String cityName = cityCodeMap.get(cityCode).toString();
        return getWeatherByCityName(cityName);
    }

    /**
     * 通过城市名称获取天气
     *
     * @param cityName 城市名称
     * @return 天气信息
     */
    private WeatherBO getWeatherByCityName(String cityName) {
        WeatherBO weatherBO = null;
        if (cityWeatherMap.containsKey(cityName)) {
            weatherBO = cityWeatherMap.get(cityName);
        } else {
            File cityFile = new File(tempDir.getPath() + File.separator + cityName);
            if (cityFile.exists()) {
                String cityWeatherString = FileUtil.readString(cityFile, StandardCharsets.UTF_8);
                weatherBO = JSON.parseObject(cityWeatherString, WeatherBO.class);
            }
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

    @Autowired
    public void setTempDir(File tempDir) {
        this.tempDir = tempDir;
    }
}
