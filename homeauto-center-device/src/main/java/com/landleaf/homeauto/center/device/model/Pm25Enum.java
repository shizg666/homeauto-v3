package com.landleaf.homeauto.center.device.model;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
public enum Pm25Enum {

    /**
     * 0~50: 优
     */
    LEVEL_EXCELLENT(0, "优"),

    /**
     * 50~100: 良
     */
    LEVEL_GOOD(50, "良"),

    /**
     * 100以上: 中
     */
    LEVEL_MEDIUM(100, "中"),

    /**
     * 否则不判断
     */
    LEVEL_NONE(-1, "未知");

    Integer value;
    String level;

    Pm25Enum(Integer value, String level) {
        this.value = value;
        this.level = level;
    }

    public static String getAirQualityByPm25(Integer pm25) {
        if (LEVEL_EXCELLENT.value < pm25 && pm25 <= LEVEL_GOOD.value) {
            return Pm25Enum.LEVEL_EXCELLENT.level;
        } else if (LEVEL_GOOD.value < pm25 && pm25 <= LEVEL_MEDIUM.value) {
            return Pm25Enum.LEVEL_GOOD.level;
        } else if (LEVEL_MEDIUM.value < pm25) {
            return Pm25Enum.LEVEL_MEDIUM.level;
        } else {
            return LEVEL_NONE.level;
        }
    }
}
