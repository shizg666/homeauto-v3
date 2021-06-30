package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceSimpleBO;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttrInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.HouseFloorRoomListVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneHvacDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.statistics.DeviceStatisticsBO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;

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
     * 根据户型id获取楼层房间设备属性集合(不包含层级关系)---新增场景设备
     * @param templateId
     * @return
     */
    List<SceneDeviceVO> getListDeviceScene(Long templateId);

    /**
     * 根据户型id获取楼层集合和房间集合
     * @param templateId
     * @return
     */
    HouseFloorRoomListVO getListFloorRooms(Long templateId);

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
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO>
     * @author wenyilu
     * @date 2021/1/5 15:58
     */
    List<TemplateDeviceDO> getTemplateDevices(Long templateId);


    /**
     *  获取家庭某个房间下设备列表详情
     * @param familyId    家庭ID
     * @param roomId      房间ID
     * @param templateId  户型ID
     * @param systemFlag 设备类型{@link FamilySystemFlagEnum}
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date  2021/1/6 9:38
     */
    List<FamilyDeviceSimpleBO> getFamilyRoomDevices(Long familyId, Long roomId, Long templateId, Integer systemFlag);
    /**
     * 根据户型统计设备数量
     * @param templateIds   户型ID集合
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.project.CountBO>
     * @author wenyilu
     * @date  2021/1/6 10:02
     */
    List<CountBO> getCountByTemplateIds(List<Long> templateIds);

    /**
     * 查询户型设备列表
     * @param templateId
     * @return
     */
    List<TemplateDevicePageVO> getListByTemplateId(Long templateId);

    /**
     * 查询房间下的设备列表
     * @param roomId
     * @return
     */
    List<TemplateDevicePageVO> getListByRoomId(Long roomId);

    /**
     *  根据户型及设备编码获取设备
     * @param templateId
     * @param deviceSn
     * @return com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO
     * @author wenyilu
     * @date 2021/1/15 15:19
     */
    TemplateDeviceDO getDeviceByTemplateAndCode(Long templateId, String deviceSn);


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
    List<SelectedVO> getSelectDeviceError(Long tempalteId);

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
    BasePageVO<TemplateDevicePageVO> getListPageByTemplateId(Long templateId, Integer pageNum, Integer pageSize);


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

//    /**
//     * 获取设备基本信息缓存
//     * @param deviceId
//     */
//    TemplateDeviceCacheDTO getBaseDeviceCache(String deviceId);

    /**
     * 获取设备属性缓存信息
     * @param templateId
     * @param attrCode
     * @return
     */
    DeviceAttrInfoCacheBO getDeviceAttrCache(String templateId,String attrCode);


    List<TemplateDeviceDO> listByTemplateId(Long templateId);
    /**
     * 按房间统计户型下的设备数量
     * @param templateId
     * @return
     */
    List<TotalCountBO> getDeviceNumGroupByRoom(Long templateId);

    TemplateDeviceDO getDeviceByIdOrDeviceSn(Long houseTemplateId, Long deviceId, String deviceSn);

    /**
     * 获取设备属性信息
     * @param deviceId
     * @return
     */
    List<DeviceAttrInfoDTO> getDeviceAttrsInfo(Long deviceId);

    /**
     * 获取设备的产品id
     * @param deviceId
     * @return
     */
    Long getProdcutIdByDeviceId(Long deviceId);

    /**
     * 获取设备的产品code
     * @param deviceId
     * @return
     */
    String getProdcutCodeByDeviceId(Long deviceId);


    /**
     * 根据产品统计产品下的设备数量
     * @param productIds
     * @return
     */
    List<CountLongBO> totalGroupByProductIds(List<Long> productIds);

    /**
     * @param: templateId
     * @description: 获取户型下系统设备
     * @return: com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO
     * @author: wyl
     * @date: 2021/5/26
     */
    TemplateDeviceDO getSystemDevice(Long templateId);

    List<TemplateDeviceDO> getSystemDevices(Long houseTemplateId,Integer... systemFlags);

    /**
     * @param: templateId
     * @description: 获取户型下设备所包含精英
     * @return: java.lang.Integer  0：都没有，1：只包含系统设备，2：只包含普通设备，3：两都皆有
     * @author: wyl
     * @date: 2021/6/2
     * @param templateId
     */
    Integer checkDeviceType(Long templateId);

    /**
     * 根据户型id获取户型设备信息列表
     * @param templateId
     * @return
     */
    List<TemplateDeviceDO> getListDeviceDOByTeamplateId(Long templateId);

    /**
     * 根据户型id获取户型设备信息列表
     * @param templateIds
     * @return
     */
    List<TemplateDeviceDO> getListDeviceDOByTeamplateIds(List<Long> templateIds);


    /**
     * 获取户型下的设备列表信息 -- 看板
     * @param templateIds
     * @return
     */
    List<DeviceStatisticsBO> getListDeviceStatistics(List<Long> templateIds);

    /**
     * 获取家庭户型传感器的设备号
     * @param realestateId
     * @return
     */
    TemplateDeviceDO getSensorDeviceSnByTId(Long realestateId);
}
