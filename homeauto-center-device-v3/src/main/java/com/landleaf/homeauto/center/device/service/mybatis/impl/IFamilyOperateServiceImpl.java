package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEventHolder;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyOperateService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
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
public class IFamilyOperateServiceImpl implements IFamilyOperateService {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private IContactScreenService iContactScreenService;
    @Autowired
    private TemplateOperateEventHolder templateOperateEventHolder;

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

    @Override
    public void sendEvent(TemplateOperateEvent event) {
        templateOperateEventHolder.addEvent(event);
    }
}
