package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrBit;
import com.landleaf.homeauto.center.device.model.mapper.DeviceAttrBitMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrBitService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 协议属性二进制值配置 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
@Service
public class DeviceAttrBitServiceImpl extends ServiceImpl<DeviceAttrBitMapper, DeviceAttrBit> implements IDeviceAttrBitService {

    @Override
    public List<DeviceAttrBit> getListByAttrId(String attrId) {
        return this.baseMapper.getListByAttrId(attrId);
    }
}
