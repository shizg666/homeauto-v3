package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.mapper.SysProductAttributeInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductAttributeInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统产品属性值表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Service
public class SysProductAttributeInfoServiceImpl extends ServiceImpl<SysProductAttributeInfoMapper, SysProductAttributeInfo> implements ISysProductAttributeInfoService {

    @Override
    public List<SysProductAttributeInfo> getByProductCode(String productCode) {
        LambdaQueryWrapper<SysProductAttributeInfo> queryWrapper = new LambdaQueryWrapper<SysProductAttributeInfo>().eq(SysProductAttributeInfo::getSysProductCode,productCode);
        return list(queryWrapper);
    }
}
