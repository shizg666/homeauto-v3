package com.landleaf.homeauto.center.device.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * app平台
 */
public enum PlatformTypeEnum {

    /**
     * 平台
     */
    ANDROID(1, "Android"),

    IOS(2, "IOS");


    private Integer type;

    private String desc;

    PlatformTypeEnum(Integer type, String desc) {
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


    private static Map<Integer, PlatformTypeEnum> PLATFORM_MAP = new HashMap<>();

    static {
        for (PlatformTypeEnum p : values()) {
            PLATFORM_MAP.put(p.getType(), p);
        }
    }

    /**
     * 获取平台枚举
     *
     * @param type
     * @return
     */
    public static PlatformTypeEnum getPlatformTypeEnum(Integer type) {
        return PLATFORM_MAP.get(type);
    }


}
