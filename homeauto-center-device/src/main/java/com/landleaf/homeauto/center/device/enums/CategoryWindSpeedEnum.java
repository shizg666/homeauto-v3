package com.landleaf.homeauto.center.device.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/3
 */
public enum CategoryWindSpeedEnum {

    /**
     * 低速风
     */
    LOW("low_speed", "低速"),

    /**
     * 中速风
     */
    MEDIUM("medium_speed", "中速"),

    /**
     * 高速风
     */
    HIGH("high_speed", "高速");

    String value;

    @Getter
    String name;

    CategoryWindSpeedEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CategoryWindSpeedEnum getInstance(String code) {
        for (CategoryWindSpeedEnum windSpeedEnum : values()) {
            if (Objects.equals(windSpeedEnum.value, code)) {
                return windSpeedEnum;
            }
        }
        return null;
    }
}
