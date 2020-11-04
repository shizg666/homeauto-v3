package com.landleaf.homeauto.center.weather.schedule;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.weather.model.bo.WeatherBO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 定时更新任务
 *
 * @author Yujiumin
 * @version 2020/8/18
 */
@Service
@EnableScheduling
public class WeatherTask {

    private static final String TABLE_CITY_WEATHER_REDIS_KEY = "TABLE_CITY_WEATHER";

    private static final String TABLE_CITY_CODE_REDIS_KEY = "TABLE_CITY_CODE";

    private Map<String, WeatherBO> weatherMap;

    private Map<Object, Object> cityCodeMap;

    private RedisUtils redisUtils;

    /**
     * 每隔10分钟从redis拉取一次数据
     */
    @Scheduled(fixedDelay = 600_000, initialDelay = 100)
    public void updateWeather() {
        weatherMap.clear();
        Map<Object, Object> cityWeatherMap = redisUtils.hmget(TABLE_CITY_WEATHER_REDIS_KEY);
        for (Object city : cityWeatherMap.keySet()) {
            String key = city.toString();
            WeatherBO value = JSON.parseObject(cityWeatherMap.get(key).toString(), WeatherBO.class);
            weatherMap.put(key, value);
        }
    }

    /**
     * 每隔1个小时去redis拉取一次城市天气编码
     */
    @Scheduled(fixedDelay = 3600_000, initialDelay = 100)
    public void updateCityCode() {
        Map<Object, Object> cityCodeMapFromRedis = redisUtils.hmget(TABLE_CITY_CODE_REDIS_KEY);
        for (Object cityName : cityCodeMapFromRedis.keySet()) {
            String cityCode = cityCodeMapFromRedis.get(cityName).toString();
            cityCodeMap.put(cityCode, cityName);
        }
    }

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Autowired
    @Qualifier("cityWeather")
    public void setWeatherMap(Map<String, WeatherBO> weatherMap) {
        this.weatherMap = weatherMap;
    }

    @Autowired
    @Qualifier("cityCode")
    public void setCityCodeMap(Map<Object, Object> cityCodeMap) {
        this.cityCodeMap = cityCodeMap;
    }
}
