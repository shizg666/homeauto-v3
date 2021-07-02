package com.landleaf.homeauto.center.device.enums;

import java.util.HashMap;
import java.util.Map;


public enum GenderEnum {

    /**
     * 平台
     */
    BOY(1, "男"),
    GIRL(2, "女"),

    SEX(3, "未知");


    private Integer type;

    private String desc;

    GenderEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    private static Map<Integer, GenderEnum> PLATFORM_MAP = new HashMap<>();

    static {
        for (GenderEnum p : values()) {
            PLATFORM_MAP.put(p.getType(), p);
        }
    }

    /**
     * 获取平台枚举
     *
     * @param type
     * @return
     */
    public static GenderEnum getPlatformTypeEnum(Integer type) {
        return PLATFORM_MAP.get(type);
    }


}
