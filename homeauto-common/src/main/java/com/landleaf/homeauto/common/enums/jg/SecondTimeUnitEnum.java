package com.landleaf.homeauto.common.enums.jg;

import com.landleaf.homeauto.common.constance.TimeConst;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lokiy
 * @date 2019/9/20 11:09
 * @description: 秒数时间单位
 */
public enum SecondTimeUnitEnum {

    /**
     * 秒
     */
    SECOND(1, TimeConst.ONE_SECOND, "秒"),

    MINUTE(2, TimeConst.ONE_MINUTE, "分钟"),

    HOUR(3, TimeConst.ONE_HOUR, "小时"),

    DAY(4, TimeConst.ONE_DAY, "天"),
    ;


    /**
     * 单位类型
     */
    private Integer unitType;

    /**
     * 秒数
     */
    private Integer seconds;

    /**
     * 描述
     */
    private String description;

    SecondTimeUnitEnum(Integer unitType, Integer seconds, String description) {
        this.unitType = unitType;
        this.seconds = seconds;
        this.description = description;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static Map<Integer, SecondTimeUnitEnum> TIME_UNIT_MAP = new ConcurrentHashMap<>();

    static {
        for (SecondTimeUnitEnum tue : SecondTimeUnitEnum.values()) {
            TIME_UNIT_MAP.put(tue.unitType, tue);
        }
    }


    /**
     * 获取枚举
     *
     * @param unitType
     * @return
     */
    public static SecondTimeUnitEnum getSecondTimeUnitEnum(Integer unitType) {
        return TIME_UNIT_MAP.get(unitType);
    }

}
