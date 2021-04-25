package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.ITemplateOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName ITemplateOperateServiceImpl
 * @Description: TODO
 * @Author shizg
 * @Date 2021/4/23
 * @Version V1.0
 **/
@Service
public class ITemplateOperateServiceImpl implements ITemplateOperateService {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private IContactScreenService iContactScreenService;

    @Override
    public void notifyTemplateUpdate(TemplateOperateEvent event) {
        List<Long> familyIds = iHomeAutoFamilyService.getFamilyIdsBind(event.getTemplateId());
        if (CollectionUtils.isEmpty(familyIds)){
            return;
        }
        familyIds.forEach(familyId->{
            iContactScreenService.notifySceneTimingConfigUpdate(familyId,event.getTypeEnum());
        });
    }
}
