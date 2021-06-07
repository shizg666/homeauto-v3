package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.familydevice.FamilyDevice;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-06-04
 */
public interface IFamilyDeviceService extends IService<FamilyDevice> {

    void add(FamilyDevice familyDevice);
}
