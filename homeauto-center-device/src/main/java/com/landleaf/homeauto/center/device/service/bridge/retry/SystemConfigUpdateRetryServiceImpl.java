package com.landleaf.homeauto.center.device.service.bridge.retry;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

/**
 * 系统重试发送失败的命令
 *
 * @author wenyilu
 */
@Service
@Slf4j
public class SystemConfigUpdateRetryServiceImpl extends Observable implements ISystemConfigUpdateRetryService {


    @Autowired
    private Observer adapterSystemRetryConfigUpdateMessageHandle;

    @PostConstruct
    protected void addObserver() {
        this.addObserver(adapterSystemRetryConfigUpdateMessageHandle);
    }


    public void dealMsg(AdapterMessageBaseDTO requestDTO) {
        // 异步通知执行
        log.info("[收到系统重试请求控制消息]消息编码:{}", requestDTO.getMessageId());
        setChanged();
        notifyObservers(requestDTO);
    }


    @Override
    public void retryConfigUpdate(AdapterConfigUpdateDTO requestDTO) {

        dealMsg(requestDTO);

    }


}
