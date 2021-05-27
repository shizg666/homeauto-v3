package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysCategoryAttribute;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysCategoryAttributeInfo;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysProductCategory;
import com.landleaf.homeauto.center.device.model.mapper.SysProductCategoryMapper;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.CountLongBO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysCategoryAttributeDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysCategoryAttributeVO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductCategoryDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductCategoryVO;
import com.landleaf.homeauto.center.device.service.mybatis.ISysCategoryAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.ISysCategoryAttributeService;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductCategoryService;
import com.landleaf.homeauto.common.enums.category.CategoryAttributeTypeEnum;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
            buildAttrData(sysProductId,sysProductCode,category.getCategoryCode(),attributeList, infoList , CategoryAttributeTypeEnum.FEATURES.getType(),category.getAttributesFunc());
            buildAttrData(sysProductId,sysProductCode,category.getCategoryCode(),attributeList, infoList, CategoryAttributeTypeEnum.BASE.getType(), category.getAttributesBase());
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
    public List<SysProductCategoryVO> getListSysProductCategoryVO(Long sysProductId) {
        List<SysProductCategory> productCategorys = list(new LambdaQueryWrapper<SysProductCategory>().eq(SysProductCategory::getSysProductId,sysProductId).select(SysProductCategory::getCategoryCode,SysProductCategory::getCategoryNum));
        if (CollectionUtils.isEmpty(productCategorys)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SysProductCategoryVO> result = BeanUtil.mapperList(productCategorys,SysProductCategoryVO.class);
        List<String> categoryCodes = productCategorys.stream().map(o->{return o.getCategoryCode();}).distinct().collect(Collectors.toList());
        //获取系统产品关联品类的属性信息
        List<SysCategoryAttributeVO> categoryAttributeVOS = iSysCategoryAttributeService.getListAttrVOBySysProductId(sysProductId);
        if (CollectionUtils.isEmpty(categoryAttributeVOS)){
            return result;
        }
        Map<String,List<SysCategoryAttributeVO>> attrMap = categoryAttributeVOS.stream().collect(Collectors.groupingBy(SysCategoryAttributeVO::getCategoryCode));
        //获取品类下是否 有项目配置了该品类的设备
        Set<String> configCa = getCategoryUpdateFalg(sysProductId).stream().distinct().collect(Collectors.toSet());
        result.forEach(obj->{
            if (!CollectionUtils.isEmpty(configCa) && configCa.contains(obj.getCategoryCode())){
                obj.setUpdateFlag(0);
            }else {
                obj.setUpdateFlag(1);
            }
            //获取品类属性
            List<SysCategoryAttributeVO> attributeVOS = attrMap.get(obj.getCategoryCode());
            if(!CollectionUtils.isEmpty(attributeVOS)){
                Map<Integer,List<SysCategoryAttributeVO>> attrTypeMap = attributeVOS.stream().collect(Collectors.groupingBy(SysCategoryAttributeVO::getFunctionType));
                obj.setAttributesBase(attrTypeMap.get(CategoryAttributeTypeEnum.BASE.getType()));
                obj.setAttributesFunc(attrTypeMap.get(CategoryAttributeTypeEnum.FEATURES.getType()));
            }
        });
        return result;
    }

    /**
     * 获取系统产品关联的品类 是否可修改  项目下户型配置了子设备就不可修改了
     * @param sysPid
     * @return
     */
    private List<String> getCategoryUpdateFalg(Long sysPid) {
        List<String> data = this.baseMapper.getCategoryUpdateFalg(sysPid);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }


    @Override
    public Map<Long, Integer> getCountBySysPids(List<Long> sysPids) {
        if (CollectionUtils.isEmpty(sysPids)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        List<CountLongBO> countLongBOS = this.baseMapper.getCountBySysPids(sysPids);
        if (CollectionUtils.isEmpty(sysPids)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        return countLongBOS.stream().collect(Collectors.toMap(CountLongBO::getId,CountLongBO::getCount));
    }


    /**
     * 构建属性
     * @param type
     * @param attributes
     */
    private void buildAttrData(Long sysProductId, String sysProductCode, String categotyCode,List<SysCategoryAttribute> attributeList, List<SysCategoryAttributeInfo> infoList, Integer type, List<SysCategoryAttributeDTO> attributes) {
        if (CollectionUtils.isEmpty(attributes)) {
            return;
        }
        for (SysCategoryAttributeDTO attribute : attributes) {
            SysCategoryAttribute categoryAttribute = BeanUtil.mapperBean(attribute, SysCategoryAttribute.class);
            categoryAttribute.setSysProductId(sysProductId);
            categoryAttribute.setSysProductCode(sysProductCode);
            categoryAttribute.setId(idService.getSegmentId());
            categoryAttribute.setFunctionType(type);
            categoryAttribute.setCategoryCode(categotyCode);
            attributeList.add(categoryAttribute);
            if (CollectionUtils.isEmpty(attribute.getInfos())) {
                continue;
            }
            attribute.getInfos().forEach(info -> {
                SysCategoryAttributeInfo attributeInfo = BeanUtil.mapperBean(info, SysCategoryAttributeInfo.class);
                attributeInfo.setSysAttrId(categoryAttribute.getId());
                attributeInfo.setSysProductId(sysProductId);
                attributeInfo.setSysProductCode(sysProductCode);
                attributeInfo.setCategoryCode(categotyCode);
                infoList.add(attributeInfo);
            });
        }
    }
}
