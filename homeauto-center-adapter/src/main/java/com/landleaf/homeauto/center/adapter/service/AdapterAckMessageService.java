package com.landleaf.homeauto.center.adapter.service;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttBaseDTO;

/**
 * 响应消息处理
 *
 * @author wenyilu
 */
public interface AdapterAckMessageService {



    /**
     * 处理数据
     *
     * @param requestDTO
     */
    void dealMsg(AdapterMessageAckDTO requestDTO);
}
