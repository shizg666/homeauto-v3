package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
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
        return null;
    }
}
