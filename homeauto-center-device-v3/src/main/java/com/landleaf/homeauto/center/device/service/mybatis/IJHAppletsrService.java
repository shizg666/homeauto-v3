package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.model.dto.jhappletes.*;

import java.util.List;

/**
 * <p>
 * 嘉宏小程序业务
 * </p>
 *
 * @since 2020-08-20
 */
public interface IJHAppletsrService {

    /**
     * 家庭成员信息变更
     * @param request
     * @return
     */
    void updateFamilyUser(JZFamilyUserDTO request);

    /**
     * 管理员转让
     * @param request
     */
    void transferFamilyAdmin(JZFamilyUserAdminDTO request);

    /**
     * 家庭所在城市室外天气获取
     * @param request
     * @return
     */
    OutDoorWeatherVO getOutDoorWeather(JZFamilyQryDTO request);

    /**
     * 家庭室内环境
     * @param request
     * @return
     */
    InDoorWeatherVO getInDoorWeather(JZFamilyQryDTO request);

    /**
     * 获取房间信息
     * @param request
     * @return
     */
    JZFamilyRoomInfoVO getListRooms(JZFamilyQryDTO request);

    /**
     * 修改房间名称
     * @param request
     */
    void updateRoomName(JZRoomInfoVO request);

    /**
     * 获取场景列表
     * @param request
     * @return
     */
    List<JZFamilySceneVO> getListScene(JZFamilyQryDTO request);

    /**
     * 删除场景
     * @param sceneId
     */
    void removeSceneById(Long sceneId);

    /**
     * 添加场景
     * @param request
     */
    Long addScene(JZFamilySceneDTO request);

    /**
     * 修改场景
     * @param request
     */
    void updateScene(JZUpdateSceneDTO request);

    /**
     * 场景详情
     * @param sceneId
     * @return
     */
    JZSceneDetailVO getDetailSceneById(Long sceneId);


    /**
     * 设备运行状态统计（按品类统计）
     * @param request
     * @return
     */
    JZDeviceStatusTotalVO getDeviceStatusTotal(JZFamilyQryDTO request);

    /**
     * 场景添加--获取房间设备数据
     * @param request
     * @return
     */
    JZSceneConfigDataVO getRoomDeviceAttrInfo(JZFamilyQryDTO request);

    /**
     * 查看品类下设备状态
     * @param request
     * @return
     */
    JZDeviceStatusCategoryVO getDeviceStatusByCategoryCode(JZDeviceStatusQryDTO request);

    /**
     * 设备控制
     * @param request
     */
    void deviceCommand(JzDeviceCommandDTO request);

    /**
     * 获取安防报警列表
     * @param request
     * @return
     */
    List<JZAlarmMessageVO> getListAlarm(JZFamilyQryDTO request);

    /**
     * 获取websocket地址
     * @param request
     * @return
     */
    String getWebSocketAddress(JZFamilyQryDTO request);


    /**
     * 获取家庭id
     * @param request
     * @return
     */
    Long getFamilyIdByFloorUnit(JZFamilyQryDTO request);

    /**
     * 查看某一房间某一品类下设备状态
     * @param request
     * @return
     */
    JZRoomDeviceStatusCategoryVO getDeviceStatusByRoomIdAndCategoryCode(JZDeviceStatusQryDTO request);

    /**
     * 清除家庭报警信息
     * @param request
     */
    void clearAlarms(JZFamilyQryDTO request);
}
