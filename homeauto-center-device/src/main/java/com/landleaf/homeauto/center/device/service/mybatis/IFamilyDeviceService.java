package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneDeviceBO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDeviceDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDeviceUpDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 家庭设备表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyDeviceService extends IService<FamilyDeviceDO> {

    /**
     * 获取所有设备
     *
     * @param familyId
     * @return
     */
    List<FamilyDeviceWithPositionBO> getAllDevices(String familyId);

    /**
     * 获取常用设备
     *
     * @param familyId
     * @return
     */
    List<FamilyDeviceWithPositionBO> getCommonDevices(String familyId);

    /**
     * 通过设备序列号获取设备信息
     *
     * @param sceneId 场景ID
     * @return 设备信息集合
     */
    List<FamilyDeviceWithPositionBO> getDeviceInfoBySceneId(String sceneId);

    /**
     * 根据产品id判断是否存在设备
     *
     * @param id
     * @return
     */
    boolean existByProductId(String id);

    /**
     * 根据产品id集合获取设备统计数据
     *
     * @param productIds
     * @return
     */
    List<CountBO> getCountByProducts(List<String> productIds);

    /**
     * 统计家庭设备数量
     *
     * @param familyIds
     * @return
     */
    List<CountBO> getCountByFamilyIds(List<String> familyIds);

    /**
     * 获取设备图标
     *
     * @param deviceId 设备ID
     * @return
     */
    String getDeviceIconById(String deviceId);

    /**
     * 获取设备位置
     *
     * @param deviceId 设备ID
     * @return
     */
    String getDevicePositionById(String deviceId);

    /**
     * 获取设备状态
     *
     * @param deviceSensorBO
     * @param attributeCode
     * @return
     */
    Object getDeviceStatus(DeviceSensorBO deviceSensorBO, String attributeCode);

    /**
     * 获取设备状态
     *
     * @param deviceId
     * @param attributeCode
     * @return
     */
    Object getDeviceStatus(String deviceId, String attributeCode);

    /**
     * 获取设备状态
     *
     * @param familyCode
     * @param productCode
     * @param deviceSn
     * @param attributeCode
     * @return
     */
    Object getDeviceStatus(String familyCode, String productCode, String deviceSn, String attributeCode);

    /**
     * 获取房间设备列表
     *
     * @param roomId 房间ID
     * @return
     */
    List<FamilyDeviceDO> getDeviceListByRoomId(String roomId);

    /**
     * 获取房间面板
     *
     * @param roomId
     * @return
     */
    FamilyDeviceDO getRoomPanel(String roomId);

    /**
     * 获取房间设备信息列表
     *
     * @param roomId
     * @return
     */
    List<FamilyDeviceBO> getDeviceInfoListByRoomId(String roomId);


    /**
     * 获取家庭的甲醛传感器
     *
     * @param familyId 家庭ID
     * @return 甲醛传感器
     */
    DeviceSensorBO getHchoSensor(String familyId);

    /**
     * 获取家庭的pm2.5传感器
     *
     * @param familyId 家庭ID
     * @return pm2.5传感器
     */
    DeviceSensorBO getPm25Sensor(String familyId);

    /**
     * 获取多参传感器
     *
     * @param familyId
     * @return
     */
    DeviceSensorBO getMultiParamSensor(String familyId);

    /**
     * 获取全参传感器
     *
     * @param familyId 家庭ID
     * @return 全参传感器
     */
    DeviceSensorBO getAllParamSensor(String familyId);

    /**
     * 获取家庭下的设备列表
     *
     * @param familyId
     * @return
     */
    List<FamilyDeviceDO> getDevicesByFamilyId(String familyId);


    /**
     * 根据家庭id获取暖通设备下拉列表
     *
     * @param familyId
     * @return
     */
    List<SelectedVO> getListHvacByFamilyId(String familyId);

    /**
     * App 修改设备名称
     *
     * @param request
     */
    void updateDeviceName(FamilyUpdateVO request);

    /**
     * 获取家庭暖通设备
     *
     * @param familyId
     * @param categoryEnum
     * @return
     */
    FamilyDeviceDO getFamilyDevice(String familyId, CategoryEnum categoryEnum);

    void add(FamilyDeviceDTO request);

    void update(FamilyDeviceUpDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 根据房间id获取设备列表
     *
     * @param roomId
     * @return
     */
    List<FamilyDevicePageVO> getListByRoomId(String roomId);

    /**
     * 根据房间id集合获取房间下的设备数量
     *
     * @param roomIds
     * @return
     */
    List<CountBO> countDeviceByRoomIds(List<String> roomIds);


    /**
     * 上移
     *
     * @param deviceId
     */
    void moveUp(String deviceId);

    /**
     * 下移
     *
     * @param deviceId
     */
    void moveDown(String deviceId);

    /**
     * 置顶
     *
     * @param deviceId
     */
    void moveTop(String deviceId);

    /**
     * 置底
     *
     * @param deviceId
     */
    void moveEnd(String deviceId);

    /**
     * @param deviceSn
     * @param familyId
     * @return
     */
    HomeAutoCategory getDeviceCategory(String deviceSn, String familyId);

    /**
     * @param deviceSn
     * @param familyId
     * @return
     */
    HomeAutoProduct getDeviceProduct(String deviceSn, String familyId);

    /**
     * 查询家庭下的房间面板设备号列表
     *
     * @param familyId
     * @return
     */
    List<String> getListPanel(String familyId);

    /**
     * 根据户家庭d获取暖通设备信息集合
     *
     * @param familyId
     * @return
     */
    List<SceneHvacDeviceVO> getListHvacInfo(String familyId);

    /**
     * 根据家庭id获取面板设置温度的属性范围（目前只考虑一户家庭中有一种类型的面板，假如有多个则随便取一个）
     *
     * @param familyId
     * @return
     */
    AttributeScopeVO getPanelSettingTemperature(String familyId);

    /**
     * 根据家庭id获取楼层房间设备属性集合
     *
     * @param familyId
     * @return
     */
    List<SceneFloorVO> getListdeviceInfo(String familyId);

    /**
     * 根据家庭id获取面板下拉列表
     *
     * @param familyId
     * @return
     */
    List<SelectedVO> getListPanelSelects(String familyId);

    /**
     * 根据户型id获取楼层房间非暖通设备属性集合(不包含层级关系)
     *
     * @param familyId
     * @return
     */
    List<SceneDeviceVO> getListDevice(String familyId);

    /**
     * 根据户型id获取楼层集合和房间集合
     *
     * @param familyId
     * @return
     */
    HouseFloorRoomListDTO getListFloorRooms(String familyId);

    /**
     * 获取家庭下的设备产品code
     *
     * @param familyId
     * @param deviceSns
     * @return
     */
    List<SyncSceneDeviceBO> getListSyncSceneDevice(String familyId, List<String> deviceSns);

    /**
     * 获取家庭设备
     *
     * @param familyId
     * @param deviceSn
     * @return
     */
    FamilyDeviceDO getFamilyDevice(String familyId, String deviceSn);
}
