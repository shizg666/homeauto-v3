package com.landleaf.homeauto.center.device.enums;

/**
 * 产品属性枚举
 *
 * @author Yujiumin
 * @version 2020/9/17
 */
public enum ProductPropertyEnum {

    /**
     * 加湿使能
     */
    HUMIDIFICATION_ENABLE("humidification_enable"),

    /**
     * 系统风量
     */
    SYSTEM_AIR_VOLUME("system_air_volume"),

    /**
     * 节能模式
     */
    ENERGY_SAVING_MODE("energy_saving_mode"),

    /**
     * 开关
     */
    SWITCH("switch"),

    /**
     * 布放状态
     */
    ARMING_STATE("arming_state"),

    /**
     * 模式
     */
    MODE("mode"),

    /**
     * 温度
     */
    TEMPERATURE("temperature"),

    /**
     * 风速
     */
    AIR_VOLUME("air_volume"),

    /**
     * 设定温度
     */
    SETTING_TEMPERATURE("setting_temperature"),

    /**
     * 风量
     */
    WIND_SPEED("wind_speed"),

    /**
     * 回风温度
     */
    RETURN_AIR_TEMPERATURE("return_air_temperature"),

    /**
     * 调光
     */
    DIMMING("dimming"),

    /**
     * 度数
     */
    DEGREE("degree"),

    /**
     * 二氧化碳
     */
    CO2("co2"),

    /**
     * pm2.5
     */
    PM25("pm25"),

    /**
     * 甲醛
     */
    HCHO("formaldehyde"),

    /**
     * 湿度
     */
    HUMIDITY("humidity");

    protected String code;

    ProductPropertyEnum(String code) {
        this.code = code;
    }

}
