package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.RepairAddReqDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.callback.SobotCallBackContentDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReport;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
public interface IHomeautoFaultReportService extends IService<HomeAutoFaultReport> {

    List<AppRepairDetailDTO> listRepairs(String familyId);

    void createRepair(RepairAddReqDTO requestBody, String userId);

    /**
     * 获取报修详情
     * @param repairId  记录Id
     * @return
     */
    AppRepairDetailDTO getRepairDetail(String repairId);

    /**
     * 接收到消息处理通知，更新状态
     * @param tickets
     */
    void updateStatus(List<SobotCallBackContentDTO> tickets);

    /**
     * 操作状态为已解决
     * @param repairId   记录ID
     * @param userId     用户
     */
    void completed(String repairId, String userId);
}
