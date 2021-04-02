package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.DeviceRepairGuide;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceGuideQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceGuideVO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceRepairGuideDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;

import java.util.List;

/**
 * <p>
 * 设备维修指南 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-23
 */
public interface IDeviceRepairGuideService extends IService<DeviceRepairGuide> {

    BasePageVO<DeviceGuideVO> page(DeviceGuideQryDTO request);

    void addOrUpdate(DeviceRepairGuideDTO request);

    void add(DeviceRepairGuideDTO request);

    void update(DeviceRepairGuideDTO request);

    /**
     * 获取故障类型
     * @return
     */
    List<SelectedIntegerVO> getTypes();
}
