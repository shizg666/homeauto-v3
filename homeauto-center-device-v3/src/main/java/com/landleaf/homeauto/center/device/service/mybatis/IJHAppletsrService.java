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
    Long updateFamilyUser(FamilyUserDTO request);

    /**
     * 管理员转让
     * @param request
     */
    void transferFamilyAdmin(FamilyUserAdminDTO request);

    /**
     * 家庭所在城市室外天气获取
     * @param request
     * @return
     */
    OutDoorWeatherVO getOutDoorWeather(FamilyWeatherQryDTO request);

    /**
     * 家庭室内环境
     * @param request
     * @return
     */
    InDoorWeatherVO getInDoorWeather(FamilyWeatherQryDTO request);

    /**
     * 获取房间信息
     * @param request
     * @return
     */
    JZFamilyRoomInfoVO getListRooms(FamilyWeatherQryDTO request);

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
    List<JZFamilySceneVO> getListScene(FamilyWeatherQryDTO request);

    /**
     * 删除场景
     * @param sceneId
     */
    void removeSceneById(Long sceneId);

    /**
     * 添加场景
     * @param request
     */
    void addScene(JZFamilySceneDTO request);

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
    JZDeviceStatusTotalVO getDeviceStatusTotal(FamilyWeatherQryDTO request);

    /**
     * 场景添加--获取房间设备数据
     * @param request
     * @return
     */
    JZSceneConfigDataVO getRoomDeviceAttrInfo(FamilyWeatherQryDTO request);

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
    List<JZAlarmMessageVO> getListAlarm(FamilyWeatherQryDTO request);
}
