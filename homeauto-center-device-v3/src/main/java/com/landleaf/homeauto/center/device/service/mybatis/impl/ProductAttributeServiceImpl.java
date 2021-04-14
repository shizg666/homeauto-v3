package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.ProductAttributeBO;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeService;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 产品属性信息表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttributeDO> implements IProductAttributeService {

    @Override
    public ProductAttributeDO getProductAttributeById(String id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    public List<Long> getIdListByProductId(Long id) {
        List<Long> data = this.baseMapper.getIdListByProductId(id);
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public List<ProductAttributeBO> listByProductCode(String productCode) {
        QueryWrapper<ProductAttributeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_code", productCode);

        List<ProductAttributeDO> productAttributeDOList = list(queryWrapper);
        List<ProductAttributeBO> productAttributeBOList = new LinkedList<>();
        for (ProductAttributeDO productAttributeDO : productAttributeDOList) {
            ProductAttributeBO productAttributeBO = new ProductAttributeBO();
            productAttributeBO.setProductCode(productCode);
            productAttributeBO.setProductAttributeId(productAttributeDO.getId());
            productAttributeBO.setProductAttributeCode(productAttributeDO.getCode());
            productAttributeBO.setProductAttributeName(productAttributeDO.getName());
            productAttributeBO.setAttributeType(AttributeTypeEnum.getInstByType(productAttributeDO.getType()));
            productAttributeBOList.add(productAttributeBO);
        }
        return productAttributeBOList;
    }

    @Override
    public List<ProductAttributeDO> getByProductCode(String productCode) {
        QueryWrapper<ProductAttributeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_code", productCode);
        return list(queryWrapper);
    }


}
