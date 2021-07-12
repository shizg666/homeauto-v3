package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.eventbus.event.FamilyOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.FamilyOperateEventHolder;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEventHolder;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyOperateService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import net.bytebuddy.asm.Advice;
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
    private FamilyOperateEventHolder familyOperateEventHolder;
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public void notifyTemplateUpdate(FamilyOperateEvent event) {
        iContactScreenService.notifySceneTimingConfigUpdate(event.getFamilyId(),event.getTypeEnum());
    }

    @Override
    public void sendEvent(FamilyOperateEvent event) {
        Long templateId = iHomeAutoFamilyService.getTemplateIdById(event.getFamilyId());
        String key = String.format(RedisCacheConst.CONFIG_HOUSE_TEMPLATE_ATTR_CACHE,templateId,event.getFamilyId());
        redisUtils.del(key);
        familyOperateEventHolder.addEvent(event);
    }
}
