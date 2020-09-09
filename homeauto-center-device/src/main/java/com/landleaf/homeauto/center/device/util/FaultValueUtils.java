package com.landleaf.homeauto.center.device.util;

/**
 * @Description 判断故障超过上下限的方法
 * @Author zhanghongbin
 * @Date 2020/9/7 18:06
 */
public class FaultValueUtils {

    public static Integer HVAC_INT_MAX = 65536;
    public static Integer HVAC_INT_MIN = 0;

    public static Integer HVAC_ERROR_STRING_LENGTH = 16;

    /**
     * @param current
     * @param min
     * @param max
     * @return 如果是故障，则返回true
     */
    public static boolean isValueError(String current,String min,String max){
        boolean flag = false;
        try {


            Double currentD = Double.parseDouble(current);
            Double minD = Double.parseDouble(min);
            Double maxD = Double.parseDouble(max);

            if (currentD - maxD >0 || currentD - minD <0){//比最大值大，比最小值小
                flag = true;
            }

        }catch (Exception e){

            e.printStackTrace();

            return flag;
        }



        return flag;

    }

    /**
     * 数字型字符串转换为16位二进制char[]
     * @param value
     * @return
     */
    public static char[] get16chars(String value){
        char[] chars = new char[]{
                '0', '0', '0', '0',
                '0', '0', '0', '0',
                '0', '0', '0', '0',
                '0', '0', '0', '0'};



        char[] charsTemp = Integer.toBinaryString(Integer.parseInt(value)).toCharArray();

        for (int i = 15,j=0 ; j < charsTemp.length ; i--,j++) {

            chars[i] = charsTemp[j];
        }

        System.out.println("=======================");

        for (int i = 0; i < chars.length; i++) {

            System.out.println(chars[i]);

        }
        return chars;
    }
    public static void main(String[] args) {
        System.out.println(isValueError("120","100","200"));

        get16chars("255");
    }
}
