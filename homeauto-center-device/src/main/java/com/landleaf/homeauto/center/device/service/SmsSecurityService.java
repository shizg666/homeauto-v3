package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.service.redis.RedisServiceForSmartHomeCode;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.exception.JgException;
import com.landleaf.homeauto.common.util.RemoteHostDetailContextUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * 短信安全服务类
 *
 * @author Lokiy
 * @date 2019/11/4 11:03
 */
@Component
public class SmsSecurityService {

    private RedisServiceForSmartHomeCode redisServiceForSmartHomeCode;

    @Value("#{homeAutoJgCodeProperties.ipDailyTimesLimit}")
    private String limitTimes;

    /**
     * 检验手机是否有验证码发送过, 有则等待，否则直接存入
     *
     * @param mobile 手机号
     */
    public void checkMobileHasSend(String mobile) {
        Object mobileHasSend = redisServiceForSmartHomeCode.getMobileHasSend(mobile);
        if (!Objects.isNull(mobileHasSend)) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()), "该手机当前发送验证短信过频繁,请稍后再试");
        }
        redisServiceForSmartHomeCode.setMobileHasSend(mobile);
    }

    /**
     * 校验验证码是否还有效
     *
     * @param codeType 短信类型
     * @param mobile   手机号
     */
    public void checkCodeValid(Integer codeType, String mobile) {
        String code = redisServiceForSmartHomeCode.hgetCodeByMobile(codeType, mobile);
        if (code != null) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_MC_JG_CODE_NOT_EXPIRE);
        }
    }

    /**
     * 校验同一ip当天发送次数
     *
     * @param ip
     */
    public Integer checkIpDailyHasSend(String ip) {
        //获取发送源ip
        ip = ip == null ? RemoteHostDetailContextUtil.getIp() : ip;
        Integer times = redisServiceForSmartHomeCode.getIpDailyTime(ip);
        if (times >= Integer.parseInt(limitTimes)) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()), "当前设备单天发送频率已超过" + limitTimes + "次,请明天再试！");
        }
//        shCodeRedisService.setIpDailyTimes(ip, times + 1);
        return times;
    }

    /**
     * 发送成功 存入次数
     *
     * @param ip
     */
    public void checkIpDailyHasSendAfter(String ip, Integer times) {
        //获取发送源ip
        ip = ip == null ? RemoteHostDetailContextUtil.getIp() : ip;

        redisServiceForSmartHomeCode.setIpDailyTimes(ip, times + 1);
    }

    @Autowired
    public void setRedisServiceForSmartHomeCode(RedisServiceForSmartHomeCode redisServiceForSmartHomeCode) {
        this.redisServiceForSmartHomeCode = redisServiceForSmartHomeCode;
    }
}
