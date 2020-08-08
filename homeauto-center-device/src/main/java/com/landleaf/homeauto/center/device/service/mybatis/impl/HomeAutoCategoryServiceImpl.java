package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoCategoryMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeInfoDicService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryService;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttributeDic;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategory;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategoryAttribute;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategoryAttributeInfo;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.*;
import com.landleaf.homeauto.common.enums.category.*;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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
    @Autowired
    private IHomeAutoAttributeInfoDicService iHomeAutoAttributeInfoDicService;

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
        saveAttribute(request.setId(homeAutoCategory.getId()));

    }

    /**
     * 品类属性和属性值保存
     * @param request
     */
    private void saveAttribute(CategoryDTO request) {
        if (CollectionUtils.isEmpty(request.getAttributes())){
            return;
        }
        String id = request.getId();
        List<HomeAutoCategoryAttributeInfo> InfoSaveData = Lists.newArrayList();
        request.getAttributes().forEach(attribute->{
            HomeAutoCategoryAttribute categoryAttribute = BeanUtil.mapperBean(attribute,HomeAutoCategoryAttribute.class);
            categoryAttribute.setCategoryId(id);
            if (!CollectionUtils.isEmpty(attribute.getInfos())){
                //值域属性类型
                if (AttributeTypeEnum.RANGE.getType().equals(attribute.getType())){
                    Map<String,String> infoMap =  attribute.getInfos().stream().collect(Collectors.toMap(CategoryAttributeInfoDTO::getName,CategoryAttributeInfoDTO::getVal));
                    categoryAttribute.setPrecision(Integer.valueOf(infoMap.get("precision")));
                    categoryAttribute.setMax(infoMap.get("max"));
                    categoryAttribute.setMin(infoMap.get("min"));
                    categoryAttribute.setStep(infoMap.get("step"));
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
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该品类已存在！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CategoryDTO request) {
        checkUpdate(request);
        HomeAutoCategory category = BeanUtil.mapperBean(request,HomeAutoCategory.class);
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
        if (request.getType() != null){
            queryWrapper.eq(HomeAutoCategory::getType,request.getType());
        }
        if (request.getProtocol() != null){
            queryWrapper.eq(HomeAutoCategory::getProtocol,request.getProtocol());
        }
        List<HomeAutoCategory> categories = list(queryWrapper);
        List<CategoryPageVO> result = BeanUtil.mapperList(categories,CategoryPageVO.class);
        PageInfo pageInfo = new PageInfo(result);
        BasePageVO<CategoryPageVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
        return resultData;
    }

    @Override
    public CategoryDetailVO getDetailById(String id) {
        HomeAutoCategory category = getById(id);
        if (category == null){
            return null;
        }
        CategoryDetailVO categoryDetailVO = BeanUtil.mapperBean(category,CategoryDetailVO.class);
        List<CategoryAttributeBO> attributeBOS = this.getBaseMapper().getAttributeInfosById(id);
        if (CollectionUtils.isEmpty(attributeBOS)){
            return categoryDetailVO;
        }
        List<CategoryDetailAttributeVO> attributes = Lists.newArrayListWithCapacity(attributeBOS.size());
        attributeBOS.forEach(attribute->{
            CategoryDetailAttributeVO detailAttributeVO = CategoryDetailAttributeVO.builder().code(attribute.getCode()).name(attribute.getName()).attributeId(attribute.getAttributeId()).build();
            StringBuilder attributeStr = new StringBuilder();
            if (AttributeTypeEnum.RANGE.getType().equals(attribute.getType())){
                    if (!StringUtil.isEmpty(attribute.getMax()) && !StringUtil.isEmpty(attribute.getMin())){
                        attributeStr.append(",").append(attribute.getMin()).append("-").append(attribute.getMax());
                    }
                    if (attribute.getPrecision() != null){
                        String precision = PrecisionEnum.getInstByType(attribute.getPrecision()) != null?PrecisionEnum.getInstByType(attribute.getPrecision()).getName():"";
                        attributeStr.append(",").append(precision);
                    }
                if (!StringUtil.isEmpty(attribute.getStep())){
                    attributeStr.append(",").append(attribute.getStep());
                }
            }else {
                List<CategoryAttributeInfoDTO> infos = attribute.getInfos();
                if (!CollectionUtils.isEmpty(infos)){
                    infos.forEach(info->{
                        attributeStr.append(",").append(info.getName());
                    });
                }
            }
            String str = attributeStr.toString();
            if (!StringUtil.isEmpty(str)){
                detailAttributeVO.setInfoStr(str.substring(1,str.length()));
            }
            attributes.add(detailAttributeVO);
        });
        categoryDetailVO.setAttributes(attributes);
        return categoryDetailVO;
    }

    @Override
    public List<SelectedVO> getProtocols() {
        List<SelectedVO> selectedVOS = Lists.newArrayList();
        for (ProtocolEnum value : ProtocolEnum.values()) {
            SelectedVO cascadeVo = new SelectedVO(value.getName(), String.valueOf(value.getType()));
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getBaudRates() {
        List<SelectedVO> selectedVOS = Lists.newArrayList();
        for (BaudRateEnum value : BaudRateEnum.values()) {
            SelectedVO cascadeVo = new SelectedVO(value.getName(), String.valueOf(value.getType()));
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }



    @Override
    public List<SelectedVO> getCheckModes() {
        List<SelectedVO> selectedVOS = Lists.newArrayList();
        for (CheckEnum value : CheckEnum.values()) {
            SelectedVO cascadeVo = new SelectedVO(value.getName(), String.valueOf(value.getType()));
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public CategoryAttributeVO getAttributeInfo(CategoryAttributeQryDTO request) {
        return null;
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
//        int count = this.getBaseMapper().countDeviceByCategoryType(request.getType());
//        if (count >0){
//            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"该品类已有设备存在不可修改！");
//        }
        Integer type = this.getBaseMapper().getTypeById(request.getId());
        if (request.getType().equals(type)){
            return;
        }
        int count = count(new LambdaQueryWrapper<HomeAutoCategory>().eq(HomeAutoCategory::getType,request.getType()));
        if (count >= 1){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该类型已存在");
        }

    }
}
