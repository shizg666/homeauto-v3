package com.landleaf.homeauto.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/9/9 11:21
 * @description:
 */
public class ThreadLocalUtil {


    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 存储
     *
     * @param key
     * @param value
     * @return void [返回类型说明]
     * @author nijia
     * @see [类、类#方法、类#成员]
     */
    public static void put(String key, Object value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new HashMap<>(16);
        }
        map.put(key, value);
        THREAD_LOCAL.set(map);
    }

    /**
     * 取值
     *
     * @param key
     * @return T [返回类型说明]
     * @returnt
     * @author nijia
     * @see [类、类#方法、类嗯#成员]
     */

    public static <T> T get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();

        if (map != null) {
            return (T) map.get(key);

        }

        return null;
    }

    public static void release() {
        THREAD_LOCAL.remove();
    }
}
