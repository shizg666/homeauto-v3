package com.landleaf.homeauto.center.device.util;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

/**
 * @Description 判断故障超过上下限的方法
 * @Author zhanghongbin
 * @Date 2020/9/7 18:06
 */
@Slf4j
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
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {

            System.out.println(chars[i]);
            sb.append(chars[i]);

        }
        log.info("转换暖通故障值为:{}", sb.toString());
        return chars;
    }

    /**
     * 将一个int数字转换为二进制的字符串形式。
     * @param num 需要转换的int类型数据
     * @param digits 要转换的二进制位数，位数不足则在前面补0
     * @return 二进制的字符串形式
     */
    public static char[] toBinary(int num, int digits) {
        int value = 1 << digits | num;
        String bs = Integer.toBinaryString(value); //0x20 | 这个是为了保证这个string长度是6位数
        String substring = bs.substring(1);
        return substring.toCharArray();
    }

    public static void main(String[] args) {

        System.out.println(isValueError("120","100","200"));

        char[] chars = get16chars("256");
        char[] chars1 = toBinary(12, 16);
        System.out.println(chars1);
    }

}
