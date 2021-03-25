package com.landleaf.homeauto.common.util;

import com.landleaf.homeauto.common.constant.RedisCacheConst;

/**
 * @author Yujiumin
 * @version 2020/8/26
 */
public final class RedisKeyUtils {


    /**
     * 获取家庭设备属性值
     * @param familyCode
     * @param deviceCode
     * @param attributeCode
     * @return java.lang.String
     * @author wenyilu
     * @date  2021/1/6 14:07
     */
    public static String getDeviceStatusKey(String familyCode, String deviceCode, String attributeCode) {
        return String.format(RedisCacheConst.FAMILY_DEVICE_STATUS_STORE_KEY, familyCode, deviceCode, attributeCode);
    }

}
