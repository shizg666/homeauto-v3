package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.familydevice.FamilyDevice;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;

    @Override
    public void addFamilyDevice(HomeAutoFamilyDO familyDO) {
        FamilyDevice familyDevice = FamilyDevice.builder().familyId(familyDO.getId()).familyCode(familyDO.getCode()).path1(familyDO.getPath1()).path2(familyDO.getPath2()).build();
        //获取户型设备列表

    }
}
