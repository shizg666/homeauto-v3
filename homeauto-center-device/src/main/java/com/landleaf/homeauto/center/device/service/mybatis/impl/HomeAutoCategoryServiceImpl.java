package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoCategoryMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryService;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategory;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategoryAttribute;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategoryAttributeInfo;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeInfoDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryDTO;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
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
    private IHomeAutoCategoryAttributeService iHomeAutoCategoryAttributeService;
    @Autowired
    private IHomeAutoCategoryAttributeInfoService iHomeAutoCategoryAttributeInfoService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CategoryDTO request) {
        checkAdd(request);
        HomeAutoCategory homeAutoCategory = BeanUtil.mapperBean(request,HomeAutoCategory.class);
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getInstByType(request.getType());
        if (categoryTypeEnum !=null){
            homeAutoCategory.setName(categoryTypeEnum.getName());
            homeAutoCategory.setIcon(categoryTypeEnum.getIcon());
        }
        save(homeAutoCategory);
        if (CollectionUtils.isEmpty(request.getAttributes())){
            return;
        }
        String id = homeAutoCategory.getId();
        List<HomeAutoCategoryAttributeInfo> InfoSaveData = Lists.newArrayList();
        request.getAttributes().forEach(attribute->{
            HomeAutoCategoryAttribute categoryAttribute = BeanUtil.mapperBean(attribute,HomeAutoCategoryAttribute.class);
            categoryAttribute.setCategoryId(id);
            if (!CollectionUtils.isEmpty(attribute.getInfos())){
                //值域属性类型
                if (AttributeTypeEnum.RANGE.getType().equals(attribute.getType())){
                    Map<String,String> infoMap =  attribute.getInfos().stream().collect(Collectors.toMap(CategoryAttributeInfoDTO::getCode,CategoryAttributeInfoDTO::getName));
                    categoryAttribute.setPrecision(infoMap.get("precision"));
                    categoryAttribute.setPrecision(infoMap.get("max"));
                    categoryAttribute.setPrecision(infoMap.get("min"));
                    categoryAttribute.setPrecision(infoMap.get("step"));
                    iHomeAutoCategoryAttributeService.save(categoryAttribute);
                }else {
                    iHomeAutoCategoryAttributeService.save(categoryAttribute);
                    List<HomeAutoCategoryAttributeInfo> attributeInfos = BeanUtil.mapperList(attribute.getInfos(),HomeAutoCategoryAttributeInfo.class);
                    attributeInfos.forEach(obj->{
                        obj.setAttributeId(categoryAttribute.getId());
                    });
                    InfoSaveData.addAll(attributeInfos);
                }
            }
        });
        iHomeAutoCategoryAttributeInfoService.saveBatch(InfoSaveData);
    }

    private void checkAdd(CategoryDTO request) {
        int count = count(new LambdaQueryWrapper<HomeAutoCategory>().eq(HomeAutoCategory::getType,request.getType()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该品类已存！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CategoryDTO request) {
        checkUpdate(request);
        deleteAttributeAndInfo(request.getId());
        add(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String categoryId) {
        removeById(categoryId);
        deleteAttributeAndInfo(categoryId);
    }

    /**
     * 删除类别关联的属性和属性值
     */
    private void deleteAttributeAndInfo(String categoryId) {
        List<String> ids = iHomeAutoCategoryAttributeService.getIdListByCategoryId(categoryId);
        if (CollectionUtils.isEmpty(ids)){
            return;
        }
        iHomeAutoCategoryAttributeService.remove(new LambdaQueryWrapper<HomeAutoCategoryAttribute>().eq(HomeAutoCategoryAttribute::getCategoryId,categoryId));
        iHomeAutoCategoryAttributeInfoService.remove(new LambdaQueryWrapper<HomeAutoCategoryAttributeInfo>().in(HomeAutoCategoryAttributeInfo::getAttributeId,ids));
    }

    private void checkUpdate(CategoryDTO request) {
        //todo
        int count = this.getBaseMapper().countDeviceByCategoryType(request.getType());
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该品类已有设备存在不可修改！");
        }
    }
}
