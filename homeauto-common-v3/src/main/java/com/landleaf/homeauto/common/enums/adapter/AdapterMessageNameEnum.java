package com.landleaf.homeauto.common.enums.adapter;


/**
 * 通讯消息名称
 *
 * @author wenyilu
 */
public enum AdapterMessageNameEnum {
    /**
     * 读取状态
     */
    TAG_DEVICE_STATUS_READ("device_status_read", "读取状态"),

    /**
     * 控制设备
     */
    TAG_DEVICE_WRITE("device_write", "控制设备"),

    TAG_FAMILY_CONFIG_UPDATE("family_config_update", "配置更新"),

    TAG_FAMILY_SCENE_SET("family_scene_set", "控制场景"),

    FAMILY_SECURITY_ALARM_EVENT("family_security_alarm_event", "安防报警上报"),

    DEVICE_STATUS_UPLOAD("device_status_upload", "状态上报"),

    HVAC_FAULT_UPLOAD("hvac_fault_upload", "暖通故障上报"),

    SCREEN_SCENE_SET_UPLOAD("screen_scene_set_upload", "大屏控制场景上报"),
    ;

    private String name;
    private String des;

    AdapterMessageNameEnum(String name, String des) {
        this.name = name;
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
