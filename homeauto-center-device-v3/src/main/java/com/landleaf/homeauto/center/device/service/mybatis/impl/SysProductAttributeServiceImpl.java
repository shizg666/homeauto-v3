package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.category.*;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttribute;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.mapper.SysProductAttributeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Service
public class SysProductAttributeServiceImpl extends ServiceImpl<SysProductAttributeMapper, SysProductAttribute> implements ISysProductAttributeService {
    @Autowired
    private ISysProductAttributeInfoService iSysProductAttributeInfoService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProductAttribures(Long sysProductId) {
        remove(new LambdaQueryWrapper<SysProductAttribute>().eq(SysProductAttribute::getSysProductId, sysProductId));
        iSysProductAttributeInfoService.remove(new LambdaQueryWrapper<SysProductAttributeInfo>().eq(SysProductAttributeInfo::getSysProductId, sysProductId));
    }
}
