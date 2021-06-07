package com.landleaf.homeauto.common.enums;


/**
 * 故障类型
 */
public enum FamilyFaultEnum implements BaseEnum {
    HAVC_ERROR(1, "暖通故障或二进制故障"),
    NUM_ERROR(2, "数值异常"),
    LINK_ERROR(3, "通信异常"),
    ;

    public final int type;
    public String name;

    FamilyFaultEnum(int type, String name) {
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


    public static FamilyFaultEnum getStatusByType(Integer type) {
        if (type == null) {
            return HAVC_ERROR;
        }
        FamilyFaultEnum[] values = FamilyFaultEnum.values();
        for (FamilyFaultEnum value : values) {
            if (value.getType() == type.intValue()) {
                return value;
            }
        }
        return HAVC_ERROR;
    }

}
