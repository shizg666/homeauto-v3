package com.landleaf.homeauto.common.enums.category;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 校验模式
 */
public enum ErrorStatusEnum {
    ODD_PARITY(0, "未解决"),
    EVEN_PARITY(1, "已解决");



    public Integer type;
    public String name;

    ErrorStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Map<Integer, ErrorStatusEnum> getMap() {
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


    private static Map<Integer, ErrorStatusEnum> map = Maps.newHashMapWithExpectedSize(ErrorStatusEnum.values().length);
    static {
        for(ErrorStatusEnum enu : ErrorStatusEnum.values()){
            map.put(enu.getType(), enu);
        }
    }
    public static ErrorStatusEnum getInstByType(Integer type){
        if(type==null){
            return null;
        }
        ErrorStatusEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}
