package com.landleaf.homeauto.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.landleaf.homeauto.common.constance.CommonConst;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/9/12 14:06
 * @description: 字符处理类
 */
public class ShStringUtil {


    /**
     * list -> str
     *
     * @param list
     * @return
     */
    public static String list2Str(List<String> list) {
        if (CollectionUtil.isEmpty(list)) {
            return CommonConst.SymbolConst.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        list.forEach(s -> sb.append(s).append(CommonConst.SymbolConst.COMMA));
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : sb.toString();
    }


    /**
     * 字符串截取
     *
     * @param oriStr
     * @param limit
     * @return
     */
    public static String limitStr(String oriStr, int limit) {
        return oriStr.length() > limit ? oriStr.substring(0, limit) : oriStr;
    }
}
