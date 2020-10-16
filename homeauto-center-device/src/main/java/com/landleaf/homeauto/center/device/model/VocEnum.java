package com.landleaf.homeauto.center.device.model;

/**
 * Voc与空气质量映射关系
 *
 * @author Yujiumin
 * @version 2020/8/27
 */
public enum VocEnum {

    /**
     * 级别0: 空气质量优的下界
     */
    LEVEL_0(0.0F, "优"),

    /**
     * 级别1: 空气质量优的上界, 空气质量良的下界
     */
    LEVEL_1(0.26F, "良"),

    /**
     * 级别2: 空气质量良的上界, 空气质量中的下界
     */
    LEVEL_2(0.6F, "中"),

    /**
     * 级别3: 空气质量中的上界, 空气质量差的下界
     */
    LEVEL_3(1F, "差"),

    /**
     * 未知空气质量
     */
    LEVEL_NONE(-1F, "未知");

    Float value;

    String level;

    VocEnum(Float value, String level) {
        this.value = value;
        this.level = level;
    }

    /**
     * 获取空气质量
     *
     * @param value voc数值
     * @return 空气质量
     */
    public static String getAqi(float value) {
        if (LEVEL_0.value < value && value <= LEVEL_1.value) {
            return LEVEL_0.level;
        } else if (LEVEL_1.value < value && value <= LEVEL_2.value) {
            return LEVEL_1.level;
        } else if (LEVEL_2.value < value && value <= LEVEL_3.value) {
            return LEVEL_2.level;
        } else if (LEVEL_3.value < value) {
            return LEVEL_3.level;
        } else {
            return LEVEL_NONE.level;
        }
    }
}
