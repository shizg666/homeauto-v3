package com.landleaf.homeauto.contact.screen.client.util;

import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * msgid的辅助类
 *
 */
@Component
public final class MessageIdUtil {

    @Autowired
    private RedisUtils redisUtil;

    public int getMsgId(String familyCode, String screenMac) {
        long value = redisUtil.hincr(RedisCacheConst.CONTACT_SCREEN_SCREEN_MESSAGE_ID_INCR, familyCode.concat("_").concat(screenMac), 1L);
        return (int) (value % 65536);
    }
}
