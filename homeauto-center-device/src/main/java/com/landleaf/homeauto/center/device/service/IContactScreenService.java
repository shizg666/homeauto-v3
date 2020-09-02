package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpApkVersionCheckResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpTimingSceneResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpWeatherResponseDTO;

import java.util.List;

/**
 * 处理大屏请求业务类
 *
 * @author wenyilu
 */
public interface IContactScreenService {

    /**
     * 大屏apk更新检测
     *
     * @param adapterHttpApkVersionCheckDTO
     * @return
     */
    ScreenHttpApkVersionCheckResponseDTO apkVersionCheck(AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO);

    /**
     * 获取楼层房间设备信息
     *
     * @param familyId 家庭id
     * @return
     */
    List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(String familyId);

    /**
     * 获取家庭所在城市天气信息
     *
     * @param familyId 家庭id
     * @return
     */
    ScreenHttpWeatherResponseDTO getWeather(String familyId);

    /**
     * 获取家庭的场景定时配置
     *
     * @param familyId 家庭id
     * @return
     */
    List<ScreenHttpTimingSceneResponseDTO> getTimingSceneList(String familyId);

    /**
     * 家庭定时场景批量删除，返回现有定时场景列表
     * @param timingIds      定时场景id集合
     * @param familyId       家庭id
     * @return
     */
    List<ScreenHttpTimingSceneResponseDTO> deleteTimingScene(List<String> timingIds, String familyId);

    /**
     * 新增或修改家庭定时场景配置
     * @param dtos      数据
     * @param familyId  家庭id
     * @return
     */
    List<ScreenHttpTimingSceneResponseDTO> saveOrUpdateTimingScene(List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos, String familyId);

    /**
     * 更新家庭终端的上下线状态
     * @param familyId     家庭id
     * @param terminalMac  终端mac
     * @param status       在线状态
     */
    void updateTerminalOnLineStatus(String familyId, String terminalMac, Integer status);
}
