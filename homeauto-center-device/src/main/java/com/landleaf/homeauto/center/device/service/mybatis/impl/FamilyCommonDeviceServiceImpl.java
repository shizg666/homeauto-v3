package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyCommonDeviceMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 家庭常用设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyCommonDeviceServiceImpl extends ServiceImpl<FamilyCommonDeviceMapper, FamilyCommonDeviceDO> implements IFamilyCommonDeviceService {

    @Override
    public List<FamilyCommonDeviceDO> listByFamilyId(String familyId) {
        QueryWrapper<FamilyCommonDeviceDO> commonDeviceQueryWrapper = new QueryWrapper<>();
        commonDeviceQueryWrapper.eq("family_id", familyId);
        commonDeviceQueryWrapper.isNotNull("device_id");
        commonDeviceQueryWrapper.ne("device_id", "");
        return list(commonDeviceQueryWrapper);
    }
}
