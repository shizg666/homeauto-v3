package com.landleaf.homeauto.contact.screen.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 上报大屏数据的生产者枚举
 *
 * @author wenyilu
 */
public enum ContactScreenUploadProcedureEnum {

    FAMILY_SCENE_STATUS_UPDATE("FamilySceneStatusUpdate", "场景状态上报通知", "familySceneStatusChangeRocketMqProcedure"),

    DEVICE_STATUS_UPDATE("DeviceStatusUpdate", "设备状态更新", "deviceStatusChangeRocketMqProcedure"),

    FAMILY_DEVICE_ALARM_EVENT("FamilyDeviceAlarmEvent", "报警信息上报", "familyDeviceAlarmEventRocketMqProcedure"),


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

    ContactScreenUploadProcedureEnum(String code, String name, String beanName) {
        this.code = code;
        this.name = name;
        this.beanName = beanName;
    }

    ContactScreenUploadProcedureEnum() {
    }

    public static ContactScreenUploadProcedureEnum getByCode(String code) {

        for (ContactScreenUploadProcedureEnum value : ContactScreenUploadProcedureEnum.values()) {
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
