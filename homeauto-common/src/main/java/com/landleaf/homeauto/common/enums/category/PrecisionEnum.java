package com.landleaf.homeauto.common.enums.category;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

/**
 * 精度枚举
 */
public enum PrecisionEnum {

    POWER_1(0, "整数") {
        @Override
        public Object parse(Object value) {
            return value;
        }
    },

    POWER_2(1, "保留一位小数") {
        @Override
        public Object parse(Object value) {
            return Integer.parseInt(Objects.toString(value)) / 10.0F;
        }
    },

    POWER_3(2, "保留两位小数") {
        @Override
        public Object parse(Object value) {
            return Integer.parseInt(Objects.toString(value)) / 100.0F;
        }
    };


    public Integer type;
    public String name;

    PrecisionEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Map<Integer, PrecisionEnum> getMap() {
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
     *
     * @param type
     * @return
     */


    private static Map<Integer, PrecisionEnum> map = Maps.newHashMapWithExpectedSize(PrecisionEnum.values().length);

    static {
        for (PrecisionEnum enu : PrecisionEnum.values()) {
            map.put(enu.getType(), enu);
        }
    }

    public static PrecisionEnum getInstByType(Integer type) {
        if (type == null) {
            return null;
        }
        PrecisionEnum pojoEnum = map.get(type);
        return pojoEnum;
    }

    public abstract Object parse(Object value);
}
