package com.landleaf.homeauto.common.enums;


/**
 * 启用状态
 */
public enum StatusEnum implements BaseEnum {
    ACTIVE(1, "启用中"),
    INACTIVE(0, "停用");

    public final int type;
    public String name;

    StatusEnum(int type, String name) {
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


    public static StatusEnum getStatusByType(Integer status){
        if(status==null){
            return INACTIVE;
        }
        StatusEnum[] values = StatusEnum.values();
        for (StatusEnum value : values) {
            if(value.getType()==status.intValue()){
                return value;
            }
        }
        return INACTIVE;
    }

}
