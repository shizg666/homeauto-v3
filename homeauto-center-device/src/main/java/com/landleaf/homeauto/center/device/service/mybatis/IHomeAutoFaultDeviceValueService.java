package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceValueDO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceValueDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
public interface IHomeAutoFaultDeviceValueService extends IService<HomeAutoFaultDeviceValueDO> {

    /**
     * 批量保存
     * @param data
     */
    void batchSave(List<HomeAutoFaultDeviceValueDTO> data);
}
