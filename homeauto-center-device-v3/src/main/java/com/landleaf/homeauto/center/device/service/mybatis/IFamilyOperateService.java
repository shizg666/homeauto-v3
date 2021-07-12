package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.eventbus.event.FamilyOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;

/**
 * <p>
 * 户型操作相关 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IFamilyOperateService {


    /**
     * 户型变动消息处理
     * @param event
     */

    void notifyTemplateUpdate(FamilyOperateEvent event);

    /**
     * 发送户型变更消息
     * @param event
     */
    void sendEvent(FamilyOperateEvent event);
}
