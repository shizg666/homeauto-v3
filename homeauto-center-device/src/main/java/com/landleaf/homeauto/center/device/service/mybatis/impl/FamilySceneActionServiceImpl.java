package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneActionDO;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeDO;
import com.landleaf.homeauto.center.device.model.domain.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneActionMapper;
import com.landleaf.homeauto.center.device.model.vo.AttributionVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneActionService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IProductAttributeService;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
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
public class FamilySceneActionServiceImpl extends ServiceImpl<FamilySceneActionMapper, FamilySceneActionDO> implements IFamilySceneActionService {

    private IProductAttributeService productAttributeService;

    private IProductAttributeInfoService productAttributeInfoService;

    @Override
    public List<AttributionVO> getDeviceActionAttributionByDeviceSn(String deviceSn) {
        QueryWrapper<FamilySceneActionDO> familySceneActionQueryWrapper = new QueryWrapper<>();
        familySceneActionQueryWrapper.eq("device_sn", deviceSn);
        List<FamilySceneActionDO> familySceneActionPoList = list(familySceneActionQueryWrapper);
        List<AttributionVO> attributionVOList = new LinkedList<>();
        for (FamilySceneActionDO familySceneActionDO : familySceneActionPoList) {
            AttributionVO attributionVO = new AttributionVO();
            String productAttributeId = familySceneActionDO.getProductAttributeId();
            ProductAttributeDO productAttributeDO = productAttributeService.getProductAttributeById(productAttributeId);
            attributionVO.setAttrName(productAttributeDO.getName());
            if (Objects.equals(AttributeTypeEnum.getInstByType(productAttributeDO.getType()), AttributeTypeEnum.RANGE)) {
                // 如果属性值类型是值域类型,则直接返回值
                attributionVO.setAttrValue(familySceneActionDO.getVal());
            } else {
                // 否则去查产品属性值表的具体含义
                ProductAttributeInfoDO productAttributeInfoPo = productAttributeInfoService.getProductAttributeInfoByAttrIdAndCode(productAttributeId, familySceneActionDO.getVal());
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
