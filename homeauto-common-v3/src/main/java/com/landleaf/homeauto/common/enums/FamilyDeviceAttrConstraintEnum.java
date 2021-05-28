package com.landleaf.homeauto.common.enums;


/**
 * 设备属性类型
 */
public enum FamilyDeviceAttrConstraintEnum implements BaseEnum {
    NORMAL_ATTR(0, "普通属性"),
    SYSTEM_ATTR(1, "系统属性"),
    RELATED_SYSTEM_ATTR(2, "关联系统属性"),
    ;

    public final int type;
    public String name;

    FamilyDeviceAttrConstraintEnum(int type, String name) {
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


    public static FamilyDeviceAttrConstraintEnum getStatusByType(Integer type) {
        if (type == null) {
            return NORMAL_ATTR;
        }
        FamilyDeviceAttrConstraintEnum[] values = FamilyDeviceAttrConstraintEnum.values();
        for (FamilyDeviceAttrConstraintEnum value : values) {
            if (value.getType() == type.intValue()) {
                return value;
            }
        }
        return NORMAL_ATTR;
    }

}
