package com.landleaf.homeauto.center.device.util;

import cn.hutool.core.util.NumberUtil;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/10/24
 */
public class NumberUtils {

    public static <T extends Number> Object parse(Object value, Class<T> clazz) {
        String valueString = Objects.toString(value);
        if (NumberUtil.isNumber(valueString)) {
            if (Objects.equals(clazz, Integer.class)) {
                return Integer.parseInt(valueString);
            } else if (Objects.equals(clazz, Float.class)) {
                return Float.parseFloat(valueString);
            }
        }
        return value;
    }

}
