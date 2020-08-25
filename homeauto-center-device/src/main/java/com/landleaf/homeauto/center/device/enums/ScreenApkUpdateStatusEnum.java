package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 大屏apk更新状态枚举
 */
public enum ScreenApkUpdateStatusEnum {
    /**
     *
     */
    UPDATING(1, "更新中"),
    SUCCESS(2, "成功"),
    FAIL(3, "失败"),

    ;


    public Integer type;
    public String name;

    ScreenApkUpdateStatusEnum(Integer type, String name) {
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

    private static Map<Integer, ScreenApkUpdateStatusEnum> map = null; // type, enum映射
    private static boolean isInit = false;

    public static ScreenApkUpdateStatusEnum getInstByType(Integer type) {
        if (type == null) {
            return null;
        }
        if (!isInit) {
            synchronized (ScreenApkUpdateStatusEnum.class) {
                if (!isInit) {
                    map = new HashMap<Integer, ScreenApkUpdateStatusEnum>();
                    for (ScreenApkUpdateStatusEnum enu : ScreenApkUpdateStatusEnum.values()) {
                        map.put(enu.getType(), enu);
                    }
                }
                isInit = true;
            }

        }
        ScreenApkUpdateStatusEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
