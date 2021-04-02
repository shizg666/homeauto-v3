package com.landleaf.homeauto.center.device.service.bridge.retry;

import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterSceneControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceStatusReadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterSceneControlDTO;

/**
 *  系统重试发送配置更新失败的命令
 * @author wenyilu
 */
public interface ISystemConfigUpdateRetryService {


    /**
     * 配置更新
     * @return
     */
    void retryConfigUpdate(AdapterConfigUpdateDTO requestDTO);
}
