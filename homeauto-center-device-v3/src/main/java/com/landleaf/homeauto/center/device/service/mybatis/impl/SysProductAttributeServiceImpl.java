package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttribute;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.mapper.SysProductAttributeMapper;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeScopeDTO;
import com.landleaf.homeauto.center.device.model.vo.sys_product.SysProductAttributeVO;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductAttributeInfoScopeService;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.ISysProductAttributeService;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.common.enums.category.PrecisionEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Service
public class SysProductAttributeServiceImpl extends ServiceImpl<SysProductAttributeMapper, SysProductAttribute> implements ISysProductAttributeService {
    @Autowired
    private ISysProductAttributeInfoService iSysProductAttributeInfoService;
    @Autowired
    private ISysProductAttributeInfoScopeService iSysProductAttributeInfoScopeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProductAttribures(Long sysProductId) {
        remove(new LambdaQueryWrapper<SysProductAttribute>().eq(SysProductAttribute::getSysProductId, sysProductId));
        iSysProductAttributeInfoService.remove(new LambdaQueryWrapper<SysProductAttributeInfo>().eq(SysProductAttributeInfo::getSysProductId, sysProductId));
        iSysProductAttributeInfoScopeService.remove(new LambdaQueryWrapper<SysProductAttributeInfoScope>().eq(SysProductAttributeInfoScope::getSysProductId, sysProductId));
    }

    @Override
    public List<SysProductAttributeDTO> getListAttrDTOBySysProductId(Long sysProductId) {
        List<SysProductAttributeDTO> data = this.baseMapper.getListAttrDTOBySysProductId(sysProductId);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public List<SysProductAttributeVO> getListAttrVOBySysProductId(Long sysProductId) {
        List<SysProductAttributeDTO> attributeDTOS = getListAttrDTOBySysProductId(sysProductId);
        if (CollectionUtils.isEmpty(attributeDTOS)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<SysProductAttributeVO> data = BeanUtil.mapperList(attributeDTOS,SysProductAttributeVO.class);
        data.forEach(obj -> {
            if (AttributeTypeEnum.VALUE.getType().equals(obj.getType())) {
                SysProductAttributeScopeDTO scopeDTO = obj.getScope();
                if (Objects.isNull(scopeDTO)) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(scopeDTO.getMin()).append("-").append(scopeDTO.getMax()).append("、").append( PrecisionEnum.getInstByType(scopeDTO.getPrecision()) !=null ?PrecisionEnum.getInstByType(scopeDTO.getPrecision()).getName():"").append("、").append(scopeDTO.getStep());
                obj.setDesc(sb.toString());
            } else if (AttributeTypeEnum.MULTIPLE_CHOICE.getType().equals(obj.getType())) {
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
    public List<SysProductAttribute> getByProductCode(String productCode) {
        QueryWrapper<SysProductAttribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sys_product_code", productCode);
        return list(queryWrapper);
    }
}
