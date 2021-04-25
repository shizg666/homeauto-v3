package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttrInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.HouseFloorRoomListVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneHvacDeviceVO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

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
}
