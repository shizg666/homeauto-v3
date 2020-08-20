package com.landleaf.homeauto.center.adapter.handle.ack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

/**
 * 将发送到网关通讯模块数据处理类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class ContactScreenGatewayMessageHandle implements Observer {


    @Override
    @Async("adapterDealRequestMessageExecute")
    public void update(Observable o, Object arg) {
    }

}
