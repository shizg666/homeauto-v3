package com.landleaf.homeauto.common.enums.category;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 模特率枚举
 */
public enum BaudRateEnum {
    POWER_1(1, "9600"),
    POWER_2(2,"4800"),
    POWER_3(3,"3600");



    public Integer type;
    public String name;

    BaudRateEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Map<Integer, BaudRateEnum> getMap() {
        return map;
    }

    public String getName() {
        return this.name;
    }

    public Integer getType() {
        return type;
    }

    /**
     * 根据type获取枚举对象
     * @param type
     * @return
     */


    private static Map<Integer, BaudRateEnum> map = Maps.newHashMapWithExpectedSize(BaudRateEnum.values().length);
    static {
        for(BaudRateEnum enu : BaudRateEnum.values()){
            map.put(enu.getType(), enu);
        }
    }
    public static BaudRateEnum getInstByType(Integer type){
        if(type==null){
            return null;
        }
        BaudRateEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
