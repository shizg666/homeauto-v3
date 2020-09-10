package com.landleaf.homeauto.center.device.service.bridge;

import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterSceneControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceStatusReadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;

/**
 * @Description 提供给APP相关操作接口
 * @Author zhanghongbin
 * @Date 2020/8/25 16:11
 */
public interface IAppService {

    /**
     * 场景控制
     * @return
     */
    AdapterSceneControlAckDTO familySceneControl(AdapterSceneControlDTO requestDTO);

    /**
     * 设备控制
     * @return
     */
    AdapterDeviceControlAckDTO deviceWriteControl(AdapterDeviceControlDTO deviceControlDTO);

    /**
     * 状态读取
     * @return
     */
    AdapterDeviceStatusReadAckDTO deviceStatusRead(AdapterDeviceStatusReadDTO requestDTO);

    /**
     * 配置更新
     * @return
     */
    AdapterConfigUpdateAckDTO configUpdate(AdapterConfigUpdateDTO requestDTO);


    /**
     * 配置更新，不返回消息
     * @return
     */
    void configUpdateConfig(AdapterConfigUpdateDTO requestDTO);
}
