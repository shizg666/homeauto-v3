package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.model.vo.statistics.KanBanStatistics;
import com.landleaf.homeauto.center.device.model.vo.statistics.KanBanStatisticsQry;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 看板
 * </p>
 *
 * @since 2020-08-20
 */
public interface IKanBanService{


    /**
     * 设备看板统计
     * @param request
     * @return
     */
    List<KanBanStatistics> getKanbanStatistics(KanBanStatisticsQry request) ;
}
