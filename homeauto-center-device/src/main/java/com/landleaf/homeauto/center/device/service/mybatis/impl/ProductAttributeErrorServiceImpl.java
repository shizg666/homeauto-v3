package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.AttributeErrorTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeErrorMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 产品故障属性表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Service
public class ProductAttributeErrorServiceImpl extends ServiceImpl<ProductAttributeErrorMapper, ProductAttributeError> implements IProductAttributeErrorService {

    @Autowired
    private IProductAttributeErrorInfoService iProductAttributeErrorInfoService;

    @Override
    public List<String> getIdListByProductId(String id) {
        List<String> data = this.baseMapper.getIdListByProductId(id);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public List<String> getErrorInfo(String id) {
        return null;
    }

    @Override
    public AttributeErrorDTO getErrorAttributeInfo(AttributeErrorQryDTO request) {
        AttributeErrorDTO errorDTO = this.baseMapper.getErrorAttributeInfo(request);
        if (errorDTO == null){
            return null;
        }
        if (AttributeErrorTypeEnum.COMMUNICATE.getType().equals(errorDTO.getType()) || AttributeErrorTypeEnum.VAKUE.getType().equals(errorDTO.getType())){
            return errorDTO;
        }
        List<String> desc = iProductAttributeErrorInfoService.getListDesc(errorDTO.getId());
        errorDTO.setDesc(desc);
        return errorDTO;
    }
}
