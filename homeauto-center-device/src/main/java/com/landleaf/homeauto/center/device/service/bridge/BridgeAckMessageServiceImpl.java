package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

/**
 * 处理adapter发给app的ack消息
 *
 * @author zhanghongbin
 */
@Service
@Slf4j
public class BridgeAckMessageServiceImpl extends Observable implements BridgeAckMessageService {

    @Autowired
    private Observer adapterAckMessageHandle;

    @PostConstruct
    protected void addObserver() {
        this.addObserver(adapterAckMessageHandle);
    }


    @Override
    public void dealMsg(AdapterMessageAckDTO adapterMessageAckDTO) {
        // 异步通知执行
        // 封装为adapter所接收的数据类型
        log.info("[收到app请求控制消息]消息编码:{}",adapterMessageAckDTO.getMessageId());
        setChanged();
        notifyObservers(adapterMessageAckDTO);
    }

}
