package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.DeviceRepairGuide;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceGuideQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceGuideVO;

import java.util.List;

/**
 * <p>
 * 设备维修指南 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-23
 */
public interface DeviceRepairGuideMapper extends BaseMapper<DeviceRepairGuide> {

    List<DeviceGuideVO> page(DeviceGuideQryDTO request);

}
