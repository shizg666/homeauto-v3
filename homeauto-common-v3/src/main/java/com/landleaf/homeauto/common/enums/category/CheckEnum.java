package com.landleaf.homeauto.common.enums.category;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 校验模式
 */
public enum CheckEnum {
    ODD_PARITY(1, "奇校验"),
    EVEN_PARITY(2, "偶校验"),
    NO_PARITY (3,"无校验");



    public Integer type;
    public String name;

    CheckEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Map<Integer, CheckEnum> getMap() {
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


    private static Map<Integer, CheckEnum> map = Maps.newHashMapWithExpectedSize(CheckEnum.values().length);
    static {
        for(CheckEnum enu : CheckEnum.values()){
            map.put(enu.getType(), enu);
        }
    }
    public static CheckEnum getInstByType(Integer type){
        if(type==null){
            return null;
        }
        CheckEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
