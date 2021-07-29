package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.common.domain.dto.datacollect.SyncCloudDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公共 服务类
 * </p>
 *
 * @author shizg
 */
public interface ILocalDataCollectService<T> {

    /**
     * 本地数采同步数据
     * @param syncCloudDTO
     */
    void syncVollectData(SyncCloudDTO syncCloudDTO);
}
