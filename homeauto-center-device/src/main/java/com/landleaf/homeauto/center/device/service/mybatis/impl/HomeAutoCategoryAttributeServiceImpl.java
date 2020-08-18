package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoCategoryAttributeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeService;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategoryAttribute;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 品类属性信息表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Service
public class HomeAutoCategoryAttributeServiceImpl extends ServiceImpl<HomeAutoCategoryAttributeMapper, HomeAutoCategoryAttribute> implements IHomeAutoCategoryAttributeService {

    @Override
    public List<String> getIdListByCategoryId(String id) {
        List<String> result = this.baseMapper.getIdListByCategoryId(id);
        if (CollectionUtils.isEmpty(result)){
            return Lists.newArrayListWithCapacity(0);
        }
        return result;
    }
}
