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
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.category.*;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoProductMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
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

    
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public HomeAutoProduct add(ProductDTO request) {
        checkAdd(request);
        HomeAutoProduct product = BeanUtil.mapperBean(request, HomeAutoProduct.class);
        iconRevole(product,request.getIcon());
        save(product);
        return product;
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
        return product;
    }


    private void checkUpdate(ProductDTO request) {
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
        if(!product.getCategoryCode().equals(request.getCategoryCode())){
            if (iHouseTemplateDeviceService.existByProductId(request.getId())) {
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "产品下存在设备不可修改品类");
            }
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
            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            return BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        }
        pageVOList.forEach(product->{
            if (!StringUtil.isEmpty(product.getIcon2())){
                product.setIcon(product.getIcon().concat(",").concat(product.getIcon2()));
            }
        });
        return BasePageVOFactory.getBasePage(pageVOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        if (iHouseTemplateDeviceService.existByProductId(id)) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "产品下存在设备不可删除");
        }
        removeById(id);
    }

//    @Override
//    public List<SelectedVO> getProtocols(String categoryId) {
//        List<SelectedVO> data = iHomeAutoCategoryService.getProtocolsByid(categoryId);
//        return data;
//    }
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
        if (detailVO == null) {
            return new ProductDetailVO();
        }
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
    public List<SelectedVO> getListProductSelect() {
        List<HomeAutoProduct> products = list(new LambdaQueryWrapper<HomeAutoProduct>().select(HomeAutoProduct::getId,HomeAutoProduct::getName));
        if (CollectionUtils.isEmpty(products)){
            return Lists.newArrayListWithCapacity(0);
        }
        return products.stream().map(product->new SelectedVO(product.getName(),product.getId())).collect(Collectors.toList());
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


    /**
     * 构建属性展示字符串
     *
     * @param data
     */
    private void buildStr(List<ProductAttributeBO> data) {
        data.forEach(obj -> {
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
        });
    }
}
