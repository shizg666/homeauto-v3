package com.landleaf.homeauto.contact.screen.common.util;


import com.landleaf.homeauto.common.constant.RedisCacheConst;

/**
 * 获取redis的key
 *
 * @author wenyilu
 */
public final class ContactScreenRedisKeyUtil {
    /**
     * 获取数据下行的redis中等待ack的key值
     *
     * @param screenMac   大屏mac
     * @param operateName 通讯类型
     * @param msgId       消息编号
     * @return 存储的key
     */
    public static String getCommandAckKey(String screenMac, String operateName, String msgId) {
        return new StringBuilder().append(RedisCacheConst.CONTACT_SCREEN_MSG_WAIT_ACK_PREFIX_MQTT)
                .append(getMessageKey(screenMac, operateName, msgId, 0)).toString();
    }


    /**
     * 获取message的key值
     *
     * @param screenMac   大屏mac
     * @param operateName 通讯类型
     * @param msgId       消息编号
     * @param current     当前包的下标
     * @return message的key值
     */
    public static String getMessageKey(String screenMac, String operateName, String msgId, int current) {
        return new StringBuilder().append(":").append(screenMac).append(":").append(operateName).append(":").append(msgId).append(":")
                .append(current).toString();
    }

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
