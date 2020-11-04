package com.landleaf.homeauto.center.weather.configuration;

import com.landleaf.homeauto.center.weather.model.bo.WeatherBO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
@Configuration
public class WeatherConfiguration {

    @Bean("cityCode")
    public Map<Object, Object> cityCodeMap() {
        return new ConcurrentHashMap<>(128);
    }

    @Bean("cityWeather")
    public Map<String, WeatherBO> weatherMap() {
        return new ConcurrentHashMap<>(128);
    }

}
