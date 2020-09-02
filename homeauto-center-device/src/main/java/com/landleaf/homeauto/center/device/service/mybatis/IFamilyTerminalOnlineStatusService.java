package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalOnlineStatusDO;

/**
 * <p>
 * 终端在线离线状态记录表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-01
 */
public interface IFamilyTerminalOnlineStatusService extends IService<FamilyTerminalOnlineStatusDO> {

    FamilyTerminalOnlineStatusDO getLatestRecord(String familyId, String mac, String terminalId);

    /**
     * 更新家庭终端的上下线状态
     * @param familyId     家庭id
     * @param terminalMac  终端mac
     * @param status       在线状态
     */
    void updateTerminalOnLineStatus(String familyId, String terminalMac, Integer status);
}
