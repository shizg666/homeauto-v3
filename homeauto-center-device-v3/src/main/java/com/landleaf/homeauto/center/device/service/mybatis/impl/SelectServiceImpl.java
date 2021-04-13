package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.vo.family.TemplateSelectedVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
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
    private IHomeAutoRealestateService iHomeAutoRealestateService;
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;





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
    public List<SelectedLongVO> ListSelectsRealestate() {
        return iHomeAutoRealestateService.ListSelects();
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
    public List<SelectedVO> getSelectByFamilyId(String familyId) {
        String tempalteId = iHomeAutoFamilyService.getTemplateIdById(familyId);
        List<SelectedVO> data = iHouseTemplateDeviceService.getSelectDeviceError(tempalteId);
        return data;
    }


}
