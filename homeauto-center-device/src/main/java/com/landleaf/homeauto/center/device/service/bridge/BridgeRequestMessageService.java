package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;

/**
 * app下发消息到adapter
 *
 * @author zhanghongbin
 */
public interface BridgeRequestMessageService {


    /**
     * 处理数据
     *
     * @param requestDTO
     */
    void dealMsg(AdapterMessageBaseDTO requestDTO);
}
