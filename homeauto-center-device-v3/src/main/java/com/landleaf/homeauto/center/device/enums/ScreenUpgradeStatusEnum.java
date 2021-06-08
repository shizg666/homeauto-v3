package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 大屏apk更新状态枚举
 */
public enum ScreenUpgradeStatusEnum {
    /**
     *
     */
    UN_SUCCESS(1, "未完成"),
    SUCCESS(2, "已完成"),

    ;


    public Integer type;
    public String name;

    ScreenUpgradeStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getType() {
        return type;
    }


    /**
     * 根据type获取枚举对象
     *
     * @param type
     * @return
     */

    private static Map<Integer, ScreenUpgradeStatusEnum> map = null; // type, enum映射
    private static boolean isInit = false;

    public static ScreenUpgradeStatusEnum getInstByType(Integer type) {
        if (type == null) {
            return null;
        }
        if (!isInit) {
            synchronized (ScreenUpgradeStatusEnum.class) {
                if (!isInit) {
                    map = new HashMap<Integer, ScreenUpgradeStatusEnum>();
                    for (ScreenUpgradeStatusEnum enu : ScreenUpgradeStatusEnum.values()) {
                        map.put(enu.getType(), enu);
                    }
                }
                isInit = true;
            }

        }
        ScreenUpgradeStatusEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
