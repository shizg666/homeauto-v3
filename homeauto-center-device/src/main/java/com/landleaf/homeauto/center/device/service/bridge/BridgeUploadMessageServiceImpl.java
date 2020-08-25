package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Observable;
import java.util.Observer;

/**
 * 上报消息处理器
 *
 * @author zhanghongbin
 */
@Service
@Slf4j
public class BridgeUploadMessageServiceImpl extends Observable implements BridgeUploadMessageService {


//    @Autowired
//    private Observer contactScreenAlarmUploadMessageHandle;
//    @Autowired
//    private Observer contactScreenSceneSetUploadMessageHandle;
    @Autowired
    private Observer adapterStatusUploadMessageHandle;


    @PostConstruct
    protected void addObserver() {
//        this.addObserver(contactScreenAlarmUploadMessageHandle);
//        this.addObserver(contactScreenSceneSetUploadMessageHandle);
        this.addObserver(adapterStatusUploadMessageHandle);
    }

    @Override
    public void dealMsg(AdapterMessageUploadDTO uploadDTO) {
       log.info("[收到mq上报消息]消息编码:{}",uploadDTO.getMessageId());
        // 异步通知执行
        setChanged();
        notifyObservers(uploadDTO);
    }

}
