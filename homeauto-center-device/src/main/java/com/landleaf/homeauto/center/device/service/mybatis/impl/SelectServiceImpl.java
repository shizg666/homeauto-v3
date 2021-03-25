package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.vo.family.TemplateSelectedVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectPathVO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.protocol.ProtocolAttrTypeEnum;
import com.landleaf.homeauto.common.enums.protocol.ProtocolTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 协议表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Service
public class SelectServiceImpl implements ISelectService {

    @Autowired
    private IProtocolInfoService iProtocolInfoService;

    @Autowired
    private IHomeAutoRealestateService iHomeAutoRealestateService;
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;


    @Override
    public List<SelectedIntegerVO> getListSelectProtocolType() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (ProtocolTypeEnum value : ProtocolTypeEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getCode());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getListSelectProtocol(Integer type) {
        return iProtocolInfoService.getListSelectProtocol(type);
    }

    @Override
    public List<SelectedVO> getListSelectCategory() {
        List<SelectedVO> selectedVOS = Lists.newArrayListWithCapacity(CategoryTypeEnum.values().length);
        for (CategoryTypeEnum category : CategoryTypeEnum.values()) {
            selectedVOS.add(new SelectedVO(category.getName(),category.getType()));
        }
        return selectedVOS;
    }

//    @Override
//    public List<SelectedVO> getListSelectUnit() {
//        List<SelectedVO> selectedVOS = Lists.newArrayListWithCapacity(AttrUnitEnum.values().length);
//        for (AttrUnitEnum unitEnum : AttrUnitEnum.values()) {
//            selectedVOS.add(new SelectedVO(unitEnum.getCode(),unitEnum.getCode()));
//        }
//        return selectedVOS;
//    }

    @Override
    public List<SelectedVO> ListSelectsRealestate() {
        return iHomeAutoRealestateService.ListSelects();
    }

    @Override
    public List<SelectedIntegerVO> getSelectRealestateStatus() {
        return iHomeAutoRealestateService.getRealestateStatus();
    }

    @Override
    public List<SelectedVO> getListSelectTemplates(String projectId) {
        List<TemplateSelectedVO> result = iProjectHouseTemplateService.getListSelectByProjectId(projectId);
        if(CollectionUtils.isEmpty(result)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return result.stream().map(template-> new SelectedVO(template.getName(),template.getId())).collect(Collectors.toList());
    }

    @Override
    public List<SelectedIntegerVO> getListSelectProtocolAttrType() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayListWithCapacity(ProtocolAttrTypeEnum.values().length);
        for (ProtocolAttrTypeEnum protocolAttrTypeEnum : ProtocolAttrTypeEnum.values()) {
            selectedVOS.add(new SelectedIntegerVO(protocolAttrTypeEnum.getName(),protocolAttrTypeEnum.getCode()));
        }
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getSelectByFamilyId(String familyId) {
        String tempalteId = iHomeAutoFamilyService.getTemplateIdById(familyId);
        List<SelectedVO> data = iHouseTemplateDeviceService.getSelectDeviceError(tempalteId);
        return data;
    }


}
