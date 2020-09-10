package com.landleaf.homeauto.center.device.service.bridge.retry;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

/**
 * 处理adapter发给系统重试的ack消息
 *
 * @author zhanghongbin
 */
@Service
@Slf4j
public class SystemConfigUpdateRetryAckServiceImpl extends Observable implements ISystemConfigUpdateRetryAckService {

    @Autowired
    private Observer adapterSystemRetryAckMessageHandle;

    @PostConstruct
    protected void addObserver() {
        this.addObserver(adapterSystemRetryAckMessageHandle);
    }


    @Override
    public void dealMsg(AdapterMessageAckDTO adapterMessageAckDTO) {
        // 异步通知执行
        // 封装为adapter所接收的数据类型
        log.info("[收到系统重试请求响应]消息编码:{}", adapterMessageAckDTO.getMessageId());
        setChanged();
        notifyObservers(adapterMessageAckDTO);
    }

}
