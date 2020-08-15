package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoService;
import com.landleaf.homeauto.common.domain.po.category.ProductAttributeInfo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品属性值表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Service
public class ProductAttributeInfoServiceImpl extends ServiceImpl<ProductAttributeInfoMapper, ProductAttributeInfo> implements IProductAttributeInfoService {

}
