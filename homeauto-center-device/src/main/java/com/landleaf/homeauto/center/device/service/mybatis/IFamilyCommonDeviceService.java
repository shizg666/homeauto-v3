package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;

import java.util.List;

/**
 * <p>
 * 家庭常用设备表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyCommonDeviceService extends IService<FamilyCommonDeviceDO> {

    /**
     * 获取家庭常用设备ID列表
     *
     * @param familyId 家庭ID
     * @return 常用设备ID列表
     */
    List<String> getCommonDeviceIdListByFamilyId(String familyId);

}
