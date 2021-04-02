package com.landleaf.homeauto.common.enums.oauth;


import com.landleaf.homeauto.common.enums.BaseEnum;

/**
 * 权限类型
 */
public enum PermissionTypeEnum implements BaseEnum {
    MENU(1, "菜单"),
    BUTTON(2, "按钮"),
    PAGE(3, "页面"),
    ;
    public final int type;
    public String name;


    PermissionTypeEnum(int type, String name) {
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

    public static PermissionTypeEnum getEnumByType(Integer status) {
        if (status == null) {
            return BUTTON;
        }
        PermissionTypeEnum[] values = PermissionTypeEnum.values();
        for (PermissionTypeEnum value : values) {
            if (value.getType() == status.intValue()) {
                return value;
            }
        }
        return BUTTON;
    }
}
