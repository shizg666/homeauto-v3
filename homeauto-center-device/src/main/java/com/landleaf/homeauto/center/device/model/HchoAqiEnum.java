package com.landleaf.homeauto.center.device.model;

/**
 * 甲醛与空气质量映射关系
 *
 * @author Yujiumin
 * @version 2020/8/27
 */
public enum HchoAqiEnum {

    /**
     * 级别0: 空气质量优的下界
     */
    LEVEL_0(0.0F),

    /**
     * 级别1: 空气质量优的上界
     */
    LEVEL_1(0.03F),

    /**
     * 级别2: 空气质量良的上界
     */
    LEVEL_2(0.08F),

    /**
     * 级别3: 空气质量差的上界
     */
    LEVEL_3(0.3F);

    Float value;

    HchoAqiEnum(Float value) {
        this.value = value;
    }

    /**
     * 获取空气质量
     *
     * @param hchoValue 甲醛数值
     * @return 空气质量
     */
    public static String getAqi(float hchoValue) {
        if (LEVEL_0.value < hchoValue && hchoValue <= LEVEL_1.value) {
            return "优";
        } else if (LEVEL_1.value < hchoValue && hchoValue <= LEVEL_2.value) {
            return "良";
        } else if (LEVEL_2.value < hchoValue) {
            return "差";
        } else {
            return "未知";
        }
    }
}
