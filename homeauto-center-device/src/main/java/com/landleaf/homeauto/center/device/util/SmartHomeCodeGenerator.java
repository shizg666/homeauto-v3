package com.landleaf.homeauto.center.device.util;

import java.util.Random;

/**
 * 智能家居验证码生成器
 *
 * @author wenyilu
 */
public class SmartHomeCodeGenerator {

    /**
     * 生成6位随机验证码
     *
     * @return 验证码
     */
    public static String codeRandom() {
        return String.valueOf(new Random().nextInt(899999) + 100_000);
    }

}
