package com.landleaf.homeauto.common.util;


import java.math.BigDecimal;

/**
 * @ClassName BigDecimalUtil
 * @Description: BigDecimal工具类
 * @Author shizg
 * @Date 2020/6/2
 * @Version V1.0
 **/
public class BigDecimalUtil {
    public static final int DEF_SCALE = 2;
    private BigDecimalUtil(){

    }

    /**
     * 加法 尽量用字符串的形式初始化,否则会有精度问题故只提供字符串入参
     * @param data1
     * @param data2
     * @return
     */
    public static BigDecimal add(String data1, String data2){
        return add(new BigDecimal(data1),new BigDecimal(data2));
    }

    /**
     * 加法
     * @param data1
     * @param data2
     * @return
     */
    public static BigDecimal add(BigDecimal data1, BigDecimal data2){
        return data1.add(data2);
    }

    /**
     * 减
     * @param data1
     * @param data2
     * @return
     */
    public static BigDecimal subtract(String data1, String data2){
        return subtract(new BigDecimal(data1),new BigDecimal(data2));
    }

    /**
     * 减法
     * @param data1
     * @param data2
     * @return
     */
    public static BigDecimal subtract(BigDecimal data1, BigDecimal data2){
        return data1.subtract(data2);
    }

    /**
     * 乘法
     * @param data1
     * @param data2
     * @return
     */
    public static BigDecimal multiply(String data1, String data2){
        return multiply(new BigDecimal(data1),new BigDecimal(data2));
    }

    /**
     * 乘法
     * @param data1
     * @param data2
     * @return
     */
    public static BigDecimal multiply(BigDecimal data1, BigDecimal data2){
        return data1.multiply(data2);
    }

    /**
     * 除法
     * @param data1 除数
     * @param data2 被除数
     * @param scale 精度范围
     * @return
     */
    public static BigDecimal divide(String data1, String data2,int scale) throws IllegalAccessException {
        return divide(new BigDecimal(data1),new BigDecimal(data2),scale);
    }

    /**
     * 除法 四舍五入
     * @param data1 除数
     * @param data2 被除数
     * @param scale 精确范围
     * @return
     */
    public static BigDecimal divide(BigDecimal data1, BigDecimal data2,int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        return data1.divide(data2,scale,BigDecimal.ROUND_HALF_UP);
    }


    public static void main(String[] args) throws IllegalAccessException {
        BigDecimal a1 = new BigDecimal("12");
        BigDecimal a2 = new BigDecimal("12");
        BigDecimal result = a1.add(a1);
        BigDecimal decimal = divide("4","0",2);
        System.out.println("args = [" + decimal + "]");
//        BigDecimal result2 = BigDecimalUtil.add(a1,a2);
    }

}
