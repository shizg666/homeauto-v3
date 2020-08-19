package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.category.CategoryAttribute;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategoryAttribute;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoCategoryMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.BaudRateEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @Autowired
    private ICategoryAttributeService iCategoryAttributeService;
    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CategoryDTO request) {
        checkAdd(request);
        HomeAutoCategory homeAutoCategory = BeanUtil.mapperBean(request,HomeAutoCategory.class);
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getInstByType(request.getCode());
        if (categoryTypeEnum ==null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该品类code不存在！");
        }
        homeAutoCategory.setName(categoryTypeEnum.getName());
        save(homeAutoCategory);

        saveAttribute(request.setId(homeAutoCategory.getId()));
    }

    /**
     * 品类属性保存
     * @param request
     */
    private void saveAttribute(CategoryDTO request) {
        if (CollectionUtils.isEmpty(request.getAttributes())){
            return;
        }
        String id = request.getId();
        List<CategoryAttribute> InfoSaveData = Lists.newArrayList();
        request.getAttributes().forEach(attribute->{
            CategoryAttribute categoryAttribute = new CategoryAttribute();
            categoryAttribute.setAttributeId(attribute);
            categoryAttribute.setCategoryId(id);
            InfoSaveData.add(categoryAttribute);
        });
        iCategoryAttributeService.saveBatch(InfoSaveData);
    }

    private void checkAdd(CategoryDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoCategory>().eq(HomeAutoCategory::getCode,request.getCode()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该品类已存在！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CategoryDTO request) {
        checkUpdate(request);
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getInstByType(request.getCode());
        if (categoryTypeEnum ==null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该品类code不存在！");
        }
        HomeAutoCategory category = BeanUtil.mapperBean(request,HomeAutoCategory.class);
        category.setName(categoryTypeEnum.getName());
        updateById(category);
        deleteAttributeAndInfo(request.getId());
        saveAttribute(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String categoryId) {
        int count = iHomeAutoProductService.count(new LambdaQueryWrapper<HomeAutoProduct>().eq(HomeAutoProduct::getCategoryId,categoryId));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"品类下有产品不可删除！");
        }
        removeById(categoryId);
        deleteAttributeAndInfo(categoryId);
    }

    @Override
    public BasePageVO<CategoryPageVO>   pageList(CategoryQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        LambdaQueryWrapper<HomeAutoCategory> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(request.getCode())){
            queryWrapper.eq(HomeAutoCategory::getCode,request.getCode());
        }
        queryWrapper.select(HomeAutoCategory::getId,HomeAutoCategory::getCode,HomeAutoCategory::getName,HomeAutoCategory::getProtocol);
        List<HomeAutoCategory> categories = list(queryWrapper);
        if (CollectionUtils.isEmpty(categories)){
            PageInfo pageInfo = new PageInfo(categories);
            BasePageVO<CategoryPageVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
            return resultData;
        }
        List<CategoryPageVO> result = BeanUtil.mapperList(categories,CategoryPageVO.class);
        List<String> categoryIds = categories.stream().map(HomeAutoCategory::getId).collect(Collectors.toList());
        List<CategoryAttributeVO> attributeVOS = iCategoryAttributeService.getAttributesByCategoryIds(categoryIds);
        Map<String,List<CategoryAttributeVO>> attributeMap = attributeVOS.stream().collect(Collectors.groupingBy(CategoryAttributeVO::getCategoryId));
        result.forEach(obj->{
            obj.setAttributes(attributeMap.get(obj.getId()));
        });
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<CategoryPageVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
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

    /**
     * 删除类别关联的属性和属性值
     */
    private void deleteAttributeAndInfo(String categoryId) {
      iCategoryAttributeService.remove(new LambdaQueryWrapper<CategoryAttribute>().eq(CategoryAttribute::getCategoryId,categoryId));
    }


    private void checkUpdate(CategoryDTO request) {
        //todo
//        int count = this.getBaseMapper().countDeviceByCategoryType(request.getType());
//        if (count >0){
//            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该品类已有设备存在不可修改！");
//        }
        String code = this.getBaseMapper().getTypeById(request.getId());
        if (request.getCode().equals(code)){
            return;
        }
        checkAdd(request);

    }
}
