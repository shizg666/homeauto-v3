package com.landleaf.homeauto.common.enums;

import cn.hutool.core.comparator.CompareUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lokiy
 * @date 2019/8/23 9:30
 * @description: 操作符
 */
public enum OperatorEnum {

    /**
     * 小于等于
     */
    ELT(-2,"≤"),
    /**
     * 小于
     */
    LT(-1,"<"),
    /**
     * 等于
     */
    EQ(0,"="),
    /**
     * 大于
     */
    GT(1,">"),
    /**
     * 大于等于
     */
    EGT(2,"≥"),
    ;

    private Integer operatorNum;
    private String operator;


    OperatorEnum(Integer operatorNum, String operator ) {
        this.operatorNum = operatorNum;
        this.operator = operator;
    }

    public Integer getOperatorNum() {
        return operatorNum;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setOperatorNum(Integer operatorNum) {
        this.operatorNum = operatorNum;
    }

    private static Map<Integer, OperatorEnum>  OPERATOR_MAP = new ConcurrentHashMap<>();
    static {
        for (OperatorEnum oe : values()) {
            OPERATOR_MAP.put(oe.operatorNum, oe);
        }
    }

    public static OperatorEnum getOperatorEnum(Integer operatorNum){
        return OPERATOR_MAP.get(operatorNum);
    }

    private static final String PATTERN = "^[\\+\\-]?[\\d]+(\\.[\\d]+)?$";

    /**
     * 用于两数比较
     * @param operatorNum
     * @param v1    待比较数
     * @param v2    设置数
     * @return
     */
    public static boolean compare(Integer operatorNum, String v1, String v2){
        OperatorEnum operatorEnum = getOperatorEnum(operatorNum);
        if(StringUtils.isBlank(v1) || StringUtils.isBlank(v2)){
            return false;
        }
        if(!isNumber(v1)
                || !isNumber(v2)){
            return v1.equals(v2);
        }else {
            Double d1 = Double.parseDouble(v1);
            Double d2 = Double.parseDouble(v2);
            switch (operatorEnum) {
                case ELT:
                    return CompareUtil.compare(d1, d2) <= 0;
                case LT:
                    return CompareUtil.compare(d1, d2) < 0;
                case EQ:
                    return d1.equals(d2);
                case GT:
                    return CompareUtil.compare(d1, d2) > 0;
                case EGT:
                    return CompareUtil.compare(d1, d2) >= 0;
                default:
                    return false;
            }
        }
    }


    public static boolean isNumber(String content) {
        if(StringUtils.isBlank(content)){
            return false;
        }
        Pattern r = Pattern.compile(PATTERN);
        Matcher m = r.matcher(content);
        return m.matches();
    }
}
