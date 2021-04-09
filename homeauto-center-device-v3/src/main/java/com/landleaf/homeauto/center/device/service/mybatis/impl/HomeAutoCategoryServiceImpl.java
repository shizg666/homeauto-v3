package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.model.domain.category.CategoryAttribute;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoCategoryMapper;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.CategoryAttributeTypeEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.category.ProtocolEnum;
import com.landleaf.homeauto.common.enums.category.StatusEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 品类表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Service
public class HomeAutoCategoryServiceImpl extends ServiceImpl<HomeAutoCategoryMapper, HomeAutoCategory> implements IHomeAutoCategoryService {

    public static final Integer UPDATE_FLAG = 1;

    @Autowired
    private ICategoryAttributeService iCategoryAttributeService;
    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CategoryDTO request) {
        checkAdd(request);
        HomeAutoCategory homeAutoCategory = BeanUtil.mapperBean(request, HomeAutoCategory.class);
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getInstByType(request.getCode());
        if (categoryTypeEnum == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该品类code不存在！");
        }
        homeAutoCategory.setName(categoryTypeEnum.getName());
        homeAutoCategory.setStatus(StatusEnum.ENABLE.getType());
        save(homeAutoCategory);

        saveAttribute(request.setId(homeAutoCategory.getId()));
    }

    /**
     * 品类属性保存
     *
     * @param request
     */
    private void saveAttribute(CategoryDTO request) {
        List<CategoryAttribute> InfoSaveData = Lists.newArrayList();
        Long id = request.getId();
        if (!CollectionUtils.isEmpty(request.getAttributes1()) ) {
            request.getAttributes1().forEach(attribute -> {
                CategoryAttribute categoryAttribute = new CategoryAttribute();
                categoryAttribute.setAttributeId(attribute);
                categoryAttribute.setCategoryId(id);
                categoryAttribute.setFunctionType(CategoryAttributeTypeEnum.FEATURES.getType());
                InfoSaveData.add(categoryAttribute);
            });
        }
        if (!CollectionUtils.isEmpty(request.getAttributes2()) ) {
            request.getAttributes1().forEach(attribute -> {
                CategoryAttribute categoryAttribute = new CategoryAttribute();
                categoryAttribute.setAttributeId(attribute);
                categoryAttribute.setCategoryId(id);
                categoryAttribute.setFunctionType(CategoryAttributeTypeEnum.BASE.getType());
                InfoSaveData.add(categoryAttribute);
            });
        }
        iCategoryAttributeService.saveBatch(InfoSaveData);
    }

    private void checkAdd(CategoryDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoCategory>().eq(HomeAutoCategory::getCode, request.getCode()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该品类已存在！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CategoryDTO request) {
        checkUpdate(request);
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getInstByType(request.getCode());
        if (categoryTypeEnum == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该品类code不存在！");
        }
        HomeAutoCategory category = BeanUtil.mapperBean(request, HomeAutoCategory.class);
        category.setName(categoryTypeEnum.getName());
        category.setStatus(StatusEnum.ENABLE.getType());
        updateById(category);
        boolean exitFlag = this.exsitCategoryProduct(request.getId());
        if (UPDATE_FLAG.equals(request.getUpdateFalg()) && !exitFlag){
            saveAttribute(request);
        }else {
            deleteAttributeAndInfo(request.getId());
            saveAttribute(request);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long categoryId) {
        int count = iHomeAutoProductService.count(new LambdaQueryWrapper<HomeAutoProduct>().eq(HomeAutoProduct::getCode, categoryId));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "品类下有产品不可删除！");
        }
        removeById(categoryId);
        deleteAttributeAndInfo(categoryId);
    }

    @Override
    public BasePageVO<CategoryPageVO> pageList(CategoryQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        LambdaQueryWrapper<HomeAutoCategory> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(request.getCode())) {
            queryWrapper.eq(HomeAutoCategory::getCode, request.getCode());
        }
        queryWrapper.select(HomeAutoCategory::getId, HomeAutoCategory::getCode, HomeAutoCategory::getName, HomeAutoCategory::getCode,HomeAutoCategory::getStatus).orderByDesc(HomeAutoCategory::getCreateTime);
        List<HomeAutoCategory> categories = list(queryWrapper);
        if (CollectionUtils.isEmpty(categories)) {
            PageInfo pageInfo = new PageInfo(categories);
            BasePageVO<CategoryPageVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
            return resultData;
        }
        List<CategoryPageVO> result = BeanUtil.mapperList(categories, CategoryPageVO.class);
        //获取产品数量
        List<TotalCountBO> data = iHomeAutoProductService.getCountGroupByCategory(null);
        Map<String,Integer> count = null;
        if (!CollectionUtils.isEmpty(data)){
            count = data.stream().collect(Collectors.toMap(TotalCountBO::getPid,TotalCountBO::getCount));
        }
        for (CategoryPageVO obj : result) {
            //产品数量
            if (Objects.nonNull(count) && count.containsKey(obj.getCode())) {
                obj.setProductCount(count.get(obj.getCode()));
            } else {
                obj.setProductCount(0);
            }
        }
        PageInfo pageInfo = new PageInfo(categories);
        pageInfo.setList(result);
        BasePageVO<CategoryPageVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }


    @Override
    public List<SelectedVO> getCategorys() {
        List<SelectedVO> selectedVOS = Lists.newArrayList();
        for (CategoryTypeEnum value : CategoryTypeEnum.values()) {
            SelectedVO cascadeVo = new SelectedVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }


    @Override
    public List<SelectedLongVO> getListSelectedVO() {
        List<HomeAutoCategory> categories = list(new LambdaQueryWrapper<HomeAutoCategory>().select(HomeAutoCategory::getId, HomeAutoCategory::getName));
        if (CollectionUtils.isEmpty(categories)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedLongVO> selectedVOS = Lists.newArrayListWithCapacity(categories.size());
        categories.forEach(obj -> {
            SelectedLongVO cascadeVo = new SelectedLongVO(obj.getName(), obj.getId());
            selectedVOS.add(cascadeVo);
        });
        return selectedVOS;
    }

    @Override
    public String getCategoryCodeById(Long categoryId) {
        return this.baseMapper.getCategoryCodeById(categoryId);
    }

    /**
     * 删除类别关联的属性和属性值
     */
    private void deleteAttributeAndInfo(Long categoryId) {
        iCategoryAttributeService.remove(new LambdaQueryWrapper<CategoryAttribute>().eq(CategoryAttribute::getCategoryId, categoryId));
    }


    private void checkUpdate(CategoryDTO request) {
        String code = this.getBaseMapper().getTypeById(request.getId());
        if (request.getCode().equals(code)) {
            return;
        }
        checkAdd(request);

    }

    @Override
    public List<HomeAutoCategory> listByCode(CategoryEnum... categoryEnum) {
        List<Integer> categoryCodeList = Arrays.stream(categoryEnum).map(CategoryEnum::getType).collect(Collectors.toList());
        List<String> categoryCodeStringList = categoryCodeList.stream().map(Objects::toString).collect(Collectors.toList());
        QueryWrapper<HomeAutoCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("code", categoryCodeStringList);
        return list(queryWrapper);
    }

    @Override
    public void switchStatus(Long categoryId) {
        HomeAutoCategory category = getById(categoryId);
        category.setStatus((category.getStatus()+1)%2);
        updateById(category);
    }

    @Override
    public CategoryAttributeInfoVO getCategorysAttrInfoList(Long categoryId) {
        List<CategoryAttributeDTO> attributeDTOS = iCategoryAttributeService.getListAttrbuteInfo(categoryId);
        if(CollectionUtils.isEmpty(attributeDTOS)){
            return null;
        }
        Map<Integer,List<CategoryAttributeDTO>> data = attributeDTOS.stream().collect(Collectors.groupingBy(CategoryAttributeDTO::getFunctionType));
        return CategoryAttributeInfoVO.builder().attrsInfo1(data.get(CategoryAttributeTypeEnum.FEATURES.getType())).attrsInfo2(data.get(CategoryAttributeTypeEnum.FEATURES.getType())).build();

    }

    @Override
    public boolean exsitCategoryProduct(Long categoryId) {
        int count = this.baseMapper.exsitCategoryProduct(categoryId);
        return count > 0?true:false;
    }

    @Override
    public CategoryAttrVO getCategoryAttrs(Long categoryId) {
        CategoryAttrVO categoryAttrVO = new CategoryAttrVO();
        List<CategoryAttributeVO> attributeVOS = iCategoryAttributeService.getAttributesByCategoryIds(Lists.newArrayList(categoryId));
        Map<Long, List<CategoryAttributeVO>> attributeMap = attributeVOS.stream().collect(Collectors.groupingBy(CategoryAttributeVO::getCategoryId));
        //拼装属性
        if (Objects.isNull(attributeMap)) {
            List<CategoryAttributeVO> attrList = attributeMap.get(categoryId);
            Map<Integer, List<CategoryAttributeVO>> attriMap = attrList.stream().collect(Collectors.groupingBy(CategoryAttributeVO::getFunctionType));
            categoryAttrVO.setAttributes1(attriMap.get(CategoryAttributeTypeEnum.FEATURES.getType()));
            categoryAttrVO.setAttributes2(attriMap.get(CategoryAttributeTypeEnum.BASE.getType()));
        }
        return categoryAttrVO;
    }
}
