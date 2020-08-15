package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeService;
import com.landleaf.homeauto.model.po.device.ProductAttributePO;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品属性信息表 服务实现类
 * </p>
 *
<<<<<<< HEAD
 * @author Yujiumin
 * @since 2020-08-15
 */
@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttributePO> implements IProductAttributeService {

    @Override
    public ProductAttributePO getProductAttributeById(String id) {
        return getBaseMapper().selectById(id);
    }

}
