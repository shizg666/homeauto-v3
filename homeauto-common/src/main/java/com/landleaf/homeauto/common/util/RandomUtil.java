package com.landleaf.homeauto.common.util;

import java.util.Random;

public class RandomUtil {

	 public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
     public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
     public static final String numberChar = "0123456789";

     /**
      * 返回一个定长的随机字符串(只包含大小写字母、数字)
      *
      * @param length 随机字符串长度
      * @return 随机字符串
      */
     public static String generateString(int length) {
             StringBuffer sb = new StringBuffer();
             Random random = new Random();
             for (int i = 0; i < length; i++) {
                     sb.append(allChar.charAt(random.nextInt(allChar.length())));
             }
             return sb.toString();
     }

     /**
      * 返回一个定长的随机纯字母字符串(只包含大小写字母)
      *
      * @param length 随机字符串长度
      * @return 随机字符串
      */
     public static String generateMixString(int length) {
             StringBuffer sb = new StringBuffer();
             Random random = new Random();
             for (int i = 0; i < length; i++) {
                     sb.append(allChar.charAt(random.nextInt(letterChar.length())));
             }
             return sb.toString();
     }

     /**
      * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
      *
      * @param length 随机字符串长度
      * @return 随机字符串
      */
     public static String generateLowerString(int length) {
             return generateMixString(length).toLowerCase();
     }

     /**
      * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
      *
      * @param length 随机字符串长度
      * @return 随机字符串
      */
     public static String generateUpperString(int length) {
             return generateMixString(length).toUpperCase();
     }

     /**返回一个定长的随机数字字符串(只包含数字)
     * @param length
     * @return
     */
    public static String generateNumberString(int length)
     {
    	StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
                sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
     }
     /**获得指定范围内的随机数
 	 * @param min
 	 * @param max
 	 * @return
 	 */
 	public static int getRadomNum(int min, int max) {
 		Random random = new Random();//创建不带种子的随机数
 		int s = random.nextInt(max) % (max - min + 1) + min;

 		return s;
 	} 
}
