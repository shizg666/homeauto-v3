package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.constant.CategoryConstant;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrValueBO;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProduct;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttribute;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.mapper.SysProductMapper;
import com.landleaf.homeauto.center.device.model.vo.product.ProductAttrInfoBO;
import com.landleaf.homeauto.center.device.model.vo.product.ProductInfoSelectVO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.*;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.common.enums.category.CategoryAttributeTypeEnum;
import com.landleaf.homeauto.common.enums.category.StatusEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
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
 * 系统产品表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Service
public class SysProductServiceImpl extends ServiceImpl<SysProductMapper, SysProduct> implements ISysProductService {

    @Autowired
    private IdService idService;
    @Autowired
    private ISysProductAttributeInfoService iSysProductAttributeInfoService;
    @Autowired
    private ISysProductAttributeService iSysProductAttributeService;
    @Autowired
    private ISysProductCategoryService iSysProductCategoryService;
    @Autowired
    private IBizNumProducerService iBizNumProducerService;
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;
    @Autowired
    private ISysProductAttributeInfoScopeService iSysProductAttributeInfoScopeService;
    @Autowired
    private ISysCategoryAttributeService iSysCategoryAttributeService;
    @Autowired
    private IProductAttributeService iProductAttributeService;
    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    public static final Integer UPDATE_FLAG = 1;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addSysProduct(SysProductDTO requestDTO) {
        checkAdd(requestDTO);
        SysProduct product = BeanUtil.mapperBean(requestDTO, SysProduct.class);
        String productCode = iBizNumProducerService.getProductCode(CategoryConstant.SYS_PRODCUT_CODE);
        product.setCode(productCode);
        product.setStatus(StatusEnum.ENABLE.getType());
        save(product);
        //保存产品属性
        requestDTO.setId(product.getId());
        requestDTO.setCode(productCode);
        saveAttribute(requestDTO);
        //保存系统产品品类信息
        iSysProductCategoryService.saveBathProductCategory(requestDTO.getId(),requestDTO.getCode(),requestDTO.getCategorys());
        return product.getId();
    }

    /**
     * 新增系统产品属性
     * @param requestDTO
     */
    private void saveAttribute(SysProductDTO requestDTO) {
        //产品属性
        List<SysProductAttribute> attributeList = Lists.newArrayList();
        //产品属性值
        List<SysProductAttributeInfo> infoList = Lists.newArrayList();
        //数值类型的属性配置
        List<SysProductAttributeInfoScope> scopeList = Lists.newArrayList();
        //功能属性
        buildAttrData(attributeList, infoList,scopeList, requestDTO, CategoryAttributeTypeEnum.FEATURES.getType(), requestDTO.getAttributesFunc());
        buildAttrData(attributeList, infoList, scopeList, requestDTO, CategoryAttributeTypeEnum.BASE.getType(), requestDTO.getAttributesBase());
        iSysProductAttributeService.saveBatch(attributeList);
        iSysProductAttributeInfoService.saveBatch(infoList);
        iSysProductAttributeInfoScopeService.saveBatch(scopeList);
    }

    private void buildAttrData(List<SysProductAttribute> attributeList, List<SysProductAttributeInfo> infoList,List<SysProductAttributeInfoScope> scopeList, SysProductDTO requestDTO, Integer type, List<SysProductAttributeDTO> attributes) {
        if (CollectionUtils.isEmpty(attributes)) {
            return;
        }
        for (SysProductAttributeDTO attribute : attributes) {
            SysProductAttribute productAttribute = BeanUtil.mapperBean(attribute, SysProductAttribute.class);
            productAttribute.setSysProductId(requestDTO.getId());
            productAttribute.setSysProductCode(requestDTO.getCode());
            productAttribute.setId(idService.getSegmentId());
            productAttribute.setFunctionType(type);
            if (!CollectionUtils.isEmpty(attribute.getCategoryStrList())){
                productAttribute.setCategoryList(attribute.getCategoryStrList().stream().collect(Collectors.joining(",")));
            }
            attributeList.add(productAttribute);
            if (AttributeTypeEnum.VALUE.getType().equals(attribute.getType()) && attribute.getScope() != null) {
                SysProductAttributeInfoScope scope = BeanUtil.mapperBean(attribute.getScope(), SysProductAttributeInfoScope.class);
                scope.setParentId(productAttribute.getId());
                scope.setSysProductId(requestDTO.getId());
                scope.setSysProductCode(requestDTO.getCode());
                scopeList.add(scope);
                continue;
            }
            if (CollectionUtils.isEmpty(attribute.getInfos())) {
                continue;
            }
            attribute.getInfos().forEach(info -> {
                SysProductAttributeInfo attributeInfo = BeanUtil.mapperBean(info, SysProductAttributeInfo.class);
                attributeInfo.setSysAttrId(productAttribute.getId());
                attributeInfo.setSysProductId(requestDTO.getId());
                attributeInfo.setSysProductCode(requestDTO.getCode());
                infoList.add(attributeInfo);
            });
        }
    }


