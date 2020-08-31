package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.*;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.apache.poi.ss.formula.functions.Count;
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
    @Autowired
    private IProductAttributeInfoScopeService iProductAttributeInfoScopeService;
    @Autowired
    private IHomeAutoCategoryService iHomeAutoCategoryService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ProductDTO request) {
        checkAdd(request);
        HomeAutoProduct product = BeanUtil.mapperBean(request, HomeAutoProduct.class);
        save(product);
        saveAttribute(request.setId(product.getId()));
    }

    private void saveAttribute(ProductDTO request) {
        if (CollectionUtils.isEmpty(request.getAttributes())) {
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
            if (AttributeTypeEnum.RANGE.getType().equals(attribute.getType()) && attribute.getScope() != null) {
                ProductAttributeInfoScope scope = BeanUtil.mapperBean(attribute.getScope(), ProductAttributeInfoScope.class);
                scope.setType(ATTRIBUTE_TYPE);
                scope.setParentId(productAttribute.getId());
                scopeList.add(scope);
                continue;
            }
            if (CollectionUtils.isEmpty(attribute.getInfos())) {
                continue;
            }
            attribute.getInfos().forEach(info -> {
                ProductAttributeInfoDO attributeInfo = BeanUtil.mapperBean(info, ProductAttributeInfoDO.class);
                attributeInfo.setProductAttributeId(productAttribute.getId());
                attributeInfo.setId(IdGeneratorUtil.getUUID32());
                infoList.add(attributeInfo);
                if (AttributeTypeEnum.MULTIPLE_CHOICE_SPECIAL.getType().equals(attribute.getType()) && info.getScope() != null) {
                    ProductAttributeInfoScope scope = BeanUtil.mapperBean(info.getScope(), ProductAttributeInfoScope.class);
                    scope.setType(ATTRIBUTE_INFO_TYPE);
                    scope.setParentId(attributeInfo.getId());
                    scopeList.add(scope);
                }
            });
        }
        iProductAttributeService.saveBatch(attributeList);
        iProductAttributeInfoService.saveBatch(infoList);
        iProductAttributeInfoScopeService.saveBatch(scopeList);
    }

    private void checkAdd(ProductDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoProduct>().eq(HomeAutoProduct::getCode, request.getCode()).or().eq(HomeAutoProduct::getName, request.getName()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "编码或者名称已存在！");
        }
    }

    @Override
    public void update(ProductDTO request) {
        checkUpdate(request);
        HomeAutoProduct product = BeanUtil.mapperBean(request, HomeAutoProduct.class);
        updateById(product);
//        deleteProductAttribures(request.getId());
//        saveAttribute(request);
    }

    private void deleteProductAttribures(String id) {
        List<String> ids = iProductAttributeService.getIdListByProductId(id);
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        iProductAttributeService.remove(new LambdaQueryWrapper<ProductAttributeDO>().eq(ProductAttributeDO::getProductId, id));
        iProductAttributeInfoService.remove(new LambdaQueryWrapper<ProductAttributeInfoDO>().in(ProductAttributeInfoDO::getProductAttributeId, ids));
    }

    private void checkUpdate(ProductDTO request) {
        HomeAutoProduct product = getById(request.getId());
        if (product == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "产品id不存在！");
        }
        if (request.getName().equals(product.getName())) {
            return;
        }
        if (!request.getCode().equals(product.getCode()) && !request.getName().equals(product.getName())) {
            checkAdd(request);
        }
        LambdaQueryWrapper<HomeAutoProduct> wrapper = new LambdaQueryWrapper<HomeAutoProduct>();
        wrapper.eq(HomeAutoProduct::getName, request.getName());
        int count = count(wrapper);
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "名称已存在");
        }
    }

    @Override
    public BasePageVO<ProductPageVO> page(ProductQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<ProductPageVO> pageVOList = this.baseMapper.listPage(request);
        PageInfo pageInfo = new PageInfo(pageVOList);
        BasePageVO<ProductPageVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        if (CollectionUtils.isEmpty(pageVOList)) {
            return resultData;
        }
        List<String> productIds = pageVOList.stream().map(ProductPageVO::getId).collect(Collectors.toList());
        List<CountBO> countBOS = iFamilyDeviceService.getCountByProducts(productIds);
        Map<String, Integer> countMap = countBOS.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        pageVOList.forEach(product -> {
            if (countMap.containsKey(product.getId())) {
                product.setCount(countMap.get(product.getId()));
            }
        });
        return resultData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        //todo 判断产品下是否有设备 户型
        if (iFamilyDeviceService.existByProductId(id)) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "产品下存在设备不可删除");
        }
        removeById(id);
        deleteProductAttribures(id);
    }

    @Override
    public List<SelectedVO> getProtocols(String categoryId) {
        List<SelectedVO> data = iHomeAutoCategoryService.getProtocolsByid(categoryId);
        return data;
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
    public List<ProductAttributeBO> getListAttributeById(String id) {
        List<ProductAttributeBO> data = this.baseMapper.getListProductAttributeById(id);
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayListWithCapacity(0);
        }
        data.forEach(obj -> {
            buildStr(obj);
        });
        return data;
    }

    @Override
    public ProductDetailVO getProductDetailInfo(String id) {
        ProductDetailVO detailVO = this.baseMapper.getProductDetailInfo(id);
        if (detailVO == null) {
            return new ProductDetailVO();
        }
        List<ProductAttributeBO> attributeBOS = this.getListAttributeById(id);
        List<ProductAttributeVO> attributeVOS = BeanUtil.mapperList(attributeBOS, ProductAttributeVO.class);
        detailVO.setAttributes(attributeVOS);
        return detailVO;
    }


    @Override
    public List<SelectedIntegerVO> getNatures() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (AttributeNatureEnum value : AttributeNatureEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public String getCategoryCodeByProductCode(String productCode) {
        QueryWrapper<HomeAutoProduct> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("code", productCode);
        HomeAutoProduct homeAutoProduct = getOne(productQueryWrapper);
        HomeAutoCategory homeAutoCategory = iHomeAutoCategoryService.getById(homeAutoProduct.getCategoryId());
        return homeAutoCategory.getCode();
    }

    @Override
    public List<ProductInfoSelectVO> getListProductSelect() {

        return this.baseMapper.getListProductSelect();
    }


    /**
     * 构建属性展示字符串
     *
     * @param obj
     */
    private void buildStr(ProductAttributeBO obj) {
        if (AttributeTypeEnum.RANGE.getType().equals(obj.getType())) {
            ProductAttributeScopeVO scopeVO = obj.getScope();
            if (scopeVO == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(scopeVO.getMin()).append("-").append(scopeVO.getMax()).append("、").append(scopeVO.getPrecisionStr()).append("、").append(scopeVO.getStep());
            obj.setInfoStr(sb.toString());
        } else {
            StringBuilder sb = new StringBuilder();
            List<ProductAttributeInfoVO> infoVOS = obj.getInfos();
            if (CollectionUtils.isEmpty(infoVOS)) {
                return;
            }
            infoVOS.forEach(info -> {
                sb.append(info.getName());
                AttributeInfoScopeVO scopeVO = info.getScope();
                if (scopeVO != null) {
                    if (!StringUtil.isBlank(scopeVO.getMax()) && !StringUtil.isBlank(scopeVO.getMin())) {
                        sb.append("(").append(scopeVO.getMin()).append("-").append(scopeVO.getMax()).append(")");
                    }
                }
                sb.append("、");
            });
            obj.setInfoStr(sb.toString().substring(0, sb.toString().length() - 1));
        }
    }
}
