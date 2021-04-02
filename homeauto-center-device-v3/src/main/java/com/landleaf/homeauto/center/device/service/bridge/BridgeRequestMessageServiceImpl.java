package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

/**
 * app向adapter下发消息请求处理
 *
 * @author zhanghongbin
 */
@Service
@Slf4j
public class BridgeRequestMessageServiceImpl extends Observable implements BridgeRequestMessageService {



    @Autowired
    private Observer adapterRequestMessageHandle;

    @PostConstruct
    protected void addObserver() {
        this.addObserver(adapterRequestMessageHandle);
    }



    @Override
    public void dealMsg(AdapterMessageBaseDTO requestDTO) {
        preCheck(requestDTO);
        // 异步通知执行
        log.info("[收到app请求控制消息]消息编码:{}",requestDTO.getMessageId());
        setChanged();
        notifyObservers(requestDTO);
    }

    private void preCheck(AdapterMessageBaseDTO requestDTO) {
        Assert.notNull(requestDTO.getTerminalMac(),String.format("%s,[家庭ID]:%s","终端mac必须",requestDTO.getFamilyId()));
        Assert.notNull(requestDTO.getFamilyCode(),String.format("%s,[家庭ID]:%s","家庭编码必须",requestDTO.getFamilyId()));
        Assert.notNull(requestDTO.getFamilyId(),String.format("%s,[家庭ID]:%s","家庭id必须",requestDTO.getFamilyId()));
        Assert.notNull(requestDTO.getMessageName(),String.format("%s,[家庭ID]:%s","消息类型必须",requestDTO.getFamilyId()));
        Assert.notNull(requestDTO.getMessageId(),String.format("%s,[家庭ID]:%s","消息编码必须",requestDTO.getFamilyId()));
    }

}
