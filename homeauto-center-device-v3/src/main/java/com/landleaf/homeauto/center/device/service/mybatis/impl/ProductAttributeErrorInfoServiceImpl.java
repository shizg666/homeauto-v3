package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeErrorInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Service
public class ProductAttributeErrorInfoServiceImpl extends ServiceImpl<ProductAttributeErrorInfoMapper, ProductAttributeErrorInfo> implements IProductAttributeErrorInfoService {

    @Override
    public List<String> getListDesc(String errorAttributeId) {
        return this.baseMapper.getListDesc(errorAttributeId);
    }

    @Override
    public List<ProductAttributeErrorInfo> getByProductCode(String productCode) {
        LambdaQueryWrapper<ProductAttributeErrorInfo> queryWrapper = new LambdaQueryWrapper<ProductAttributeErrorInfo>().eq(ProductAttributeErrorInfo::getProductCode,productCode);
        return list(queryWrapper);
    }
}
