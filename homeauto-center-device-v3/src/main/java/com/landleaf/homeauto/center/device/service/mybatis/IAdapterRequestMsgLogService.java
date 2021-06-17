package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.AdapterRequestMsgLog;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;

import java.util.List;

/**
 * <p>
 * 控制命令操作日志 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
public interface IAdapterRequestMsgLogService extends IService<AdapterRequestMsgLog> {

    void saveRecord(AdapterMessageBaseDTO message, String toJSONString);

    void updateRecord(AdapterMessageAckDTO message);

    void updateRecordRetry(String messageId, Long familyId);

    void updateRecordRetryResult(AdapterMessageAckDTO message);

    List<AdapterRequestMsgLog> findRetryRecord();
}
