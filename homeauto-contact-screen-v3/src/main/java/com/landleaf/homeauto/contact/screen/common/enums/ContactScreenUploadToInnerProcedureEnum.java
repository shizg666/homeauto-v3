package com.landleaf.homeauto.contact.screen.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 上报大屏数据的生产者枚举
 *
 * @author wenyilu
 */
public enum ContactScreenUploadToInnerProcedureEnum {

    /**
     * 上报大屏数据的生产者枚举
     */
    SCREEN_SCENE_SET_UPLOAD("ScreenSceneSetUpload", "大屏场景执行上报", "screenSceneSetUploadRocketMqProcedure"),

    DEVICE_STATUS_UPDATE("DeviceStatusUpdate", "设备状态更新", "deviceStatusChangeRocketMqProcedure"),

    FAMILY_DEVICE_ALARM_EVENT("FamilySecurityAlarmEvent", "报警信息上报", "familyDeviceAlarmEventRocketMqProcedure"),

    HVAC_POWER_UPLOAD("HVACPowerUpload", "暖通功率上传", "hVACPowerUploadRocketMqProcedure"),

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

    ContactScreenUploadToInnerProcedureEnum(String code, String name, String beanName) {
        this.code = code;
        this.name = name;
        this.beanName = beanName;
    }

    ContactScreenUploadToInnerProcedureEnum() {
    }

    public static ContactScreenUploadToInnerProcedureEnum getByCode(String code) {

        for (ContactScreenUploadToInnerProcedureEnum value : ContactScreenUploadToInnerProcedureEnum.values()) {
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
