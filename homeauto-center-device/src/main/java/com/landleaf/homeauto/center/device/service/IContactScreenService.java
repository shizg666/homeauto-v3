package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpApkVersionCheckResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;

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
     * @param familyId   家庭id
     * @return
     */
    List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(String familyId);
}
