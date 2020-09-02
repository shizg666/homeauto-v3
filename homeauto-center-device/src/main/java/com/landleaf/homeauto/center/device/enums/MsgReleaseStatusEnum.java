package com.landleaf.homeauto.center.device.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 信息发布状态枚举
 * 发布标识 0-未发布 1-已发布
 */
public enum MsgReleaseStatusEnum {
    UNPUBLISHED(0, "未发布"),
    PUBLISHED(1, "已发布"),

    ;


    public Integer type;
    public String name;

    MsgReleaseStatusEnum(Integer type, String name) {
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

    private static Map<Integer, MsgReleaseStatusEnum> map = null; // type, enum映射
    private static boolean isInit = false;

    public static MsgReleaseStatusEnum getInstByType(Integer type) {
        if (type == null) {
            return null;
        }
        if (!isInit) {
            synchronized (MsgReleaseStatusEnum.class) {
                if (!isInit) {
                    map = new HashMap<Integer, MsgReleaseStatusEnum>();
                    for (MsgReleaseStatusEnum enu : MsgReleaseStatusEnum.values()) {
                        map.put(enu.getType(), enu);
                    }
                }
                isInit = true;
            }

        }
        MsgReleaseStatusEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
