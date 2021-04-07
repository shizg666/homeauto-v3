package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.constant.DeviceNatureEnum;
import com.landleaf.homeauto.center.device.model.dto.product.ProductDTO;
import com.landleaf.homeauto.center.device.model.dto.product.ProductPageVO;
import com.landleaf.homeauto.center.device.model.vo.BasePageVOFactory;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.enums.category.*;
import com.landleaf.homeauto.center.device.model.domain.category.*;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoProductMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
@Slf4j
public class HomeAutoProductServiceImpl extends ServiceImpl<HomeAutoProductMapper, HomeAutoProduct> implements IHomeAutoProductService {
    // 属性类型
    public static final Integer ATTRIBUTE_TYPE = 1;
    //属性值类型
    public static final Integer ATTRIBUTE_INFO_TYPE = 2;
    public static final String ZERO_STR = "0";
    public static final Integer UPDATE_FLAG = 1;

    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IdService idService;
    @Autowired
    private IProductAttributeService iProductAttributeService;
    @Autowired
    private IProductAttributeInfoService iProductAttributeInfoService;
    @Autowired
    private IProductAttributeInfoScopeService iProductAttributeInfoScopeService;
    @Autowired
    private IProductAttributeErrorService iProductAttributeErrorService;
    @Autowired
    private IProductAttributeErrorInfoService iProductAttributeErrorInfoService;
    @Autowired
    private IHomeAutoCategoryService iHomeAutoCategoryService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public HomeAutoProduct add(ProductDTO request) {
        String categoryCode = iHomeAutoCategoryService.getCategoryCodeById(request.getCategoryId());
        checkAdd(request);
        HomeAutoProduct product = BeanUtil.mapperBean(request, HomeAutoProduct.class);
        iconRevole(product,request.getIcon());
        product.setCategoryCode(categoryCode);
        String productCode = buildProductCode(request.getCategoryId(),categoryCode);
        product.setCode(productCode);
        save(product);
        //保存产品属性
        request.setCode(productCode);
        saveAttribute(request.setId(product.getId()));
        return product;
    }

    /**
     * 生成产品code  5位  前两位品类code 后3位递增的数值
     * @param categoryId
     * @return
     */
    private String buildProductCode(Long categoryId,String categoryCode) {
        String productCode = categoryCode;
        if (categoryCode.length()<2){
            productCode = ZERO_STR.concat(categoryCode);
        }
        String productCodeMax = this.baseMapper.getLastProductCodeByCategory(categoryId);
        if (StringUtil.isEmpty(productCodeMax)){
            productCode.concat("001");
        }else {
            Integer num = Integer.valueOf(productCodeMax)+1;
            if (categoryCode.length()<2){
                productCode = ZERO_STR.concat(String.valueOf(num));
            }
        }
        return productCode;
    }



    /**
     * 产品属性保存
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveAttribute(ProductDTO request) {
        //产品属性
        List<ProductAttributeDO> attributeList = Lists.newArrayList();
        //产品属性值
        List<ProductAttributeInfoDO> infoList = Lists.newArrayList();
        //数值类型的属性配置
        List<ProductAttributeInfoScope> scopeList = Lists.newArrayList();

        //功能属性
        buildAttrData(attributeList,infoList,scopeList,request,CategoryAttributeTypeEnum.FEATURES.getType(),request.getAttributes1());
        buildAttrData(attributeList,infoList,scopeList,request,CategoryAttributeTypeEnum.BASE.getType(),request.getAttributes2());

        iProductAttributeService.saveBatch(attributeList);
        iProductAttributeInfoService.saveBatch(infoList);
        iProductAttributeInfoScopeService.saveBatch(scopeList);
    }

    /**
     * 属性数据构造
     * @param infoList
     * @param scopeList
     * @param request
     * @param type
     * @param attributes
     */
    private void buildAttrData(List<ProductAttributeDO> attributeList,List<ProductAttributeInfoDO> infoList, List<ProductAttributeInfoScope> scopeList, ProductDTO request, Integer type, List<ProductAttributeDTO> attributes) {
        if (CollectionUtils.isEmpty(attributes)) {
            return;
        }
        for (ProductAttributeDTO attribute : request.getAttributes1()) {
            ProductAttributeDO productAttribute = BeanUtil.mapperBean(attribute, ProductAttributeDO.class);
            productAttribute.setProductId(request.getId());
            productAttribute.setProductCode(request.getCode());
            productAttribute.setId(idService.getSegmentId());
            productAttribute.setFunctionType(type);
            attributeList.add(productAttribute);
            if (AttributeTypeEnum.VALUE.getType().equals(attribute.getType()) && attribute.getScope() != null) {
                ProductAttributeInfoScope scope = BeanUtil.mapperBean(attribute.getScope(), ProductAttributeInfoScope.class);
                scope.setType(ATTRIBUTE_TYPE);
                scope.setParentId(productAttribute.getId());
                scope.setProductId(request.getId());
//                if (SETTING_TEMPERATURE.equals(attribute.getCode()) ){
//                    if (StringUtil.isEmpty(attribute.getScope().getMax()) || StringUtil.isEmpty(attribute.getScope().getMin())){
//                        scope.setMin(MIN);
//                        scope.setMax(MAX);
//                    }
//                }
                scopeList.add(scope);
                continue;
            }
            if (CollectionUtils.isEmpty(attribute.getInfos())) {
                continue;
            }
            attribute.getInfos().forEach(info -> {
                ProductAttributeInfoDO attributeInfo = BeanUtil.mapperBean(info, ProductAttributeInfoDO.class);
                attributeInfo.setProductAttributeId(productAttribute.getId());
                attributeInfo.setProductId(request.getId());
                infoList.add(attributeInfo);
            });
        }
    }


