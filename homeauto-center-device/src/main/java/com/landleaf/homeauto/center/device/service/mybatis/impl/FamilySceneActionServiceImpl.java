package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneActionMapper;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDetailVO;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.model.po.device.FamilySceneActionPO;
import com.landleaf.homeauto.model.po.device.ProductAttributeInfoPO;
import com.landleaf.homeauto.model.po.device.ProductAttributePO;
import com.landleaf.homeauto.model.vo.AttributionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 场景关联设备动作表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilySceneActionServiceImpl extends ServiceImpl<FamilySceneActionMapper, FamilySceneActionPO> implements IFamilySceneActionService {

    private IProductAttributeService productAttributeService;

    private IProductAttributeInfoService productAttributeInfoService;

    @Override
    public List<AttributionVO> getDeviceActionAttributionByDeviceSn(String deviceSn) {
        QueryWrapper<FamilySceneActionPO> familySceneActionQueryWrapper = new QueryWrapper<>();
        familySceneActionQueryWrapper.eq("device_sn", deviceSn);
        List<FamilySceneActionPO> familySceneActionPoList = list(familySceneActionQueryWrapper);
        List<AttributionVO> attributionVOList = new LinkedList<>();
        for (FamilySceneActionPO familySceneActionPo : familySceneActionPoList) {
            AttributionVO attributionVO = new AttributionVO();
            String productAttributeId = familySceneActionPo.getProductAttributeId();
            ProductAttributePO productAttributePo = productAttributeService.getProductAttributeById(productAttributeId);
            attributionVO.setAttrName(productAttributePo.getName());
            if (Objects.equals(AttributeTypeEnum.getInstByType(productAttributePo.getType()), AttributeTypeEnum.RANGE)) {
                // 如果属性值类型是值域类型,则直接返回值
                attributionVO.setAttrValue(familySceneActionPo.getVal());
            } else {
                // 否则去查产品属性值表的具体含义
                ProductAttributeInfoPO productAttributeInfoPo = productAttributeInfoService.getProductAttributeInfoByAttrIdAndCode(productAttributeId, familySceneActionPo.getVal());
                attributionVO.setAttrValue(productAttributeInfoPo.getName());
            }
            attributionVOList.add(attributionVO);
        }
        return attributionVOList;
    }

    @Autowired
    public void setProductAttributeService(IProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @Autowired
    public void setProductAttributeInfoService(IProductAttributeInfoService productAttributeInfoService) {
        this.productAttributeInfoService = productAttributeInfoService;
    }
}
