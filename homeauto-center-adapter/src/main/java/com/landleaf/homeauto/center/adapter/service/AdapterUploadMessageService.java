package com.landleaf.homeauto.center.adapter.service;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;

/**
 * 上报消息处理
 *
 * @author wenyilu
 */
public interface AdapterUploadMessageService {


    /**
     * 处理数据
     *
     * @param requestDTO
     */
    void dealMsg(AdapterMessageUploadDTO requestDTO);
}
