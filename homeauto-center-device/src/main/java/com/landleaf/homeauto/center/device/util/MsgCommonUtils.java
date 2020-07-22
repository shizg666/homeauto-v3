package com.landleaf.homeauto.center.device.util;

import cn.hutool.core.util.PageUtil;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.domain.qry.MsgQry;
import com.landleaf.homeauto.common.enums.OperatorEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于处理各种通用的消息功能
 *
 * @author wenyilu
 */
public class MsgCommonUtils {

    /**
     * 获取分页后的集合
     *
     * @param tempList
     * @param msgQry
     * @param <T>
     * @return
     */
    public static <T> List<T> getPageList(List<T> tempList, MsgQry msgQry) {
        return tempList.stream()
                .skip(msgQry.getPageSize() * (msgQry.getPageNum() - 1))
                .limit(msgQry.getPageSize()).collect(Collectors.toList());
    }

    /**
     * 获取分页的对象
     *
     * @param resultList
     * @param tempList
     * @param msgQry
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> getPageInfo(List<T> resultList, List<T> tempList, MsgQry msgQry) {
        PageInfo<T> result = new PageInfo<>(resultList);
        result.setTotal(tempList.size());
        result.setPages(PageUtil.totalPage(tempList.size(), msgQry.getPageSize()));
        return result;
    }

    public static String getShowStatus(String attributeName, Integer operatorNum, String value) {
        return attributeName + OperatorEnum.getOperatorEnum(operatorNum).getOperator() + value;
    }

    /**
     * 展示数据显示 带百分号
     *
     * @param attributeName
     * @param value
     * @return
     */
    public static String getSpShowStatus(String attributeName, String value) {
        return attributeName + CommonConst.SymbolConst.EQUAL + value + CommonConst.SymbolConst.PER_CENT;
    }

    /**
     * 展示数据显示
     *
     * @param attributeName
     * @param value
     * @return
     */
    public static String getEqShowStatus(String attributeName, String value) {
        return attributeName + CommonConst.SymbolConst.EQUAL + value;
    }
}
