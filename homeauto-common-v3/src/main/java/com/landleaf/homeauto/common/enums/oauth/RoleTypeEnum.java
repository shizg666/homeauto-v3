package com.landleaf.homeauto.common.enums.oauth;


import com.landleaf.homeauto.common.enums.BaseEnum;

/**
 * 后台管理用户角色类型
 */
public enum RoleTypeEnum implements BaseEnum {
    LANDLEAF(1, "朗绿"),
    PROPERTY(2, "物业"),
    OTHER(0, "其它");

    public final int type;
    public String name;


    RoleTypeEnum(int type, String name) {
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

    public static RoleTypeEnum getEnumByType(Integer status) {
        if (status == null) {
            return OTHER;
        }
        RoleTypeEnum[] values = RoleTypeEnum.values();
        for (RoleTypeEnum value : values) {
            if (value.getType() == status.intValue()) {
                return value;
            }
        }
        return OTHER;
    }
}
