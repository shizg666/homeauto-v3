package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.model.dto.jhappletes.*;
import com.landleaf.homeauto.center.device.service.mybatis.IJHAppletsrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName IKanBanServiceImpl
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/11
 * @Version V1.0
 **/
@Slf4j
@Service
public class IJHAppletstServiceImpl implements IJHAppletsrService {


    @Override
    public Long updateFamilyUser(FamilyUserDTO request) {
        return null;
    }

    @Override
    public void transferFamilyAdmin(FamilyUserAdminDTO request) {

    }

    @Override
    public OutDoorWeatherVO getOutDoorWeather(FamilyWeatherQryDTO request) {
        return null;
    }

    @Override
    public InDoorWeatherVO getInDoorWeather(FamilyWeatherQryDTO request) {
        return null;
    }

    @Override
    public JZFamilyRoomInfoVO getListRooms(FamilyWeatherQryDTO request) {
        return null;
    }

    @Override
    public void updateRoomName(JZRoomInfoVO request) {

    }

    @Override
    public List<JZFamilySceneVO> getListScene(FamilyWeatherQryDTO request) {
        return null;
    }

    @Override
    public void removeSceneById(Long sceneId) {

    }

    @Override
    public void addScene(JZFamilySceneDTO request) {

    }

    @Override
    public void updateScene(JZUpdateSceneDTO request) {

    }

    @Override
    public JZSceneDetailVO getDetailSceneById(Long sceneId) {
        return null;
    }

    @Override
    public JZDeviceStatusTotalVO getDeviceStatusTotal(FamilyWeatherQryDTO request) {
        return null;
    }

    @Override
    public JZSceneConfigDataVO getRoomDeviceAttrInfo(FamilyWeatherQryDTO request) {
        return null;
    }

    @Override
    public JZDeviceStatusCategoryVO getDeviceStatusByCategoryCode(JZDeviceStatusQryDTO request) {
        return null;
    }

    @Override
    public void deviceCommand(JzDeviceCommandDTO request) {

    }

    @Override
    public List<JZAlarmMessageVO> getListAlarm(FamilyWeatherQryDTO request) {
        return null;
    }
}
