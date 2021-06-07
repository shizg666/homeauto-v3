package com.landleaf.homeauto.common.enums.screen;


import com.landleaf.homeauto.common.enums.BaseEnum;

/**
 * 升級類型
 */
public enum UpgradeTypeEnum implements BaseEnum {
    USER(1, "用戶升级"),
    BACKGROUND(2, "后台升级"),
    ;

    public final int type;
    public String name;

    UpgradeTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static UpgradeTypeEnum getStatusByType(Integer type) {
        if (type == null) {
            return BACKGROUND;
        }
        UpgradeTypeEnum[] values = UpgradeTypeEnum.values();
        for (UpgradeTypeEnum value : values) {
            if (value.getType() == type.intValue()) {
                return value;
            }
        }
        return BACKGROUND;
    }

}
