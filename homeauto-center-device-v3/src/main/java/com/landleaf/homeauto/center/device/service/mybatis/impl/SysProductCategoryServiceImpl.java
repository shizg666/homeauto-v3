package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.sys_product.*;
import com.landleaf.homeauto.center.device.model.mapper.SysProductCategoryMapper;
import com.landleaf.homeauto.center.device.model.vo.sys_product.*;
import com.landleaf.homeauto.center.device.service.mybatis.ISysCategoryAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.ISysCategoryAttributeService;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductCategoryService;
import com.landleaf.homeauto.common.enums.category.CategoryAttributeTypeEnum;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 系统产品关联品类表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Service
public class SysProductCategoryServiceImpl extends ServiceImpl<SysProductCategoryMapper, SysProductCategory> implements ISysProductCategoryService {

    @Autowired
    private IdService idService;
    @Autowired
    private ISysCategoryAttributeService iSysCategoryAttributeService;
    @Autowired
    private ISysCategoryAttributeInfoService iSysCategoryAttributeInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBathCategorAttribute(Long sysProductId, String sysProductCode, List<SysProductCategoryDTO> categorys) {
        if(CollectionUtils.isEmpty(categorys)){
            return;
        }
        List<SysCategoryAttribute> attributeList = Lists.newArrayList();
        //系统类别属性值
        List<SysCategoryAttributeInfo> infoList = Lists.newArrayList();
        categorys.forEach(category->{
            //系统类别属性
            //功能属性
            buildAttrData(sysProductId,sysProductCode,attributeList, infoList , CategoryAttributeTypeEnum.FEATURES.getType(),category.getAttributesFunc());
            buildAttrData(sysProductId,sysProductCode,attributeList, infoList, CategoryAttributeTypeEnum.BASE.getType(), category.getAttributesBase());
        });
        iSysCategoryAttributeService.saveBatch(attributeList);
        iSysCategoryAttributeInfoService.saveBatch(infoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBathProductCategory(Long sysProductId, String sysProductCode, List<SysProductCategoryDTO> categorys) {
        if (CollectionUtils.isEmpty(categorys)){
            return;
        }
        List<SysProductCategory> productCategorys = BeanUtil.mapperList(categorys,SysProductCategory.class);
        productCategorys.forEach(obj->{
            obj.setSysProductCode(sysProductCode);
            obj.setSysProductId(sysProductId);
        });
        saveBatch(productCategorys);
        saveBathCategorAttribute(sysProductId,sysProductCode,categorys);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBySysProductId(Long sysProductId) {
        remove(new LambdaQueryWrapper<SysProductCategory>().eq(SysProductCategory::getSysProductId,sysProductId));
        iSysCategoryAttributeService.remove(new LambdaQueryWrapper<SysCategoryAttribute>().eq(SysCategoryAttribute::getSysProductId,sysProductId));
        iSysCategoryAttributeInfoService.remove(new LambdaQueryWrapper<SysCategoryAttributeInfo>().eq(SysCategoryAttributeInfo::getSysProductId,sysProductId));
    }

    @Override
    public List<SysProductAttributeVO> getListAttrVOBySysProductId(Long sysProductId) {
        return null;
    }

    @Override
    public List<SysProductAttributeDTO> getListAttrDTOBySysProductId(Long sysProductId) {
        return this.baseMapper.getListAttrDTOBySysProductId(sysProductId);
    }

    /**
     * 构建属性
     * @param type
     * @param attributes
     */
    private void buildAttrData(Long sysProductId, String sysProductCode,List<SysCategoryAttribute> attributeList, List<SysCategoryAttributeInfo> infoList, Integer type, List<SysCategoryAttributeDTO> attributes) {
        if (CollectionUtils.isEmpty(attributes)) {
            return;
        }
        for (SysCategoryAttributeDTO attribute : attributes) {
            SysCategoryAttribute categoryAttribute = BeanUtil.mapperBean(attribute, SysCategoryAttribute.class);
            categoryAttribute.setSysProductId(sysProductId);
            categoryAttribute.setSysProductCode(sysProductCode);
            categoryAttribute.setId(idService.getSegmentId());
            categoryAttribute.setFunctionType(type);
            attributeList.add(categoryAttribute);
            if (CollectionUtils.isEmpty(attribute.getInfos())) {
                continue;
            }
            attribute.getInfos().forEach(info -> {
                SysCategoryAttributeInfo attributeInfo = BeanUtil.mapperBean(info, SysCategoryAttributeInfo.class);
                attributeInfo.setSysAttrId(categoryAttribute.getId());
                attributeInfo.setSysProductId(sysProductId);
                attributeInfo.setSysProductCode(sysProductCode);
                infoList.add(attributeInfo);
            });
        }
    }
}
