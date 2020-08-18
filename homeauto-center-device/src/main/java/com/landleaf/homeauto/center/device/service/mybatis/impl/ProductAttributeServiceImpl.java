package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    public List<String> getIdListByProductId(String id) {
        List<String> data = this.baseMapper.getIdListByProductId(id);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

}
