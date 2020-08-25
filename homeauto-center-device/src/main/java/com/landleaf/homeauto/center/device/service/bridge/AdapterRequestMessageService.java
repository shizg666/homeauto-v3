package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;

/**
 * 内部服务向下消息的处理器
 *
 * @author wenyilu
 */
public interface AdapterRequestMessageService {


    /**
     * 处理数据
     *
     * @param requestDTO
     */
    void dealMsg(AdapterMessageBaseDTO requestDTO);
}
