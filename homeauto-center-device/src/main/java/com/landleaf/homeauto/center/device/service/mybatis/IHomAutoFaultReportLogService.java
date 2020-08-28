package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReport;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReportLog;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface IHomAutoFaultReportLogService extends IService<HomeAutoFaultReportLog> {

    List<HomeAutoFaultReportLog> getLogsByTikcketId(String ticketId);

    void saveOperate( String ticketid, Integer ticket_status, String reply_content);
}
