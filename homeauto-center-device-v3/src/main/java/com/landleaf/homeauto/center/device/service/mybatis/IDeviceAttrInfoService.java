package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;

import java.util.List;

/**
 * <p>
 * 协议属性信息 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
public interface IDeviceAttrInfoService extends IService<DeviceAttrInfo> {

    /**
     *  根据设备ID及属性类型查询属性
     * @param deviceId  设备ID
     * @param type    属性类型
     * @param appFlag   app是否需要 ：1-是，0-否
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo>
     * @author wenyilu
     * @date 2021/1/21 16:59
     */
    List<DeviceAttrInfo> getAttributesByDeviceId(String deviceId, Integer type,Integer appFlag);

    /**
     * 获取设备故障属性信息
     * @param request
     * @return
     */
    AttributeErrorDTO getAttrError(AttributeErrorQryDTO request);



    /**
     * 获取设备的故障属性code列表
     * @param deviceId
     * @return
     */
    List<String> getAttrErrorCodeListByDeviceId(String deviceId);


    /**
     * 设备故障属性缓存新增
     * @param event
     */
    public void errorAttrInfoCacheAdd(DeviceOperateEvent event);

    /**
     * 设备故障属性缓存删除
     * @param event
     */
    public void errorAttrInfoCacheDelete(DeviceOperateEvent event);

    /**
     * 获取设别属性信息缓存
     * @param templateId
     * @param attrCode
     * @return
     */
    DeviceAttrInfoCacheBO getAndSaveAttrInfoCache(String templateId, String attrCode);
}
