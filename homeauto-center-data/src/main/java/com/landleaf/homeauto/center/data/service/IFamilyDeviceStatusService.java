package com.landleaf.homeauto.center.data.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
public interface IFamilyDeviceStatusService extends IService<FamilyDeviceStatusDO> {

    /**
     * 批量插入设备状态
     *
     * @param deviceStatusBOList 设备状态信息
     */
    void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList);

}
