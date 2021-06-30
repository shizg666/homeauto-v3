package com.landleaf.homeauto.center.device.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备类型
 */
public enum DeviceTypeEnum {

    /**
     * 平台
     */
    SYSTEM(2, "系统设备"),
    PUTONG(0, "普通设备"),

    SUB_SYSTEM(1, "系统子设备");


    private Integer type;

    private String desc;

    DeviceTypeEnum(Integer type, String desc) {
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


    private static Map<Integer, DeviceTypeEnum> PLATFORM_MAP = new HashMap<>();

    static {
        for (DeviceTypeEnum p : values()) {
            PLATFORM_MAP.put(p.getType(), p);
        }
    }

    /**
     * 获取平台枚举
     *
     * @param type
     * @return
     */
    public static DeviceTypeEnum getPlatformTypeEnum(Integer type) {
        return PLATFORM_MAP.get(type);
    }


}
