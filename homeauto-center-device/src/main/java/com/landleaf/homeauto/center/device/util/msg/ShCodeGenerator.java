package com.landleaf.homeauto.center.device.util.msg;

import java.util.Random;

/**
 *
 */
public class ShCodeGenerator {


    /**
     * 生成6位随机验证码
     *
     * @return
     */
    public static String codeRandom() {
        return String.valueOf(new Random().nextInt(899999) + 100000);
    }


}
