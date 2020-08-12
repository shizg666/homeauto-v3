package com.landleaf.homeauto.contact.screen.common.util;


import com.landleaf.homeauto.common.constance.RedisCacheConst;

/**
 * 获取redis的key
 *
 * @author wenyilu
 */
public final class ContactScreenRedisKeyUtil {
    /**
     * 获取数据下行的redis中等待ack的key值
     *
     * @param familyCode  家庭编码
     * @param screenMac   大屏mac
     * @param operateName 通讯类型
     * @param msgId       消息编号
     * @return 存储的key
     */
    public static String getCommandAckKey(String familyCode, String screenMac, String operateName, String msgId) {
        return new StringBuilder().append(RedisCacheConst.CONTACT_SCREEN_MSG_WAIT_ACK_PREFIX_MQTT)
                .append(getMessageKey(familyCode, screenMac, operateName, msgId, 0)).toString();
    }


    /**
     * 获取message的key值
     *
     * @param familyCode  家庭编码
     * @param screenMac   大屏mac
     * @param operateName 通讯类型
     * @param msgId       消息编号
     * @param current     当前包的下标
     * @return message的key值
     */
    public static String getMessageKey(String familyCode, String screenMac, String operateName, String msgId, int current) {
        return new StringBuilder().append(familyCode).append(":").append(screenMac).append(":").append(operateName).append(":").append(msgId).append(":")
                .append(current).toString();
    }

}
