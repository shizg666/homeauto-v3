package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceHavcDO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
public interface HomeAutoFaultDeviceHavcMapper extends BaseMapper<HomeAutoFaultDeviceHavcDO> {

    /**
     * 查询暖通设备故障列表
     * @param request
     * @return
     */
    List<DeviceErrorVO> getListDeviceError(DeviceErrorQryDTO request);

}
