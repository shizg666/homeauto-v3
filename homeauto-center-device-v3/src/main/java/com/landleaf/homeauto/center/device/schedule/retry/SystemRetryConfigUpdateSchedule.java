package com.landleaf.homeauto.center.device.schedule.retry;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.model.domain.AdapterRequestMsgLog;
import com.landleaf.homeauto.center.device.service.bridge.retry.ISystemConfigUpdateRetryService;
import com.landleaf.homeauto.center.device.service.mybatis.IAdapterRequestMsgLogService;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 重试2个小时以前的且失败次数小于3的配置更新通知
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class SystemRetryConfigUpdateSchedule {

    @Autowired
    private IAdapterRequestMsgLogService adapterRequestMsgLogService;

    @Autowired
    private ISystemConfigUpdateRetryService systemConfigUpdateRetryService;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void refreshTicketToken() {
        log.info("[定时任务]-重试配置更新通知");
        List<AdapterRequestMsgLog> errorData = adapterRequestMsgLogService.findRetryRecord();
        if (!CollectionUtils.isEmpty(errorData)) {
            log.info("[定时任务]-重试配置更新通知,条数:{}", errorData.size());
            for (AdapterRequestMsgLog errorDatum : errorData) {
                String content = errorDatum.getContent();
                AdapterConfigUpdateDTO adapterConfigUpdateDTO = JSON.parseObject(content, AdapterConfigUpdateDTO.class);
                adapterConfigUpdateDTO.setTime(System.currentTimeMillis());
                systemConfigUpdateRetryService.retryConfigUpdate(adapterConfigUpdateDTO);
            }
        }
    }

}
