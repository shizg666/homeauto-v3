package com.landleaf.homeauto.center.device.model.constant;

/**
 * 设备性质类型枚举
 *
 * @author Yujiumin
 * @version 2020/10/16
 */
public enum DeviceNatureEnum {

    /**
     * 控制类型
     */
    CONTROLLABLE(1, "控制"),

    /**
     * 只读类型
     */
    READ_ONLY(2, "只读");

    DeviceNatureEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
