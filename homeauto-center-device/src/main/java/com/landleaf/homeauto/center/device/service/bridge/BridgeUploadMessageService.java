package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;

/**
 * 上报消息处理
 *
 * @author zhanghongbin
 */
public interface BridgeUploadMessageService {


    /**
     * 处理数据
     *
     * @param uploadDTO
     */
    void dealMsg(AdapterMessageUploadDTO uploadDTO);
}
