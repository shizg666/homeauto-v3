package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceHavcDO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.error.DeviceErrorVO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
public interface IHomeAutoFaultDeviceHavcService extends IService<HomeAutoFaultDeviceHavcDO> {

    /**
     * 批量保存暖通故障
     * @param data
     */
    void batchSave(List<HomeAutoFaultDeviceHavcDTO> data);

    /**
     * 设备故障信息获取
     * @param request
     * @return
     */
    List<DeviceErrorVO> getListDeviceError(DeviceErrorQryDTO request);
}
