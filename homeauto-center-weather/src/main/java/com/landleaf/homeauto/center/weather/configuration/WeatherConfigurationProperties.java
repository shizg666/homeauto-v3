package com.landleaf.homeauto.center.weather.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
@Component
@ConfigurationProperties(prefix = "homeauto.weather")
public class WeatherConfigurationProperties {

    private String tempDir = "/homeauto/center/weather";

    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }
}
