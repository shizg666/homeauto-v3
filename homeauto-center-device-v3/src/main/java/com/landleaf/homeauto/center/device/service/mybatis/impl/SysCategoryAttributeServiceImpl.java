package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.sys_product.SysCategoryAttribute;
import com.landleaf.homeauto.center.device.model.mapper.SysCategoryAttributeMapper;
import com.landleaf.homeauto.center.device.model.vo.sys_product.*;
import com.landleaf.homeauto.center.device.service.mybatis.ISysCategoryAttributeService;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-25
 */
@Service
public class SysCategoryAttributeServiceImpl extends ServiceImpl<SysCategoryAttributeMapper, SysCategoryAttribute> implements ISysCategoryAttributeService {

    @Override
    public List<SysCategoryAttributeVO> getListAttrVOBySysProductId(Long sysProductId) {
        List<SysCategoryAttributeDTO> attributeDTOS = getListAttrDTOBySysProductId(sysProductId);
        if (CollectionUtils.isEmpty(attributeDTOS)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SysCategoryAttributeVO> data = BeanUtil.mapperList(attributeDTOS,SysCategoryAttributeVO.class);
        data.forEach(obj -> {
            if (AttributeTypeEnum.MULTIPLE_CHOICE.getType().equals(obj.getType())) {
                StringBuilder sb = new StringBuilder();
                List<SysProductAttributeInfoDTO> infoVOS = obj.getInfos();
                if (!CollectionUtils.isEmpty(infoVOS)) {
                    infoVOS.forEach(info -> {
                        sb.append(info.getName());
                        sb.append("、");
                    });
                    obj.setDesc(sb.toString().substring(0, sb.toString().length() - 1));
                }
            }
        });
        return data;
    }

    @Override
    public List<SysCategoryAttributeDTO> getListAttrDTOBySysProductId(Long sysProductId) {
        return this.baseMapper.getListAttrDTOBySysProductId(sysProductId);
    }


}
