package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
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
public class AdapterRequestMessageServiceImpl extends Observable implements AdapterRequestMessageService {


    @Autowired
    private Observer contactScreenRequestMessageHandle;
    @Autowired
    private Observer contactGatewayRequestMessageHandle;


    @PostConstruct
    protected void addObserver() {
        this.addObserver(contactScreenRequestMessageHandle);
        this.addObserver(contactGatewayRequestMessageHandle);
    }

    @Override
    public void dealMsg(AdapterMessageBaseDTO requestDTO) {
        // 异步通知执行
        setChanged();
        notifyObservers(requestDTO);
    }

}
