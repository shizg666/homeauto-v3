package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

/**
 * 内部服务向下消息的处理器
 *
 * @author wenyilu
 */
@Service
@Slf4j
public class BridgeAckMessageServiceImpl extends Observable implements BridgeAckMessageService {

    @Autowired
    private Observer contactScreenGatewayMessageHandle;

    @PostConstruct
    protected void addObserver() {
        this.addObserver(contactScreenGatewayMessageHandle);
    }


    @Override
    public void dealMsg(AdapterMessageAckDTO requestDTO) {
        // 异步通知执行
        // 封装为adapter所接收的数据类型


        setChanged();
        notifyObservers(requestDTO);
    }

}
