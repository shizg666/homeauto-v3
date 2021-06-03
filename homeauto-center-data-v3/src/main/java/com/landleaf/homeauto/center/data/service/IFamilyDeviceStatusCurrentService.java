package com.landleaf.homeauto.center.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusCurrent;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;

import java.util.List;

/**
 * <p>
 * 设备状态表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
public interface IFamilyDeviceStatusCurrentService extends IService<FamilyDeviceStatusCurrent> {

    void insertBatchDeviceStatus(List<DeviceStatusBO> requestDto);
}
