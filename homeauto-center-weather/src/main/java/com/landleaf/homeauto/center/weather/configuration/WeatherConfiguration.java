package com.landleaf.homeauto.center.weather.configuration;

import com.landleaf.homeauto.center.weather.model.bo.WeatherBO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
@Configuration
public class WeatherConfiguration {

    private RedisUtils redisUtils;

    private static final String TABLE_CITY_CODE_REDIS_KEY = "TABLE_CITY_CODE";

    @Bean
    public File tempDir() {
        File file = new File("/Users/yujiumin/Desktop/homeauto/center/weather");
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) {
                return null;
            }
        }
        return file;
    }

    @Bean("cityCode")
    public Map<Object, Object> cityCodeMap() {
        Map<Object, Object> cityCodeMapFromRedis = redisUtils.hmget(TABLE_CITY_CODE_REDIS_KEY);
        Map<Object, Object> cityCodeMap = new LinkedHashMap<>();
        for (Object cityName : cityCodeMapFromRedis.keySet()) {
            String cityCode = cityCodeMapFromRedis.get(cityName).toString();
            cityCodeMap.put(cityCode, cityName);
        }
        return cityCodeMap;
    }

    @Bean("cityWeather")
    public Map<String, WeatherBO> weatherMap() {
        return new ConcurrentHashMap<>(128);
    }

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }
}
