package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeInfoScopeMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.ProductAttributeValueScopeBO;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoScopeService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoService;
import com.landleaf.homeauto.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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

    @Autowired
    private IProductAttributeInfoService productAttributeInfoService;

    @Override
    public List<ProductAttributeValueScopeBO> getByProductAttributeId(String productAttributeId) {
        // 1. 先通过属性ID查询该属性下面可以有哪些值
        List<ProductAttributeInfoDO> productAttributeInfoDOList = productAttributeInfoService.listByProductAttributeId(productAttributeId);
        log.info("产品属性值域: {}", productAttributeInfoDOList);

        // 2. 遍历每一个属性值, 取他们的取值范围
        List<ProductAttributeValueScopeBO> productAttributeValueScopeBOList = new LinkedList<>();
        for (ProductAttributeInfoDO productAttributeInfoDO : productAttributeInfoDOList) {
            QueryWrapper<ProductAttributeInfoScope> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", productAttributeInfoDO.getId());
            List<ProductAttributeInfoScope> productAttributeInfoScopeList = list(queryWrapper);
            if (!CollectionUtil.isEmpty(productAttributeInfoScopeList)) {
                if (productAttributeInfoScopeList.size() == 1) {
                    ProductAttributeInfoScope productAttributeInfoScope = productAttributeInfoScopeList.get(0);
                    ProductAttributeValueScopeBO productAttributeValueScopeBO = new ProductAttributeValueScopeBO();
                    productAttributeValueScopeBO.setAttributeValue(productAttributeInfoDO.getCode());
                    productAttributeValueScopeBO.setMinValue(productAttributeInfoScope.getMin());
                    productAttributeValueScopeBO.setMaxValue(productAttributeInfoScope.getMax());
                    productAttributeValueScopeBOList.add(productAttributeValueScopeBO);
                    continue;
                }
                log.error("属性值下的取值范围不唯一, 属性ID: {}, 属性值ID:{}", productAttributeId, productAttributeInfoDO.getId());
                throw new BusinessException(90000, "属性值下的取值范围不唯一@" + productAttributeInfoDO.getId());
            }
        }
        return productAttributeValueScopeBOList;
    }
}
