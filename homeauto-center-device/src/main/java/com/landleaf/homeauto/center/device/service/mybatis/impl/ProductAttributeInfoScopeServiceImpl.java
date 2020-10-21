package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeInfoScopeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoScopeService;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ProductAttributeInfoScope getByProductAttributeId(String productAttributeId, final String attributeValue) {
        // 1. 先通过属性ID查询该属性下面可以有哪些值
        List<ProductAttributeInfoDO> productAttributeInfoDOList = productAttributeInfoService.listByProductAttributeId(productAttributeId);
        log.info("产品属性值域: {}", productAttributeInfoDOList);

        // 如果这个产品没有值域, 说明是数值类型的
        if (!CollectionUtil.isEmpty(productAttributeInfoDOList)) {
            // 2. 再把当前的值和设置的值进行匹配, 拿到值实体信息(拿到具体是哪个值)
            List<ProductAttributeInfoDO> productAttributeInfoFilterList = productAttributeInfoDOList.stream().filter(attributeDO -> Objects.equals(attributeDO.getCode(), attributeValue)).collect(Collectors.toList());
            log.info("筛选后的属性值域: {}", productAttributeInfoFilterList);

            // 3. 最后通过模式值的ID, 去获取值的取值范围(拿到这个模式下的取值范围)
            ProductAttributeInfoDO productAttributeInfoDO = productAttributeInfoFilterList.get(0);

            QueryWrapper<ProductAttributeInfoScope> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", productAttributeInfoDO.getId());
            List<ProductAttributeInfoScope> productAttributeInfoScopeList = list(queryWrapper);
            if (!CollectionUtil.isEmpty(productAttributeInfoScopeList)) {
                return productAttributeInfoScopeList.get(0);
            }
        }
        return null;
    }
}
