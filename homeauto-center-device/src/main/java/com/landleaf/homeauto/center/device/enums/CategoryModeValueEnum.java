package com.landleaf.homeauto.center.device.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/3
 */
public enum CategoryModeValueEnum {
    /**
     * 制冷
     */
    COLD("cold", "制冷"),

    /**
     * 制热
     */
    HOT("hot", "制热"),

    /**
     * 送风
     */
    WIND("wind", "送风"),

    /**
     * 除湿
     */
    DEHUMIDIFICATION("dehumidifcation", "除湿");

    String value;

    @Getter
    String name;

    CategoryModeValueEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CategoryModeValueEnum getInstance(String code) {
        for (CategoryModeValueEnum modeValueEnum : values()) {
            if (Objects.equals(modeValueEnum.value, code)) {
                return modeValueEnum;
            }
        }
        return null;
    }
}
