package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.category.CategoryAttribute;
import com.landleaf.homeauto.center.device.model.mapper.CategoryAttributeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.ICategoryAttributeService;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttrQryDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 品类属性信息表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Service
public class CategoryAttributeServiceImpl extends ServiceImpl<CategoryAttributeMapper, CategoryAttribute> implements ICategoryAttributeService {

    @Override
    public List<CategoryAttributeVO> getAttributesByCategoryIds(List<String> categoryIds) {
        if (CollectionUtils.isEmpty(categoryIds)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<CategoryAttributeVO> data = this.baseMapper.getAttributesByCategoryIds(categoryIds);
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public List<SelectedVO> getListAttrbute(String categoryId) {
        return this.baseMapper.getListAttrbute(categoryId);
    }

    @Override
    public CategoryAttributeDTO getAttrbuteDetail(CategoryAttrQryDTO request) {
        return this.baseMapper.getAttrbuteDetail(request);
    }

    @Override
    public List<CategoryAttributeDTO> getListAttrbuteInfo(String categoryId) {
        return this.baseMapper.getListAttrbuteInfo(categoryId);
    }


}
