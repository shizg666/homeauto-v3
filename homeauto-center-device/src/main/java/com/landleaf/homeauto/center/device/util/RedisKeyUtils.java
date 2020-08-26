package com.landleaf.homeauto.center.device.util;

import com.landleaf.homeauto.common.constant.RedisCacheConst;

/**
 * @author Yujiumin
 * @version 2020/8/26
 */
public final class RedisKeyUtils {

    /**
     * @param familyCode    家庭码
     * @param productCode   产品码
     * @param deviceSn      设备序列号
     * @param attributeCode 属性码
     * @return 设备状态的KEY值
     * @author yujiumin
     */
    public static String getDeviceStatusKey(String familyCode, String productCode, String deviceSn, String attributeCode) {
        return String.format(RedisCacheConst.FAMILY_DEVICE_STATUS_STORE_KEY, familyCode, productCode, deviceSn, attributeCode);
    }

}
