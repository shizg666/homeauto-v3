package com.landleaf.homeauto.common.enums;


/**
 * 家庭设备类型
 */
public enum FamilySystemFlagEnum implements BaseEnum {
    NORMAL_DEVICE(0, "普通设备"),
    SYS_SUB_DEVICE(1, "系统下子设备"),
    SYS_DEVICE(2, "系统设备"),
    ;

    public final int type;
    public String name;

    FamilySystemFlagEnum(int type, String name) {
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


    public static FamilySystemFlagEnum getStatusByType(Integer type) {
        if (type == null) {
            return NORMAL_DEVICE;
        }
        FamilySystemFlagEnum[] values = FamilySystemFlagEnum.values();
        for (FamilySystemFlagEnum value : values) {
            if (value.getType() == type.intValue()) {
                return value;
            }
        }
        return NORMAL_DEVICE;
    }

}
