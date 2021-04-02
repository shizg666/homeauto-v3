package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.common.domain.dto.log.OperationLog;

public interface LogService {

    void saveLog(OperationLog operationLog);

    int getType();

}
