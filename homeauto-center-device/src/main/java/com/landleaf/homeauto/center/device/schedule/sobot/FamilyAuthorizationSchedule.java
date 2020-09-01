package com.landleaf.homeauto.center.device.schedule.sobot;

import com.landleaf.homeauto.center.device.service.mybatis.IFamilyAuthorizationService;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName FamilyAuthorizationSchedule
 * @Description: 家庭授权超时自动授权定时任务
 * @Author shizg
 * @Date 2020/9/1
 * @Version V1.0
 **/
@Component
@Slf4j
public class FamilyAuthorizationSchedule {
    @Autowired
    private IFamilyAuthorizationService iFamilyAuthorizationService;

    @Scheduled(cron = "0 0/10 * * * *")
    void checkData(){

        iFamilyAuthorizationService.timingScan(LocalDateTimeUtil.nowformat(LocalDateTimeUtil.YYYY_MM_DD_HH_MM_SS));
    }
}
