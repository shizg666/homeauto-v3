package com.landleaf.homeauto.center.device.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * @Author Lokiy
 * @Date 2021/7/28 14:00
 * @Description 云端同步类型
 */
@AllArgsConstructor
@Getter
public enum CloudSyncTypeEnum {

    /**
     * 数据同步标识
     */

    FAMILY_DEVICE_STATUS_CURRENT("family_device_status_current"),

    FAMILY_DEVICE_INFO_STATUS("family_device_info_status"),

    HOME_AUTO_FAULT_DEVICE_CURRENT("home_auto_fault_device_current"),


    FAMILY_DEVICE_STATUS_HISTORY("family_device_status_history"),

    HOME_AUTO_FAULT_DEVICE_HAVC("home_auto_fault_device_havc"),

    HOME_AUTO_FAULT_DEVICE_LINK("home_auto_fault_device_link"),

    HOME_AUTO_FAULT_DEVICE_VALUE("home_auto_fault_device_value"),

    ;

    private String type;


    private static final Map<String,CloudSyncTypeEnum> MAP = Maps.newConcurrentMap();
    static {
        for (CloudSyncTypeEnum e: values()) {
            MAP.put(e.getType(), e);
        }
     }

    /**
     * 根据类型获取同步枚举值
     * @param type 类型
     * @return 同步枚举值
     */
     public static CloudSyncTypeEnum get(String type){
        return MAP.get(type);
     }
}
