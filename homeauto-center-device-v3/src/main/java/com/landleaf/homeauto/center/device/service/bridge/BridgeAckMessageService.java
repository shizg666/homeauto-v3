package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;

/**
 * 响应消息处理
 *
 * @author zhanghongbin
 */
public interface BridgeAckMessageService {



    /**
     * 处理数据
     *
     * @param ackDTO
     */
    void dealMsg(AdapterMessageAckDTO ackDTO);


}
