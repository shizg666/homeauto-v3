package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFaultDeviceLinkDO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceLinkDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
public interface IHomeAutoFaultDeviceLinkService extends IService<HomeAutoFaultDeviceLinkDO> {

    void batchSave(List<HomeAutoFaultDeviceLinkDTO> data);
}
