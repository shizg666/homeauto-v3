package com.landleaf.homeauto.center.device.enums;//package com.landleaf.homeauto.center.device.enums;

/**
 * 屬性app是否需要枚舉
 *
 * @author pilo
 */
public enum AttrAppFlagEnum {

    /**
     * 需要
     */
    ACTIVE(1),

    /**
     * 不需要
     */
    INACTIVE(0);


    private Integer code;

    AttrAppFlagEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
