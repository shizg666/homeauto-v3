package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeInfoScopeMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.ProductAttributeValueScopeBO;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoScopeService;
import com.landleaf.homeauto.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 产品属性精度范围表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Slf4j
@Service
public class ProductAttributeInfoScopeServiceImpl extends ServiceImpl<ProductAttributeInfoScopeMapper, ProductAttributeInfoScope> implements IProductAttributeInfoScopeService {

    @Override
    public ProductAttributeValueScopeBO getByProductAttributeId(String parentId) {

        LambdaQueryWrapper<ProductAttributeInfoScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductAttributeInfoScope::getParentId, parentId);
        List<ProductAttributeInfoScope> productAttributeInfoScopeList = list(queryWrapper);
        if (!CollectionUtil.isEmpty(productAttributeInfoScopeList)) {
            if (productAttributeInfoScopeList.size() == 1) {
                ProductAttributeInfoScope productAttributeInfoScope = productAttributeInfoScopeList.get(0);
                ProductAttributeValueScopeBO productAttributeValueScopeBO = new ProductAttributeValueScopeBO();
                productAttributeValueScopeBO.setMinValue(productAttributeInfoScope.getMin());
                productAttributeValueScopeBO.setMaxValue(productAttributeInfoScope.getMax());
                return productAttributeValueScopeBO;
            } else {
                log.error("属性值下的取值范围不唯一, 属性ID: {}", parentId);
                throw new BusinessException(90000, "属性值下的取值范围不唯一@" + parentId);
            }
        }
        return null;
    }
}
