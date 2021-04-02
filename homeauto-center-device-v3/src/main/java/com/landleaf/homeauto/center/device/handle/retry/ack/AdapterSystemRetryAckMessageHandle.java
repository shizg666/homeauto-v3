package com.landleaf.homeauto.center.device.handle.retry.ack;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.service.mybatis.IAdapterRequestMsgLogService;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

/**
 * 监听Adapter到系统重试的响应ack
 */
@Component
@Slf4j
public class AdapterSystemRetryAckMessageHandle implements Observer {

    @Autowired
    private IAdapterRequestMsgLogService adapterRequestMsgLogService;

    @Override
    @Async(value = "bridgeDealAckMessageExecute")
    public void update(Observable o, Object arg) {

        AdapterMessageAckDTO message = (AdapterMessageAckDTO) arg;
        // 走下面处理逻辑
          log.info("收到重试请求响应:{}", JSON.toJSONString(message));
            // 更新日志

            try {
                //修改重试记录
                adapterRequestMsgLogService.updateRecordRetryResult(message);

            } catch (Exception e) {
                log.error("更新失败记录重试响应记录异常，装作没看见....");
            }

    }

}