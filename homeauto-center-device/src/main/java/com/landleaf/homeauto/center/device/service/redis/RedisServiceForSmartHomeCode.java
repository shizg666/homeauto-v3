package com.landleaf.homeauto.center.device.service.redis;

import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.enums.msg.MsgTemplateEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 智能家居SmartHome验证码服务
 *
 * @author wenyilu
 */
@Service
public class RedisServiceForSmartHomeCode {

    @Value("#{homeAutoJgCodeProperties.redisKeyPrefix}")
    private String redisCodeKeyPrefix;

    @Value("#{homeAutoJgCodeProperties.mobileHasSend}")
    private String mobileHasSend;

    @Value("#{homeAutoJgCodeProperties.mobileHasSendTtl}")
    private int mobileHasSendTtl;

    @Value("#{homeAutoJgCodeProperties.ipDailyTimesPrefix}")
    private String ipDailyTimesPrefix;

    private RedisUtils redisUtils;

    /**
     * 存入验证码入redis
     *
     * @param codeType 验证码类型
     * @param mobile   手机号
     * @param code     验证码
     * @param ttl      失效时间
     * @return 存入结果
     */
    public boolean hsetCodeByMobile(Integer codeType, String mobile, String code, Long ttl) {
        return redisUtils.hsetEx(redisCodeKeyPrefix + codeType, mobile, code, ttl);
    }

    /**
     * 从redis获取验证码
     *
     * @param codeType 验证码类型
     * @param mobile   手机号
     * @return 验证码。如果过期, 则为null
     */
    public String hgetCodeByMobile(Integer codeType, String mobile) {
        Object smsCode = redisUtils.hgetEx(redisCodeKeyPrefix + codeType, mobile);
        if (smsCode == null) {
            return null;
        }
        return String.valueOf(smsCode);
    }

    /**
     * 设置该手机号为已经发送验证码
     *
     * @param mobile 手机号
     * @return
     */
    public boolean setMobileHasSend(String mobile) {
        String key = mobileHasSend + MsgTemplateEnum.MSG_MOBILE.getType() + CommonConst.SymbolConst.COLON + mobile;
        return redisUtils.set(key, "Hello World!", mobileHasSendTtl);
    }

    /**
     * 根据手机看是否有发送过
     *
     * @param mobile
     * @return
     */
    public Object getMobileHasSend(String mobile) {
        String key = mobileHasSend + MsgTemplateEnum.MSG_MOBILE.getType() + CommonConst.SymbolConst.COLON + mobile;
        return redisUtils.get(key);
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

        return redisUtils.set(ipDailyTimesPrefix + MsgTemplateEnum.MSG_MOBILE.getType() + CommonConst.SymbolConst.COLON + ip,
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
        Object temp = redisUtils.get(ipDailyTimesPrefix + MsgTemplateEnum.MSG_MOBILE.getType() + CommonConst.SymbolConst.COLON + ip);
        if (temp != null) {
            return (Integer) temp;
        }
        return CommonConst.NumberConst.INT_ZERO;
    }

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }
}
