package com.landleaf.homeauto.center.device.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 楼盘状态枚举
 */
public enum HouseTemplateFloorTypeEnum {
    ONG_FLOOR(1, "单楼层"),
    MORE_FLOOR(2, "多楼层");

    public Integer type;
    public String name;

    HouseTemplateFloorTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Map<Integer, HouseTemplateFloorTypeEnum> getMap() {
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


    private static Map<Integer, HouseTemplateFloorTypeEnum> map = Maps.newHashMapWithExpectedSize(HouseTemplateFloorTypeEnum.values().length);
    static {
        for(HouseTemplateFloorTypeEnum enu : HouseTemplateFloorTypeEnum.values()){
            map.put(enu.getType(), enu);
        }
    }
    public static HouseTemplateFloorTypeEnum getInstByType(Integer type){
        if(type==null){
            return null;
        }
        HouseTemplateFloorTypeEnum pojoEnum = map.get(type);
        return pojoEnum;
    }
}

