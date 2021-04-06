package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 户型设备表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IHouseTemplateDeviceService extends IService<TemplateDeviceDO> {

    TemplateDeviceDO add(TemplateDeviceAddDTO request);

    void update(TemplateDeviceAddDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 根据房间id集合获取房间下的设备数量
     * @param roomIds
     * @return
     */
    List<CountBO> countDeviceByRoomIds(List<String> roomIds);

    /**
     * 根据房间id获取设备列表
     * @param roomId
     * @return
     */
    List<TemplateDevicePageVO> getListByRoomId(String roomId);

    /**
     * 上移
     * @param deviceId
     */
    void moveUp(String deviceId);
    /**
     * 下移
     * @param deviceId
     */
    void moveDown(String deviceId);

    /**
     * 置顶
     * @param deviceId
     */
    void moveTop(String deviceId);

    /**
     * 置底
     * @param deviceId
     */
    void moveEnd(String deviceId);

    /**
     * 查询户型下的房间面板设备号列表
     * @param templateId
     * @return
     */
    List<String> getListPanel(String templateId);

    /**
     * 查询户型下的面板下拉选择列表（添加场景）
     * @param templateId
     * @return
     */
    List<SelectedVO> getListPanelSelects(String templateId);

    /**
     * 根据户型id获取暖通设备信息集合
     * @param templateId
     * @return
     */
    List<SceneHvacDeviceVO> getListHvacInfo(String templateId);

    /**
     * 获取户型下面板设置温度的范围信息（目前只考虑一户家庭中有一种类型的面板，假如有多个则随便取一个）
     * @param templateId
     * @return
     */
    AttributeScopeVO getPanelSettingTemperature(String templateId);

    /**
     * 根据户型id获取楼层房间设备属性集合(包含层级关系)
     * @param templateId
     * @return
     */
    List<SceneFloorVO> getListdeviceInfo(String templateId);

    /**
     * 根据户型id获取楼层房间非暖通设备属性集合(不包含层级关系)
     * @param templateId
     * @return
     */
    List<SceneDeviceVO> getListDevice(String templateId);

    /**
     * 根据户型id获取楼层集合和房间集合
     * @param templateId
     * @return
     */
    HouseFloorRoomListDTO getListFloorRooms(String templateId);

    /**
     * 判断户型有没有配置该产品的设备
     * @param productId
     * @return
     */
    boolean existByProductId(Long productId);

    /**
     * 根据户型查询设备
     *
     * @param templateId 户型ID
     * @param showApp  app是否展示（0：否，1：是） 非必须
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO>
     * @author wenyilu
     * @date 2021/1/5 15:58
     */
    List<TemplateDeviceDO> getTemplateDevices(String templateId,Integer showApp);

    /**
     * 获取带索引的设备信息
     *
     * @param familyId
     * @param templateId
     * @param templateDevices
     * @param familyCommonDeviceDOList
     * @param commonUse
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date  2021/1/5 16:02
     */
    List<FamilyDeviceBO> getFamilyDeviceWithIndex(String familyId, String templateId, List<TemplateDeviceDO> templateDevices, List<FamilyCommonDeviceDO> familyCommonDeviceDOList, boolean commonUse);

    /**
     * 批量获取家庭设备详情信息
     * @param deviceIds
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date  2021/1/5 16:07
     */
    List<FamilyDeviceBO> listDeviceDetailByIds(List<String> deviceIds,String familyId,String templateId);

    /**
     * 获取家庭某个设备信息详情
     * @param deviceId
     * @return com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO
     * @author wenyilu
     * @date  2021/1/5 16:07
     */
    FamilyDeviceBO detailDeviceById(String deviceId,String familyId,String templateId);
    /**
     *  获取家庭某个房间下设备列表详情
     * @param familyId    家庭ID
     * @param roomId      房间ID
     * @param templateId  户型ID
     * @param showApp     设备在app是否展示（0：否，1：是）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date  2021/1/6 9:38
     */
    List<FamilyDeviceBO> getFamilyRoomDevices(String familyId, String roomId,String templateId,Integer showApp);
    /**
     * 根据户型统计设备数量
     * @param templateIds   户型ID集合
     * @param showApp   app是否展示（0：否，1：是）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.project.CountBO>
     * @author wenyilu
     * @date  2021/1/6 10:02
     */
    List<CountBO> getCountByTemplateIds(List<String> templateIds,Integer showApp);

    /**
     * 查询户型设备列表
     * @param templateId
     * @return
     */
    List<TemplateDevicePageVO> getListByTemplateId(String templateId);

    /**
     *  根据户型及设备编码获取设备
     * @param templateId
     * @param deviceCode
     * @return com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO
     * @author wenyilu
     * @date 2021/1/15 15:19
     */
    TemplateDeviceDO getDeviceByTemplateAndCode(String templateId, String deviceCode);


    TemplateDeviceDetailVO detailById(String deviceId);


    /**
     * 获取户型设备下拉列表
     * @param tempalteId
     * @return
     */
    List<SelectedVO> getSelectByTempalteId(String tempalteId);


    /**
     * 故障报修功能 设备下拉选择
     * @param tempalteId
     * @return
     */
    List<SelectedVO> getSelectDeviceError(String tempalteId);

    /**
     *  根据户型及属性code获取设备
     * @param templateId  户型ID
     * @param attrCode   属性code
     * @return com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO
     * @author wenyilu
     * @date 2021/2/4 9:56
     */
    TemplateDeviceDO getDeviceByTemplateAndAttrCode(String templateId, String attrCode);


    /**
     * 户型设备分页查询
     * @param templateId
     * @param pageNum
     * @param pageSize
     * @return
     */
    BasePageVO<TemplateDevicePageVO> getListPageByTemplateId(String templateId, Integer pageNum, Integer pageSize);


    /***
     * 所有户型设备统计
     * @param request
     * @return
     */
    List<HomeDeviceStatistics> getDeviceStatistics(HomeDeviceStatisticsQry request);

    /**
     * 设备故障缓存
     * @param event
     */
    void errorAttrInfoCache(DeviceOperateEvent event);

    /**
     * 获取设备基本信息缓存
     * @param deviceId
     */
    TemplateDeviceCacheDTO getBaseDeviceCache(String deviceId);

    /**
     * 获取设备属性缓存信息
     * @param templateId
     * @param attrCode
     * @return
     */
    DeviceAttrInfoCacheBO getDeviceAttrCache(String templateId,String attrCode);


    List<TemplateDeviceDO> listByTemplateId(String templateId);
}
