package com.landleaf.homeauto.center.device.enums;

import lombok.Data;

/**
 * 品类定义枚举
 *
 * @author Yujiumin
 * @version 2020/8/31
 */
public enum CategoryEnum {

    /**
     * 多参数传感器
     */
    MULTI_PARAM_SENSOR("多参数传感器", 1, ""),

    /**
     * 全参数传感器
     */
    ALL_PARAM_SENSOR("全参数传感器", 2, ""),

    /**
     * 甲醛传感器
     */
    HCHO_SENSOR("甲醛传感器", 3, ""),

    /**
     * PM2.5传感器
     */
    PM25_SENSOR("pm2.5传感器", 4, ""),

    /**
     * 暖通
     */
    HAVC("暖通", 5, ""),

    /**
     * 温控面板
     */
    PANEL_TEMP("温控面板", 6, ""),

    /**
     * 朗绿新风
     */
    LANDLEAF_FRESH_AIR("朗绿新风", 7, ""),

    /**
     * 空调
     */
    AIR_CONDITION("空调", 8, ""),

    /**
     * 窗帘
     */
    WINDOW_CURTAINS("窗帘", 9, "9001-9099为开合帘;9100-9199为卷帘"),

    /**
     * 电表
     */
    ELECTRIC_METER("电表", 10, ""),

    /**
     * 灯
     */
    LIGHT("灯", 11, "11001-11099为开关灯;11100-11199为调光灯"),

    /**
     * 安防
     */
    SECURITY("安防", 12, ""),

    /**
     * 地暖
     */
    FLOOR_HEATING("地暖", 13, ""),

    /**
     * 背景音乐
     */
    BACKGROUND_MUSIC("背景音乐", 14, ""),

    /**
     * 水阀煤气阀
     */
    WATER_GAS_VALVE("水阀煤气阀", 15, ""),

    /**
     * 智能猫眼
     */
    SMART_PEEPHOLE("智能猫眼", 16, ""),

    /**
     * 睡眠监测
     */
    SLEEP_MONITOR("睡眠监测", 17, "");

    String name;

    Integer type;

    String backup;

    CategoryEnum(String name, Integer type, String backup) {
        this.name = name;
        this.type = type;
        this.backup = backup;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }
}