    /**
     * 产品图片（黑白,彩色需要解析） 格式：黑白，彩色
     * @param product
     * @param icon
     */
    private void iconRevole(HomeAutoProduct product, String icon) {
        if (StringUtil.isEmpty(icon)){
            return;
        }
        String[] icons = icon.split(",");
        if (icons == null){
            return;
        }
        if (icons.length !=2){
            product.setIcon(icon);
            product.setIcon2("");
        }else {
            product.setIcon(icons[0]);
            product.setIcon2(icons[1]);
        }

    }


    private void checkAdd(ProductDTO request) {
        productCheckCodeAndName(request.getCode(),request.getName());
    }

    @Override
    public HomeAutoProduct update(ProductDTO request) {
        checkUpdate(request);
        HomeAutoProduct product = BeanUtil.mapperBean(request, HomeAutoProduct.class);
        iconRevole(product,request.getIcon());
        updateById(product);
        //todo 加上判断
//        boolean b  = this.getExistProductDevice(request.getId());
        if (UPDATE_FLAG.equals(request.getUpdateFalg())){
            saveAttribute(request);
        }else {
            deleteProductAttribures(request.getId());
            saveAttribute(request);
        }
        return product;
    }


    private void checkUpdate(ProductDTO request) {
        if (UPDATE_FLAG.equals(request.getUpdateFalg()) && iHouseTemplateDeviceService.existByProductId(request.getId())){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "产品下有设备存在不可修改！");
        }
        HomeAutoProduct product = getById(request.getId());
        if (product == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "产品id不存在！");
        }
        if (product.getCode().equals(request.getCode()) && product.getName().equals(request.getName())){
            return;
        }
        if (!product.getCode().equals(request.getCode()) && !product.getName().equals(request.getName())){
            productCheckCodeAndName(request.getCode(),request.getName());
        }else if (!product.getCode().equals(request.getCode())){
            productCheckCodeAndName(request.getCode(),null);
        }else if (!product.getName().equals(request.getName())){
            productCheckCodeAndName(null,request.getName());
        }
    }

    private void productCheckCodeAndName(String code, String name) {
        LambdaQueryWrapper<HomeAutoProduct> wrapper = new LambdaQueryWrapper();
        if (!StringUtil.isEmpty(code)){
            wrapper.eq(HomeAutoProduct::getCode,code);
        }
        if (!StringUtil.isEmpty(name)){
            wrapper.or().eq(HomeAutoProduct::getName,name);
        }
        int count = count(wrapper);
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "名称或编码已存在");
        }
    }

    @Override
    public BasePageVO<ProductPageVO> page(ProductQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<ProductPageVO> pageVOList = this.baseMapper.listPage(request);
        if (CollectionUtils.isEmpty(pageVOList)){
            return BasePageVOFactory.getBasePage(Lists.newArrayListWithCapacity(0));
        }
//        pageVOList.forEach(product->{
//            if (!StringUtil.isEmpty(product.getIcon2())){
//                product.setIcon(product.getIcon().concat(",").concat(product.getIcon2()));
//            }
//        });
        return BasePageVOFactory.getBasePage(pageVOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        if (iHouseTemplateDeviceService.existByProductId(id)) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "产品下存在设备不可删除");
        }
        removeById(id);
        deleteProductAttribures(id);
        removeById(id);
    }

    /**
     * 删除产品属性
     * @param productId
     */
    private void deleteProductAttribures(Long productId) {
        iProductAttributeService.remove(new LambdaQueryWrapper<ProductAttributeDO>().eq(ProductAttributeDO::getProductId, productId));
        iProductAttributeInfoScopeService.remove(new LambdaQueryWrapper<ProductAttributeInfoScope>().eq(ProductAttributeInfoScope::getProductId, productId));
        iProductAttributeInfoService.remove(new LambdaQueryWrapper<ProductAttributeInfoDO>().in(ProductAttributeInfoDO::getProductId, productId));
        //故障属性
        iProductAttributeErrorService.remove(new LambdaQueryWrapper<ProductAttributeError>().eq(ProductAttributeError::getProductId, productId));
        iProductAttributeErrorInfoService.remove(new LambdaQueryWrapper<ProductAttributeErrorInfo>().eq(ProductAttributeErrorInfo::getProductId, productId));

    }

    @Override
    public List<SelectedVO> getProtocols() {
        List<SelectedVO> selectedVOS = Lists.newArrayListWithCapacity(ProtocolEnum.values().length);
        for (ProtocolEnum value : ProtocolEnum.values()) {
            SelectedVO cascadeVo = new SelectedVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }
//
//    @Override
//    public List<SelectedIntegerVO> getBaudRates() {
//        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
//        for (BaudRateEnum value : BaudRateEnum.values()) {
//            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
//            selectedVOS.add(cascadeVo);
//        }
//        return selectedVOS;
//    }
//
//
//    @Override
//    public List<SelectedIntegerVO> getCheckModes() {
//        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
//        for (CheckEnum value : CheckEnum.values()) {
//            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
//            selectedVOS.add(cascadeVo);
//        }
//        return selectedVOS;
//    }

    @Override
    public List<ProductAttributeBO> getListAttributeById(String productId) {
        List<ProductAttributeBO> data = this.baseMapper.getListProductAttributeById(productId);
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayListWithCapacity(0);
        }
        buildStr(data);
        return data;
    }

    @Override
    public ProductDetailVO getProductDetailInfo(String productId) {
        ProductDetailVO detailVO = this.baseMapper.getProductDetailInfo(productId);
        if (Objects.isNull(detailVO)) {
            return null;
        }
        List<ProductAttributeBO> attributeBOS = this.getListAttributeById(productId);
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
    public HomeAutoProduct getCategoryByProductCode(String productCode) {
        log.info("productCode为{}", productCode);
        QueryWrapper<HomeAutoProduct> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("code", productCode);
        return getOne(productQueryWrapper);
    }

    @Override
    public List<SelectedLongVO> getListProductSelect() {
        List<HomeAutoProduct> products = list(new LambdaQueryWrapper<HomeAutoProduct>().select(HomeAutoProduct::getId,HomeAutoProduct::getName));
        if (CollectionUtils.isEmpty(products)){
            return Lists.newArrayListWithCapacity(0);
        }
        return products.stream().map(product->new SelectedLongVO(product.getName(),product.getId())).collect(Collectors.toList());
    }

    @Override
    public List<SelectedIntegerVO> getErrorTypes() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (AttributeErrorTypeEnum value : AttributeErrorTypeEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getReadAttrSelects(String productId) {
        return this.baseMapper.getReadAttrSelects(productId);
    }

    @Override
    public List<SceneDeviceAttributeVO> getListdeviceAttributeInfo(List<String> productIds) {
        return this.baseMapper.getListdeviceAttributeInfo(productIds);
    }




    @Override
    public boolean getHvacFlagById(String productId) {
        int hvacFlag = this.baseMapper.getHvacFlagById(productId);
        if (1 == hvacFlag) {
            return true;
        }
        return false;
    }

    @Override
    public List<CascadeVo> allProductType() {
        return this.baseMapper.allProductType();
    }

    @Override
    public List<SelectedVO> getListCodeSelects() {
        List<HomeAutoProduct> products = list(new LambdaQueryWrapper<HomeAutoProduct>().select(HomeAutoProduct::getCode, HomeAutoProduct::getName));
        if (CollectionUtils.isEmpty(products)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SelectedVO> selectedVOS = Lists.newArrayListWithCapacity(products.size());
        products.forEach(product -> {
            SelectedVO selectedVO = new SelectedVO(product.getName(), product.getCode());
            selectedVOS.add(selectedVO);
        });
        return selectedVOS;
    }

    @Override
    public List<HomeAutoProduct> listProductByNature(DeviceNatureEnum deviceNatureEnum) {
        QueryWrapper<HomeAutoProduct> homeAutoProductQueryWrapper = new QueryWrapper<>();
        homeAutoProductQueryWrapper.eq("nature", deviceNatureEnum.getCode());
        return list(homeAutoProductQueryWrapper);
    }

    @Override
    public ProductProtocolInfoBO getProductProtocolInfo(String productId) {
        return this.baseMapper.getProductProtocolInfo(productId);
    }

    @Override
    public String getCategoryCodeById(String productId) {
        return this.baseMapper.getCategoryCodeById(productId);
    }

    @Override
    public String getProductCodeById(String productId) {
        return this.baseMapper.getProductCodeById(productId);
    }

    @Override
    public List<TotalCountBO> getCountGroupByCategory(List<String> categoryCodes) {
        return this.baseMapper.getCountGroupByCategory(categoryCodes);
    }

    @Override
    public List<ProductInfoSelectVO> getListProductSelectByCategoryId(String categoryId) {
        return this.baseMapper.getListProductSelectByCategoryId(categoryId);
    }

    @Override
    public boolean getExistProductDevice(Long productId) {
        return this.baseMapper.getExistProductDevice(productId);
    }


    /**
     * 构建属性展示字符串
     *
     * @param data
     */
    private void buildStr(List<ProductAttributeBO> data) {
        data.forEach(obj -> {
            if (AttributeTypeEnum.VALUE.getType().equals(obj.getType())) {
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
                    sb.append("、");
                });
                obj.setInfoStr(sb.toString().substring(0, sb.toString().length() - 1));
            }
        });
    }
}
