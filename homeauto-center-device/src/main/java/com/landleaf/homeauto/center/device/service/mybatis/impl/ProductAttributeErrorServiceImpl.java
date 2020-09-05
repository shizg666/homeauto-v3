package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.AttributeErrorTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeError;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeErrorInfo;
import com.landleaf.homeauto.center.device.model.mapper.ProductAttributeErrorMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeErrorService;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ProductErrorAttributeDTO request) {
        if (CollectionUtils.isEmpty(request.getErrorAttributes())) {
            return;
        }
        List<ProductAttributeErrorDTO> errorAttributes = request.getErrorAttributes();
        List<ProductAttributeError> saveErrorAttrs = Lists.newArrayListWithCapacity(errorAttributes.size());
        List<ProductAttributeErrorInfo> saveErrorInfoAttrs = Lists.newArrayList();
//        List<ProductAttributeError> attributeErrors = BeanUtil.mapperList(errorAttributes,ProductAttributeError.class);
        for (ProductAttributeErrorDTO errorAttribute : errorAttributes) {
            ProductAttributeError attributeError = BeanUtil.mapperBean(errorAttribute, ProductAttributeError.class);
            attributeError.setProductId(request.getProductId());
            attributeError.setId(IdGeneratorUtil.getUUID32());
            saveErrorAttrs.add(attributeError);
            if (CollectionUtils.isEmpty(errorAttribute.getInfos())) {
                continue;
            }
            List<ProductAttributeErrorInfoDTO> infos = errorAttribute.getInfos();
//            List<ProductAttributeErrorInfo> errorInfos = BeanUtil.mapperList(infos, ProductAttributeErrorInfo.class);
            infos.forEach(errorInfo -> {
                ProductAttributeErrorInfo errorInfoObj = BeanUtil.mapperBean(errorInfo, ProductAttributeErrorInfo.class);
                errorInfoObj.setErrorAttributeId(attributeError.getId());
                saveErrorInfoAttrs.add(errorInfoObj);
            });
        }
        saveBatch(saveErrorAttrs);
        iProductAttributeErrorInfoService.saveBatch(saveErrorInfoAttrs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductErrorAttributeDTO request) {
        deleteErrorAttribures(request);
        add(request);
    }

    /**
     * 删除产品故障属性
     * @param request
     */
    private void deleteErrorAttribures(ProductErrorAttributeDTO request) {
        List<String> ids = this.getIdListByProductId(request.getProductId());
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        this.remove(new LambdaQueryWrapper<ProductAttributeError>().eq(ProductAttributeError::getProductId, request.getProductId()));
        iProductAttributeErrorInfoService.remove(new LambdaQueryWrapper<ProductAttributeErrorInfo>().in(ProductAttributeErrorInfo::getErrorAttributeId, ids));
    }
}
