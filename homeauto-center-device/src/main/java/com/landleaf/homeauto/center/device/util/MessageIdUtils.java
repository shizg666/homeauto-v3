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
    public static String genMessageId(){
        return DateUtil.now()+ "-" + RandomUtil.generateNumberString(NUMBER_LENGTH);
    }

    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            log.info(genMessageId());
        }
    }
}
