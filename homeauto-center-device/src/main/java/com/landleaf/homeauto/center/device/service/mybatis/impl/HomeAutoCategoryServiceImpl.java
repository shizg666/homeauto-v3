package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoCategoryMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.category.CategoryAttribute;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategory;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategoryAttribute;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.*;
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
    private IHomeAutoCategoryAttributeService iHomeAutoCategoryAttributeService;
    @Autowired
    private IHomeAutoCategoryAttributeInfoService iHomeAutoCategoryAttributeInfoService;
    @Autowired
    private IHomeAutoAttributeDicService iHomeAutoAttributeDicService;

    @Autowired
    private ICategoryAttributeService iCategoryAttributeService;

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









//    private void saveAttribute22222(CategoryDTO request) {
//        if (CollectionUtils.isEmpty(request.getAttributes())){
//            return;
//        }
//        String id = request.getId();
//        List<HomeAutoCategoryAttributeInfo> InfoSaveData = Lists.newArrayList();
//        request.getAttributes().forEach(attribute->{
//            HomeAutoCategoryAttribute categoryAttribute = BeanUtil.mapperBean(attribute,HomeAutoCategoryAttribute.class);
//            categoryAttribute.setCategoryId(id);
//            if (!CollectionUtils.isEmpty(attribute.getInfos())){
//                //值域属性类型
//                if (AttributeTypeEnum.RANGE.getType().equals(attribute.getType())){
//                    Map<String,String> infoMap =  attribute.getInfos().stream().collect(Collectors.toMap(CategoryAttributeInfoDTO22222222::getName,CategoryAttributeInfoDTO22222222::getCode));
//                    categoryAttribute.setPrecision(Integer.valueOf(infoMap.get("precision")));
//                    categoryAttribute.setMax(infoMap.get("max"));
//                    categoryAttribute.setMin(infoMap.get("min"));
//                    categoryAttribute.setStep(infoMap.get("step"));
//                    iHomeAutoCategoryAttributeService.save(categoryAttribute);
//                }else {
//                    iHomeAutoCategoryAttributeService.save(categoryAttribute);
//                    List<HomeAutoCategoryAttributeInfo> attributeInfos = BeanUtil.mapperList(attribute.getInfos(),HomeAutoCategoryAttributeInfo.class);
//                    attributeInfos.forEach(obj->{
//                        obj.setAttributeId(categoryAttribute.getId());
//                    });
//                    InfoSaveData.addAll(attributeInfos);
//                }
//            }
//        });
//        iHomeAutoCategoryAttributeInfoService.saveBatch(InfoSaveData);
//    }

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
        removeById(categoryId);
        deleteAttributeAndInfo(categoryId);
    }

    @Override
    public BasePageVO<CategoryPageVO> pageList(CategoryQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        LambdaQueryWrapper<HomeAutoCategory> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtil.isEmpty(request.getCode())){
            queryWrapper.eq(HomeAutoCategory::getCode,request.getCode());
        }
        queryWrapper.select(HomeAutoCategory::getId,HomeAutoCategory::getCode,HomeAutoCategory::getName,HomeAutoCategory::getProtocol);
        List<HomeAutoCategory> categories = list(queryWrapper);
        if (CollectionUtils.isEmpty(categories)){
            PageInfo pageInfo = new PageInfo(null);
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

//    @Override
//    public CategoryDetailVO getDetailById(String id) {
//        HomeAutoCategory category = getById(id);
//        if (category == null){
//            return null;
//        }
//        CategoryDetailVO categoryDetailVO = BeanUtil.mapperBean(category,CategoryDetailVO.class);
//        List<CategoryAttributeBO22222222> attributeBOS = this.getBaseMapper().getAttributeInfosById(id);
//        if (CollectionUtils.isEmpty(attributeBOS)){
//            return categoryDetailVO;
//        }
//        List<CategoryDetailAttributeVO> attributes = Lists.newArrayListWithCapacity(attributeBOS.size());
//        attributeBOS.forEach(attribute->{
//            CategoryDetailAttributeVO detailAttributeVO = CategoryDetailAttributeVO.builder().code(attribute.getCode()).name(attribute.getName()).build();
//            StringBuilder attributeStr = new StringBuilder();
//            if (AttributeTypeEnum.RANGE.getType().equals(attribute.getType())){
//                    if (!StringUtil.isEmpty(attribute.getMax()) && !StringUtil.isEmpty(attribute.getMin())){
//                        attributeStr.append(",").append(attribute.getMin()).append("-").append(attribute.getMax());
//                    }
//                    if (attribute.getPrecision() != null){
//                        String precision = PrecisionEnum.getInstByType(attribute.getPrecision()) != null?PrecisionEnum.getInstByType(attribute.getPrecision()).getName():"";
//                        attributeStr.append(",").append(precision);
//                    }
//                if (!StringUtil.isEmpty(attribute.getStep())){
//                    attributeStr.append(",").append(attribute.getStep());
//                }
//            }else {
//                List<CategoryAttributeInfoDTO22222222> infos = attribute.getInfos();
//                if (!CollectionUtils.isEmpty(infos)){
//                    infos.forEach(info->{
//                        attributeStr.append(",").append(info.getName());
//                    });
//                }
//            }
//            String str = attributeStr.toString();
//            if (!StringUtil.isEmpty(str)){
//                detailAttributeVO.setInfoStr(str.substring(1,str.length()));
//            }
//            attributes.add(detailAttributeVO);
//        });
//        categoryDetailVO.setAttributes(attributes);
//        return categoryDetailVO;
//    }

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
    public CategoryAttributeVO2222222 getAttributeInfo(CategoryAttributeQryDTO request) {
        AttributeCascadeVO attributeCascadeVO = iHomeAutoAttributeDicService.getCascadeInfoByCode(request.getCode());
        HomeAutoCategoryAttribute categoryAttribute = iHomeAutoCategoryAttributeService.getById(request.getCategoryId());
        if (categoryAttribute == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "品类id不存在");
        }
        CategoryAttributeVO2222222 result = BeanUtil.mapperBean(categoryAttribute, CategoryAttributeVO2222222.class);
        List<AttributeInfoDicDTO> infoDicDTOs = iHomeAutoCategoryAttributeInfoService.getListByAttributeCode(request.getCategoryId(),request.getCode());
        if (CollectionUtils.isEmpty(attributeCascadeVO.getInfos())){
            return result;
        }
        if (CollectionUtils.isEmpty(infoDicDTOs)){
            List<CategoryAttributeInfoVO2222222> attributeVO = BeanUtil.mapperList(attributeCascadeVO.getInfos(), CategoryAttributeInfoVO2222222.class);
            return result.setInfos(attributeVO);
        }
        List<CategoryAttributeInfoVO2222222> infoVOS = Lists.newArrayListWithCapacity(attributeCascadeVO.getInfos().size());
        Set<String> codeSet = infoDicDTOs.stream().map(AttributeInfoDicDTO::getCode).collect(Collectors.toSet());
        attributeCascadeVO.getInfos().stream().forEach(obj->{
            CategoryAttributeInfoVO2222222 infoVO = BeanUtil.mapperBean(obj, CategoryAttributeInfoVO2222222.class);
            if (codeSet.contains(infoVO.getCode())){
                infoVO.setSelected(1);
            }
            infoVOS.add(infoVO);
        });
        result.setInfos(infoVOS);
        return result;
    }

    /**
     * 删除类别关联的属性和属性值
     */
    private void deleteAttributeAndInfo(String categoryId) {
      iCategoryAttributeService.remove(new LambdaQueryWrapper<CategoryAttribute>().eq(CategoryAttribute::getCategoryId,categoryId));
    }

//    private void deleteAttributeAndInfo222222222222(String categoryId) {
//        List<String> ids = iHomeAutoCategoryAttributeService.getIdListByCategoryId(categoryId);
//        if (CollectionUtils.isEmpty(ids)){
//            return;
//        }
//        iHomeAutoCategoryAttributeService.remove(new LambdaQueryWrapper<HomeAutoCategoryAttribute>().eq(HomeAutoCategoryAttribute::getCategoryId,categoryId));
//        iHomeAutoCategoryAttributeInfoService.remove(new LambdaQueryWrapper<HomeAutoCategoryAttributeInfo>().in(HomeAutoCategoryAttributeInfo::getAttributeId,ids));
//    }

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
