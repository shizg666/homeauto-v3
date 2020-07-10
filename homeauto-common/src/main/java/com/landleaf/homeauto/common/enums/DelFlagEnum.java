package com.landleaf.homeauto.common.enums;


/**
 * 是否删除枚举
 */
public enum DelFlagEnum implements BaseEnum {
    UNDELETE(0, "未删除"),
    DELETED(2, "已删除");

    public final int type;
    public String name;

    DelFlagEnum(int type, String name) {
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

}
