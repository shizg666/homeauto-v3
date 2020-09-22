package com.landleaf.homeauto.center.websocket.rocketmq.util;

import java.util.*;

/**
 * 集合工具类
 *
 * @author Yujiumin
 * @version 2020/9/7
 */
public class CollectionUtils {

    /**
     * @param isLinked
     * @param element
     * @param <T>
     * @return
     */
    public static <T> List<T> list(boolean isLinked, T element) {
        List<T> list = isLinked ? new LinkedList<>() : new ArrayList<>();
        list.add(element);
        return list;
    }

    /**
     * @param isLinked
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> map(boolean isLinked, K key, V value) {
        Map<K, V> map = isLinked ? new LinkedHashMap<>(16) : new HashMap<>(16);
        map.put(key, value);
        return map;
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> intersection = new LinkedList<>();
        for (T t : list1) {
            if (list2.contains(t)) {
                intersection.add(t);
            }
        }
        return intersection;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

}
