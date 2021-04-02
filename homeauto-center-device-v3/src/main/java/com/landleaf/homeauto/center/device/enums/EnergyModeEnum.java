package com.landleaf.homeauto.center.device.enums;

import com.alibaba.druid.util.StringUtils;

/**
 *能源站模式枚举
 * @author wenyilu
 */
public enum EnergyModeEnum {

    /**
     * 制冷
     */
    COLD("cold",  "制冷"),
    HOT("hot",  "制热"),
    OVER_SEASON("over_season",  "过度季"),
    ;


    String code;

    String desc;


    EnergyModeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取品类枚举
     *
     * @param code
     * @return
     */
    public static EnergyModeEnum get(String code) {
        for (EnergyModeEnum energyModeEnum : values()) {
            if (StringUtils.equals(energyModeEnum.getCode(),code)) {
                return energyModeEnum;
            }
        }
        return OVER_SEASON;
    }
}
