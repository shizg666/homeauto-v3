package com.landleaf.homeauto.center.weather.schedule;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.weather.model.bo.WeatherBO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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

    private RedisUtils redisUtils;

    private static final String TABLE_CITY_WEATHER_REDIS_KEY = "TABLE_CITY_WEATHER";

    private Map<String, WeatherBO> weatherMap;

    private File tempDir;

    /**
     * 每隔10分钟从redis拉取一次数据
     */
    @Scheduled(fixedDelay = 600_000)
    public void updateWeather() {
        Map<Object, Object> cityWeatherMap = redisUtils.hmget(TABLE_CITY_WEATHER_REDIS_KEY);
        for (Object city : cityWeatherMap.keySet()) {
            // 第一步, 存map
            String key = city.toString();
            WeatherBO value = JSON.parseObject(cityWeatherMap.get(key).toString(), WeatherBO.class);
            weatherMap.put(key, value);

            // 第二步, 存文件
            try {
                File cityFile = new File(tempDir.getPath() + File.separator + key);
                if (!cityFile.exists()) {
                    boolean newFileCreated = cityFile.createNewFile();
                    if (!newFileCreated) {
                        continue;
                    }
                }
                FileUtil.writeBytes(cityWeatherMap.get(key).toString().getBytes(), cityFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public void setWeatherDir(File tempDir) {
        this.tempDir = tempDir;
    }
}
