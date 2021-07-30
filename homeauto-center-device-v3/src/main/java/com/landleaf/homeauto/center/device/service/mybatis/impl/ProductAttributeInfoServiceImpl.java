package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品属性值表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Service
public class ProductAttributeInfoServiceImpl extends ServiceImpl<ProductAttributeInfoMapper, ProductAttributeInfoDO> implements IProductAttributeInfoService {

    @Override
    public ProductAttributeInfoDO getProductAttributeInfoByAttrIdAndCode(String attributeId, String code) {
        QueryWrapper<ProductAttributeInfoDO> productAttributeQueryWrapper = new QueryWrapper<>();
        productAttributeQueryWrapper.eq("product_attribute_id", attributeId);
        productAttributeQueryWrapper.eq("code", code);
        return getBaseMapper().selectOne(productAttributeQueryWrapper);
    }

    @Override
    public List<ProductAttributeInfoDO> listByProductAttributeId(Long productAttributeId) {
        LambdaQueryWrapper<ProductAttributeInfoDO> queryWrapper = new LambdaQueryWrapper<ProductAttributeInfoDO>().eq(ProductAttributeInfoDO::getProductAttributeId,productAttributeId).select(ProductAttributeInfoDO::getCode,ProductAttributeInfoDO::getName,ProductAttributeInfoDO::getSortNo,ProductAttributeInfoDO::getProductId);
        return list(queryWrapper);
    }

    @Override
    public List<ProductAttributeInfoDO> getByProductCode(String productCode) {
        LambdaQueryWrapper<ProductAttributeInfoDO> queryWrapper = new LambdaQueryWrapper<ProductAttributeInfoDO>().eq(ProductAttributeInfoDO::getProductCode,productCode).orderByAsc(ProductAttributeInfoDO::getSortNo);
        return list(queryWrapper);
    }
}
