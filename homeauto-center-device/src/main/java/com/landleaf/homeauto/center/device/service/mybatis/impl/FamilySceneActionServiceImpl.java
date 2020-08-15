package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneActionMapper;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneActionService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeDicService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeInfoDicService;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDetailVO;
import com.landleaf.homeauto.model.po.device.FamilySceneActionPO;
import com.landleaf.homeauto.model.vo.AttributionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    private IHomeAutoAttributeDicService homeAutoAttributeDicService;

    private IHomeAutoAttributeInfoDicService homeAutoAttributeInfoDicService;

    @Override
    public List<AttributionVO> getDeviceActionAttributionByDeviceSn(String deviceSn) {
        QueryWrapper<FamilySceneActionPO> familySceneActionQueryWrapper = new QueryWrapper<>();
        familySceneActionQueryWrapper.eq("device_sn", deviceSn);
        List<FamilySceneActionPO> familySceneActionPoList = list(familySceneActionQueryWrapper);
        List<AttributionVO> attributionVOList = new LinkedList<>();
        for (FamilySceneActionPO familySceneActionPo : familySceneActionPoList) {
            AttributionVO attributionVO = new AttributionVO();
            AttributeDicDetailVO attributeDicDetailVO = homeAutoAttributeDicService.getDetailById(familySceneActionPo.getAttributeId());
            attributionVO.setAttrName(attributeDicDetailVO.getCode());
            attributionVO.setAttrValue(familySceneActionPo.getVal());
            attributionVOList.add(attributionVO);
        }
        return attributionVOList;
    }


    @Autowired
    public void setHomeAutoAttributeDicService(IHomeAutoAttributeDicService homeAutoAttributeDicService) {
        this.homeAutoAttributeDicService = homeAutoAttributeDicService;
    }
}
