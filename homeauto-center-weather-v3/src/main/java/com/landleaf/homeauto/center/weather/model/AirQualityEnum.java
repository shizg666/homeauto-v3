package com.landleaf.homeauto.center.weather.model;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/8/18
 */
public enum AirQualityEnum {

    /**
     * 空气质量:优
     */
    EXCELLENT {
        @Override
        public String getLevel() {
            return "优";
        }

    },

    /**
     * 空气质量:良
     */

    GOOD {
        @Override
        public String getLevel() {
            return "良";
        }

    },

    /**
     * 空气质量:中
     */
    MEDIUM {
        @Override
        public String getLevel() {
            return "中";
        }

    },

    NONE {
        @Override
        public String getLevel() {
            return "未知";
        }
    };

    public static AirQualityEnum getAirQualityByPm25(Integer pm25) {
        if (0 <= pm25 && pm25 <= 50) {
            return AirQualityEnum.EXCELLENT;
        } else if (50 <= pm25 && pm25 <= 100) {
            return AirQualityEnum.GOOD;
        } else if (100 <= pm25) {
            return AirQualityEnum.MEDIUM;
        } else {
            return NONE;
        }
    }

    public abstract String getLevel();
}
