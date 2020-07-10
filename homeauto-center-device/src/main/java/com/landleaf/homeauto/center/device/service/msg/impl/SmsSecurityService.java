package com.landleaf.homeauto.center.device.service.msg.impl;

import com.landleaf.homeauto.center.device.common.msg.cache.ShCodeRedisService;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.RemoteHostDetailContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Lokiy
 * @date 2019/11/4 11:03
 * @description:
 */
@Component
public class SmsSecurityService {

    @Autowired
    private ShCodeRedisService shCodeRedisService;


    @Value("${homeauto.jg.code.ip-daily-times-limit}")
    private String limitTimes;


    /**
     * 检验手机是否有验证码发送过,有 则需等过期  没有 则直接存入
     *
     * @param mobile
     */
    public void checkMobileHasSend(String mobile) {
        Object mobileHasSend = shCodeRedisService.getMobileHasSend(mobile);
        if (mobileHasSend != null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()), "该手机当前发送验证短信过频繁,请稍后再试");
        }
        shCodeRedisService.setMobileHasSend(mobile);
    }


    /**
     * 校验同一ip当天发送次数
     *
     * @param ip
     */
    public Integer checkIpDailyHasSend(String ip) {
        //获取发送源ip
        ip = ip == null ? RemoteHostDetailContextUtil.getIp() : ip;
        Integer times = shCodeRedisService.getIpDailyTime(ip);
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

        shCodeRedisService.setIpDailyTimes(ip, times + 1);
    }


}
