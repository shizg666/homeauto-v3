package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoProductMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.common.enums.category.BaudRateEnum;
import com.landleaf.homeauto.common.enums.category.CheckEnum;
import com.landleaf.homeauto.common.enums.category.ProtocolEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Service
public class HomeAutoProductServiceImpl extends ServiceImpl<HomeAutoProductMapper, HomeAutoProduct> implements IHomeAutoProductService {

    public static final Integer ATTRIBUTE_TYPE = 1;
    public static final Integer ATTRIBUTE_INFO_TYPE = 2;

    @Autowired
    private IProductAttributeService iProductAttributeService;
    @Autowired
    private IProductAttributeInfoService iProductAttributeInfoService;
    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ProductDTO request) {
        checkAdd(request);
        HomeAutoProduct product = BeanUtil.mapperBean(request,HomeAutoProduct.class);
        save(product);
        saveAttribute(request.setId(product.getId()));
    }

    private void saveAttribute(ProductDTO request) {
        if (CollectionUtils.isEmpty(request.getAttributes())){
            return;
        }
        String id = request.getId();
        List<ProductAttributeInfoDO> infoList = Lists.newArrayList();
        List<ProductAttributeDO> attributeList = Lists.newArrayList();
        List<ProductAttributeInfoScope> scopeList = Lists.newArrayList();
        for (ProductAttributeDTO attribute : request.getAttributes()) {
            ProductAttributeDO productAttribute = BeanUtil.mapperBean(attribute, ProductAttributeDO.class);
            productAttribute.setProductId(id);
            productAttribute.setId(IdGeneratorUtil.getUUID32());
            attributeList.add(productAttribute);
            if (AttributeTypeEnum.RANGE.getType().equals(attribute.getType())) {
                ProductAttributeInfoScope scope = BeanUtil.mapperBean(attribute.getScopeDTO(), ProductAttributeInfoScope.class);
                scope.setType(ATTRIBUTE_TYPE);
                scope.setParentId(productAttribute.getProductId());
                scopeList.add(scope);
                continue;
            }
            if (CollectionUtils.isEmpty(attribute.getInfos())) {
                continue;
            }
            attribute.getInfos().forEach(info->{
                ProductAttributeInfoDO attributeInfo = BeanUtil.mapperBean(info,ProductAttributeInfoDO.class);
                attributeInfo.setProductAttributeId(productAttribute.getId());
                attributeInfo.setId(productAttribute.getProductId());
                infoList.add(attributeInfo);
                if (AttributeTypeEnum.MULTIPLE_CHOICE_SPECIAL.getType().equals(attribute.getType())) {
                    ProductAttributeInfoScope scope = BeanUtil.mapperBean(info.getScopeDTO(), ProductAttributeInfoScope.class);
                    scope.setType(ATTRIBUTE_INFO_TYPE);
                    scope.setParentId(attributeInfo.getId());
                    scopeList.add(scope);
                }
            });
        }
        iProductAttributeService.saveBatch(attributeList);
        iProductAttributeInfoService.saveBatch(infoList);
    }

    private void checkAdd(ProductDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoProduct>().eq(HomeAutoProduct::getCode,request.getCode()).or().eq(HomeAutoProduct::getName,request.getName()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"编码或者名称已存在！");
        }
    }

    @Override
    public void update(ProductDTO request) {
        checkUpdate(request);
        HomeAutoProduct product = BeanUtil.mapperBean(request,HomeAutoProduct.class);
        updateById(product);
        deleteProductAttribures(request.getId());
        saveAttribute(request);
    }

    private void deleteProductAttribures(String id) {
        List<String> ids = iProductAttributeService.getIdListByProductId(id);
        if (CollectionUtils.isEmpty(ids)){
            return;
        }
        iProductAttributeService.remove(new LambdaQueryWrapper<ProductAttributeDO>().eq(ProductAttributeDO::getProductId,id));
        iProductAttributeInfoService.remove(new LambdaQueryWrapper<ProductAttributeInfoDO>().in(ProductAttributeInfoDO::getProductAttributeId,ids));
    }

    private void checkUpdate(ProductDTO request) {
        HomeAutoProduct product = getById(request.getId());
        if (product == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"产品id不存在！");
        }
        if (request.getCode().equals(product.getCode()) && request.getName().equals(product.getName()) ){
            return;
        }
        LambdaQueryWrapper<HomeAutoProduct> wrapper = new LambdaQueryWrapper<HomeAutoProduct>();
        if (!request.getCode().equals(product.getCode()) && !request.getName().equals(product.getName())){
            checkAdd(request);
        }
        if (!request.getCode().equals(product.getCode())){
            wrapper.eq(HomeAutoProduct::getCode,product.getCode());
        }
        if (!request.getName().equals(product.getName())){
            wrapper.eq(HomeAutoProduct::getName,product.getName());
        }
        int count = count(wrapper);
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"编码或者名称已存在！");
        }
    }

    @Override
    public BasePageVO<ProductPageVO> page(ProductQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<ProductPageVO> pageVOList = this.baseMapper.listPage(request);
        PageInfo pageInfo = new PageInfo(pageVOList);
        BasePageVO<ProductPageVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
        return resultData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        //todo 判断产品下是否有设备 户型
        if (iFamilyDeviceService.existByProductId(id)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"产品下存在设备不可删除");
        }
        removeById(id);
        deleteProductAttribures(id);
    }

    @Override
    public List<SelectedVO> getProtocols() {
        List<SelectedVO> selectedVOS = Lists.newArrayList();
        for (ProtocolEnum value : ProtocolEnum.values()) {
            SelectedVO cascadeVo = new SelectedVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedIntegerVO> getBaudRates() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (BaudRateEnum value : BaudRateEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }



    @Override
    public List<SelectedIntegerVO> getCheckModes() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (CheckEnum value : CheckEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<ProductAttributeVO> getListAttributeById(String id) {
        List<ProductAttributeVO> attributeVOS = this.baseMapper.getListProductAttributeById(id);
        return null;
    }
}