    private void checkAdd(SysProductDTO requestDTO) {
        int count = count(new LambdaQueryWrapper<SysProduct>().eq(SysProduct::getType,requestDTO.getType()).last("limit 1"));
        if (count > 0){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"该系统已存在！");
        }
    }
    private void checkUpdate(SysProductDTO requestDTO) {
        SysProduct product = getById(requestDTO.getId());
        if (product.getType().equals(requestDTO.getType())){
            return;
        }
        checkAdd(requestDTO);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSysProdut(SysProductDTO requestDTO) {
        if (UPDATE_FLAG.equals(requestDTO.getUpdateFalg())) {
            boolean useFalg = isUserdProject(requestDTO.getId());
            if (useFalg){
                throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"系统已绑定不可修改！");
            }
            SysProduct product = BeanUtil.mapperBean(requestDTO, SysProduct.class);
            checkUpdate(requestDTO);
            updateById(product);
            //1 可以修改
            //删除数据
            iSysProductAttributeService.deleteProductAttribures(requestDTO.getId());
            iSysProductCategoryService.deleteBySysProductId(requestDTO.getId());
            //新增数据
            saveAttribute(requestDTO);
            //保存系统产品品类信息
            iSysProductCategoryService.saveBathProductCategory(requestDTO.getId(),requestDTO.getCode(),requestDTO.getCategorys());
        } else {
            //不能修改系统属性
            //新增数据
            iSysProductCategoryService.deleteBySysProductId(requestDTO.getId());
            //保存系统产品品类信息
            iSysProductCategoryService.saveBathProductCategory(requestDTO.getId(),requestDTO.getCode(),requestDTO.getCategorys());
        }
    }

    /**
     * 判断系统是否 被项目绑定
     * @param sysPid
     * @return
     */
    private boolean isUserdProject(Long sysPid ){
        return this.baseMapper.isUserdProject(sysPid)>0?true:false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSysProdutById(Long sysProductId) {
        if (iHomeAutoProjectService.exsistSysPruduct(sysProductId)){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"系统已关联项目不可删除");
        }
        removeById(sysProductId);
        iSysProductAttributeService.deleteProductAttribures(sysProductId);
        iSysProductCategoryService.deleteBySysProductId(sysProductId);
    }

    @Override
    public SysProductDetailVO getDetailSysProdut(Long sysProductId) {
        SysProduct sysProduct = getById(sysProductId);
        SysProductDetailVO result = BeanUtil.mapperBean(sysProduct,SysProductDetailVO.class);
        //获取系统属性
        List<SysProductAttributeVO> sysProductAttributeVOS = iSysProductAttributeService.getListAttrVOBySysProductId(sysProductId);
        if (!CollectionUtils.isEmpty(sysProductAttributeVOS)){
            Map<Integer,List<SysProductAttributeVO>> dataMap =  sysProductAttributeVOS.stream().collect(Collectors.groupingBy(SysProductAttributeVO::getFunctionType));
            result.setAttributesBase(dataMap.get(CategoryAttributeTypeEnum.BASE.getType()));
            result.setAttributesFunc(dataMap.get(CategoryAttributeTypeEnum.FEATURES.getType()));
        }
        //获取系统关联的品类
        List<SysProductCategoryVO> categoryVos = iSysProductCategoryService.getListSysProductCategoryVO(sysProductId);
        if (!CollectionUtils.isEmpty(categoryVos)){
            result.setCategorys(categoryVos);
        }
        return result;
    }

    @Override
    public List<ScreenProductAttrCategoryBO> getAllAttrByCode(String productCode) {
        List<ScreenProductAttrCategoryBO> result = Lists.newArrayList();
        List<SysProductAttribute> productAttributeBOS = iSysProductAttributeService.getByProductCode(productCode);
        Map<Long, List<SysProductAttributeInfo>> attrInfoMap = getAttrInfoMap(productCode);
        Map<Long, List<SysProductAttributeInfoScope>> attrScopeMap = getAttrScopeMap(productCode);

        if (!CollectionUtils.isEmpty(productAttributeBOS)) {
            result.addAll(productAttributeBOS.stream().map(i -> {
                ScreenProductAttrCategoryBO bo = new ScreenProductAttrCategoryBO();
                bo.setFunctionType(i.getFunctionType());
                bo.setSysAttrBO(buildAttrBo(i, attrInfoMap, attrScopeMap));
                return bo;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public List<SysProductVO> getList(SysProductQryDTO request) {
        LambdaQueryWrapper<SysProduct> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(request.getName())){
            wrapper.like(SysProduct::getName,request.getName());
        }
        List<SysProduct> sysProducts = list(wrapper);
        if (CollectionUtils.isEmpty(sysProducts)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SysProductVO> result = BeanUtil.mapperList(sysProducts,SysProductVO.class);
        //获取关联的品类数
        List<Long> sysPids = result.stream().map(obj->{
            return obj.getId();
        }).collect(Collectors.toList());
        //品类数
        Map<Long,Integer> countMapCa = iSysProductCategoryService.getCountBySysPids(sysPids);
        //获取关联项目数
        Map<Long,Integer> countMpaP = iHomeAutoProjectService.getCountBySysPids(sysPids);
        //可选择的产品数量 todo
//        iSysProductCategoryService.getListCategoryBySysPid()
//        getListProductSelectByCategoryCode（
        result.forEach(obj->{
            obj.setCategoryNum(countMapCa.get(obj.getId())==null?0:countMapCa.get(obj.getId()));
            obj.setProjectNum(countMpaP.get(obj.getId())==null?0:countMpaP.get(obj.getId()));
        });
        return result;

    }



    @Override
    public void enableSwitch(SysProductStatusDTO request) {
        SysProduct sysProduct = BeanUtil.mapperBean(request,SysProduct.class);
        sysProduct.setId(request.getSysProductid());
        updateById(sysProduct);
    }

    @Override
    public List<ProductInfoSelectVO> getListProductSelectByCategoryCode(String categoryCode) {
        //获取系统某一品类下的属性和属性值信息
        List<ProductAttrInfoBO> attributeBOS = iSysCategoryAttributeService.getAttributeAndValByCategoryCode(categoryCode);
        List<ProductAttrInfoBO> productAttributeBOS = iProductAttributeService.getAttributeAndValByCategoryCode(categoryCode);
        List<Long> pidList = filterPid(attributeBOS,productAttributeBOS);
        return iHomeAutoProductService.getListProductSelectByPids(pidList);
    }

    @Override
    public List<SelectedLongVO> getSelectList() {
        List<SysProduct> sysproducts = list(new LambdaQueryWrapper<SysProduct>().eq(SysProduct::getStatus, StatusEnum.ENABLE.getType()).select(SysProduct::getId,SysProduct::getName));
        if (CollectionUtils.isEmpty(sysproducts)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SelectedLongVO> data = sysproducts.stream().map(p-> new SelectedLongVO(p.getName(),p.getId())).collect(Collectors.toList());
        return data;
    }

    @Override
    public SysProduct getSysProductByProjectId(Long projectId) {
        return this.baseMapper.getSysProductByProjectId(projectId);
    }

    @Override
    public List<SysProduct> getSysProductByPids(List<Long> sysPids) {
        if (CollectionUtils.isEmpty(sysPids)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SysProduct> data = list(new LambdaQueryWrapper<SysProduct>().in(SysProduct::getId,sysPids).select(SysProduct::getId,SysProduct::getCode,SysProduct::getName));
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    /**
     * 根据系统品类下的属性，过滤单品
     * @param attributeBOS
     * @param productAttributeBOS
     * @return
     */
    private List<Long> filterPid(List<ProductAttrInfoBO> attributeBOS, List<ProductAttrInfoBO> productAttributeBOS) {
        List<Long> pids = Lists.newArrayList();
        if (CollectionUtils.isEmpty(productAttributeBOS)){
            return pids;
        }
        if (CollectionUtils.isEmpty(attributeBOS)){
            //如果说系统品类的属性是空则 不需过滤
            return productAttributeBOS.stream().map(ProductAttrInfoBO::getProductId).collect(Collectors.toList());
        }
        //根据系统品类属性过滤 属性和值拼接成字符串比较
        StringBuilder sb = new StringBuilder();
        attributeBOS.forEach(attr->{
            sb.append(attr.getCode());
            if (!CollectionUtil.isEmpty(attr.getValues())){
                attr.getValues().forEach(value->{
                    sb.append(value.getCode());
                });
            }
        });
        String chekStr = sb.toString();
        Map<Long,List<ProductAttrInfoBO>> dataMap = productAttributeBOS.stream().collect(Collectors.groupingBy(ProductAttrInfoBO::getProductId));
        dataMap.forEach((pid,attrList)->{
            if (attrList.size() == attributeBOS.size()){
                StringBuilder sbP = new StringBuilder();
                attrList.forEach(attr->{
                    sbP.append(attr.getCode());
                    if (!CollectionUtil.isEmpty(attr.getValues())){
                        attr.getValues().forEach(value->{
                            sbP.append(value.getCode());
                        });
                    }
                });
                if (chekStr.equals(sbP.toString())){
                    pids.add(pid);
                }
            }
        });
        return pids;
    }


    private Map<Long, List<SysProductAttributeInfo>> getAttrInfoMap(String productCode) {
        Map<Long, List<SysProductAttributeInfo>> attrInfoMap = Maps.newConcurrentMap();
        List<SysProductAttributeInfo> attributeInfoDOS = iSysProductAttributeInfoService.getByProductCode(productCode);

        if (!CollectionUtils.isEmpty(attributeInfoDOS)) {
            attrInfoMap = attributeInfoDOS.stream().collect(Collectors.groupingBy(SysProductAttributeInfo::getSysAttrId));
        }
        return attrInfoMap;
    }

    private Map<Long, List<SysProductAttributeInfoScope>> getAttrScopeMap(String productCode) {
        Map<Long, List<SysProductAttributeInfoScope>> attrScopeMap = Maps.newConcurrentMap();

        List<SysProductAttributeInfoScope> attrScopeDOs = iSysProductAttributeInfoScopeService.getByProductCode(productCode);
        if (!CollectionUtils.isEmpty(attrScopeDOs)) {
            attrScopeMap = attrScopeDOs.stream().collect(Collectors.groupingBy(SysProductAttributeInfoScope::getParentId));
        }

        return attrScopeMap;
    }

    private ScreenSysProductAttrBO buildAttrBo(SysProductAttribute attributeDO, Map<Long, List<SysProductAttributeInfo>> attrInfoMap,
                                               Map<Long, List<SysProductAttributeInfoScope>> attrScopeMap) {
        ScreenSysProductAttrBO result = new ScreenSysProductAttrBO();
        List<SysProductAttributeInfo> infos = attrInfoMap.get(attributeDO.getId());
        List<SysProductAttributeInfoScope> scopes = attrScopeMap.get(attributeDO.getId());
        ScreenSysProductAttrValueBO attrValue = new ScreenSysProductAttrValueBO();
        attrValue.setType(attributeDO.getType());
        AttributeTypeEnum typeEnum = AttributeTypeEnum.getInstByType(attributeDO.getType());
        switch (typeEnum) {
            case MULTIPLE_CHOICE:
                attrValue.setSelectValues(infos);
                break;
            case VALUE:
                attrValue.setNumValue(CollectionUtils.isEmpty(scopes)?null:scopes.get(0));
            default:
                break;
        }
        result.setAttrCode(attributeDO.getCode());
        result.setAttrValue(attrValue);
        return result;
    }
}
