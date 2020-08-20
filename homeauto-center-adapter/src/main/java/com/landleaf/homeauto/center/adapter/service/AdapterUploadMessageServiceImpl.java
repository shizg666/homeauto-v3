package com.landleaf.homeauto.center.adapter.service;

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
 * @author wenyilu
 */
@Service
@Slf4j
public class AdapterUploadMessageServiceImpl extends Observable implements AdapterUploadMessageService {


    @Autowired
    private Observer contactScreenAlarmUploadMessageHandle;
    @Autowired
    private Observer contactScreenSceneSetUploadMessageHandle;
    @Autowired
    private Observer contactScreenStatusUploadMessageHandle;


    @PostConstruct
    protected void addObserver() {
        this.addObserver(contactScreenAlarmUploadMessageHandle);
        this.addObserver(contactScreenSceneSetUploadMessageHandle);
        this.addObserver(contactScreenStatusUploadMessageHandle);
    }

    @Override
    public void dealMsg(AdapterMessageUploadDTO requestDTO) {

        // 异步通知执行
        setChanged();
        notifyObservers(requestDTO);
    }

}
