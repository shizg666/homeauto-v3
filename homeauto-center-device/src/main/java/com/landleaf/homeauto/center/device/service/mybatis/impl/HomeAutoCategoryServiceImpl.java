package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoCategoryMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryService;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoCategory;
import com.landleaf.homeauto.common.domain.vo.category.CategoryDTO;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        HomeAutoCategory homeAutoCategory = BeanUtil.mapperBean(request,HomeAutoCategory.class);
        save(homeAutoCategory);

    }

    @Override
    public void update(CategoryDTO request) {

    }
}
