package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalOnlineStatusDO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 终端在线离线状态记录表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-01
 */
public interface FamilyTerminalOnlineStatusMapper extends BaseMapper<FamilyTerminalOnlineStatusDO> {

    FamilyTerminalOnlineStatusDO getLatestRecord(@Param("familyId") String familyId, @Param("mac") String mac, @Param("terminalId")String terminalId);
}
