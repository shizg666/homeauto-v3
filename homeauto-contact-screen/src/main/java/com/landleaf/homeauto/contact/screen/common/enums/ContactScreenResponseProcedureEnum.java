package com.landleaf.homeauto.contact.screen.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 响应内部服务发送的指令的生产者枚举
 *
 * @author wenyilu
 */
public enum ContactScreenResponseProcedureEnum {

    DEVICE_WRITE("DeviceWrite", "设备写入", "deviceWriteResponeRocketMqProcedure"),

    DEVICE_STATUS_READ("DeviceStatusRead", "读取状态", "deviceStatusReadResponeRocketMqProcedure"),

    FAMILY_SCENE_SET("FamilySceneSet", "控制场景", "familySceneControlResponeRocketMqProcedure"),

    FAMILY_CONFIG_UPDATE("FamilyConfigUpdate", "配置数据更新通知", "familyConfigUpdateResponeRocketMqProcedure"),

    SCREEN_APK_UPDATE("ScreenApkUpdate", "大屏apk升级", "familyConfigUpdateResponeRocketMqProcedure"),

    ;
    /**
     * 设备操作类型code
     */
    private String code;
    /**
     * 业务名称
     */
    public String name;
    /**
     * 业务类
     */
    public String beanName;

    ContactScreenResponseProcedureEnum(String code, String name, String beanName) {
        this.code = code;
        this.name = name;
        this.beanName = beanName;
    }

    ContactScreenResponseProcedureEnum() {
    }

    public static ContactScreenResponseProcedureEnum getByCode(String code) {

        for (ContactScreenResponseProcedureEnum value : ContactScreenResponseProcedureEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

}
