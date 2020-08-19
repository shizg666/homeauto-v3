package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoCategoryAttributeInfoMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryAttributeInfoService;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategoryAttributeInfo;
import com.landleaf.homeauto.common.domain.vo.category.AttributeInfoDicDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 品类属性值表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Service
public class HomeAutoCategoryAttributeInfoServiceImpl extends ServiceImpl<HomeAutoCategoryAttributeInfoMapper, HomeAutoCategoryAttributeInfo> implements IHomeAutoCategoryAttributeInfoService {

    @Override
    public List<AttributeInfoDicDTO> getListByAttributeCode(String categoryId,String code) {
        List<AttributeInfoDicDTO> data = this.baseMapper.getListByAttributeCode(categoryId,code);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }
}
