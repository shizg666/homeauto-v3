package com.landleaf.homeauto.center.adapter.handle.request;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
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
public class ContactGatewayRequestMessageHandle implements Observer {


    @Override
    @Async("adapterDealRequestMessageExecute")
    public void update(Observable o, Object arg) {
        AdapterMessageBaseDTO message = (AdapterMessageBaseDTO) arg;
        // 走下面处理逻辑
        Integer terminalType = message.getTerminalType();
        if (terminalType != null && TerminalTypeEnum.GATEWAY.getCode().intValue() == terminalType.intValue()) {

            // 发送数据





        }
    }

}
