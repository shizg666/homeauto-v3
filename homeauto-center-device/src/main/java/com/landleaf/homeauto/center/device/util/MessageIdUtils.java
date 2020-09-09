package com.landleaf.homeauto.center.device.util;

import cn.hutool.core.date.DateUtil;
import com.landleaf.homeauto.common.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 生成messageId的公共方法，区分app下发消息指令
 * @Author zhanghongbin
 * @Date 2020/8/26 15:29
 */
@Slf4j
public class MessageIdUtils {
    private final static int NUMBER_LENGTH = 20;

    public static String genMessageId() {
        return DateUtil.now() + "-" + RandomUtil.generateNumberString(NUMBER_LENGTH);
    }

    public static void main(String[] args) {

        String value = "7";

        char a = 1;

        char[] chars = new char[]{
                '0', '0', '0', '0',
                '0', '0', '0', '0',
                '0', '0', '0', '0',
                '0', '0', '0', '0'};



        char[] charsTemp = Integer.toBinaryString(Integer.parseInt(value)).toCharArray();


        for (int i = 0; i < charsTemp.length; i++) {

            System.out.println(charsTemp[i]);

        }


//        for (int i = 15,j=0 ; i >= 15 - j,j< charsTemp.length; i--,j++) {
//
//            for (int j = 0; j < charsTemp.length; j++) {
//
//                chars[i] = charsTemp[j];
//            }
//        }
//
//        for (int i = 0; i < chars.length; i++) {
//
//            System.out.println(chars[i]);
//
//        }


    }

}
