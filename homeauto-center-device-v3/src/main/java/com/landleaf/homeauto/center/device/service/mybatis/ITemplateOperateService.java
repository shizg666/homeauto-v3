package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;

/**
 * <p>
 * 户型操作相关 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface ITemplateOperateService  {


    /**
     * 户型变动消息处理
     * @param event
     */

    void notifyTemplateUpdate(TemplateOperateEvent event);

    /**
     * 发送户型变更消息
     * @param event
     */
    void sendEvent(TemplateOperateEvent event);
}
