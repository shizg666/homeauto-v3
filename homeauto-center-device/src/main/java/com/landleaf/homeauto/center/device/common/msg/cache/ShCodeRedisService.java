package com.landleaf.homeauto.center.device.common.msg.cache;

import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.enums.msg.MsgTemplateEnum;
import com.landleaf.homeauto.common.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 */
@Service
public class ShCodeRedisService {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${homeauto.jg.code.redis-key-prefix}")
    private String redisCodeKeyPrefix;


    @Value("${homeauto.jg.code.mobile-has-send}")
    private String mobileHasSend;

    @Value("${homeauto.jg.code.mobile-has-send-ttl}")
    private int mobileHasSendTtl;


    public String ipDailyTimesPrefix = "ip_daily_times:";


    /**
     * 存入验证码入redis
     *
     * @param codeType
     * @param mobile
     * @param code
     * @param ttl
     * @return
     */
    public boolean hsetCodeByMobile(Integer codeType, String mobile, String code, Long ttl) {
        return redisUtil.hsetEx(redisCodeKeyPrefix + codeType, mobile, code, ttl);
    }


    /**
     * 从redis获取验证码
     *
     * @param codeType
     * @param mobile
     * @return 如果过期, 则为null
     */
    public String hgetCodeByMobile(Integer codeType, String mobile) {
        Object smsCode = redisUtil.hgetEx(redisCodeKeyPrefix + codeType, mobile);
        if (smsCode == null) {
            return null;
        }
        return String.valueOf(smsCode);
    }


    /**
     * 设置 手机 单次发送时间
     *
     * @param mobile
     * @return
     */
    public boolean setMobileHasSend(String mobile) {
        return redisUtil.set(mobileHasSend + MsgTemplateEnum.MSG_MOBILE.getType() + CommonConst.SymbolConst.COLON + mobile,
                "Hello World!",
                mobileHasSendTtl);
    }

    /**
     * 根据手机看是否有发送过
     *
     * @param mobile
     * @return
     */
    public Object getMobileHasSend(String mobile) {
        return redisUtil.get(mobileHasSend + MsgTemplateEnum.MSG_MOBILE.getType() +
                CommonConst.SymbolConst.COLON + mobile);
    }


    /**
     * 存入当前ip次数
     *
     * @param ip
     * @param times
     * @return
     */
    public boolean setIpDailyTimes(String ip, Integer times) {
        //当天
        LocalDateTime now = LocalDateTime.now();
        //下一天
        LocalDate nextDay = now.toLocalDate().plus(1, ChronoUnit.DAYS);
        //过期时间
        long ttl = ChronoUnit.SECONDS.between(now, nextDay.atStartOfDay());

        return redisUtil.set(ipDailyTimesPrefix + MsgTemplateEnum.MSG_MOBILE.getType() + CommonConst.SymbolConst.COLON + ip,
                times,
                ttl);
    }

    /**
     * 获取当前ip次数
     *
     * @param ip
     * @return
     */
    public Integer getIpDailyTime(String ip) {
        Object temp = redisUtil.get(ipDailyTimesPrefix + MsgTemplateEnum.MSG_MOBILE.getType() + CommonConst.SymbolConst.COLON + ip);
        if (temp != null) {
            return (Integer) temp;
        }
        return CommonConst.NumberConst.INT_ZERO;
    }

}
