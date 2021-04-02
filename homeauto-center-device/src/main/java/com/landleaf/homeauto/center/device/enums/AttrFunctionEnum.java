package com.landleaf.homeauto.center.device.enums;//package com.landleaf.homeauto.center.device.enums;

/**
 * 产品属性类别
 *
 * @author 产品属性类别
 */
public enum AttrFunctionEnum {

    /**
     * 功能性
     */
    FUNCTION_ATTR(1,"功能属性"),
    /**
     * 基本属性
     */
    BASE_ATTR(2,"基本属性"),
    /**
     * 故障属性
     */
    ERROR_ATTR(3,"故障属性"),
    ;

    private Integer type;
    private String name;

    AttrFunctionEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
