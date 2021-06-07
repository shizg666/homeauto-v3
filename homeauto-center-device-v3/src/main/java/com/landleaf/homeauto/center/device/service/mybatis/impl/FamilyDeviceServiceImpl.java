package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.familydevice.FamilyDevice;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-06-04
 */
@Service
public class FamilyDeviceServiceImpl extends ServiceImpl<FamilyDeviceMapper, FamilyDevice> implements IFamilyDeviceService {

    @Override
    public void add(FamilyDevice familyDevice) {

    }
}
