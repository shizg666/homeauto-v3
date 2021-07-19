package com.landleaf.homeauto.center.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceEnergyDay;
import com.landleaf.homeauto.center.data.domain.FamilyDevicePowerHistory;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.HistoryQryDTO2;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.domain.bo.FamilyDevicePowerDO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备功耗表 服务类
 * </p>
 *
 */
public interface IFamilyDevicePowerHistoryService extends IService<FamilyDevicePowerHistory> {
    /**
     * 批量插入设备状态
     *
     * @param powerDOS 设备功耗状态信息
     */
    void insertBatchDevicePower(List<FamilyDevicePowerDO> powerDOS);

    BasePageVO<FamilyDeviceStatusHistory> getStatusByFamily(HistoryQryDTO2 historyQryDTO);

    /**
     * 获取昨天基础值
     * @return
     */
    Map<Long, List<FamilyDevicePowerHistory>> getGlcPowerYesterday(String startTime,String endTime);

    Map<Long,List<FamilyDevicePowerHistory>> getGlvPowerYesterday(String startTime,String endTime);
}
