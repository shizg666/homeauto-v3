package com.landleaf.homeauto.center.adapter.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wenyilu
 */
@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_DEVICE)
public interface DeviceRemote {


    /**
     * 获取家庭信息
     *
     * @param terminalMac  终端地址
     * @param terminalType 终端类型
     * @return 家庭信息
     */
    @GetMapping("/device/screen/family/info")
    Response<AdapterFamilyDTO> getFamily(@RequestParam("terminalMac") String terminalMac,
                                         @RequestParam("terminalType") Integer terminalType);

    /**
     * 大屏apk检查更新
     *
     * @param adapterHttpApkVersionCheckDTO 请求体
     * @return
     */
    @PostMapping("/screen/apk-version/check")
    Response<ScreenHttpApkVersionCheckResponseDTO> apkVersionCheck(@RequestBody AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO);

    /**
     * 获取家庭码
     *
     * @param adapterMessageHttpDTO
     * @return
     */
    @PostMapping("/familyCode")
    Response<ScreenHttpFamilyCodeResponseDTO> getFamilyCode(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 获取楼层房间设备信息
     *
     * @param adapterMessageHttpDTO
     * @return
     */
    @PostMapping("/floor-room-device/list")
    Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> getFloorRoomDeviceList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 获取消息公告信息
     *
     * @param adapterMessageHttpDTO
     * @return
     */
    @PostMapping("/news/list")
    Response<List<ScreenHttpNewsResponseDTO>> getNews(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 场景(自由方舟)删除
     */
    @PostMapping("/non-smart/scene/delete")
    Response<List<ScreenHttpSceneResponseDTO>> deleteNonSmartScene(@RequestBody AdapterHttpDeleteNonSmartSceneDTO adapterMessageHttpDTO);

    /**
     * 场景(自由方舟)修改/新增
     */
    @PostMapping("/non-smart/scene/save-update")
    Response<List<ScreenHttpSceneResponseDTO>> saveOrUpdateNonSmartScene(@RequestBody List<AdapterHttpSaveOrUpdateNonSmartSceneDTO> dtos);

    /**
     * 场景(户式化)获取
     */
    @PostMapping("/scene/list")
    Response<List<ScreenHttpSceneResponseDTO>> getSceneList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);


    /**
     * 场景(自由方舟)获取
     */
    @PostMapping("/non-smart/scene/list")
    Response<List<ScreenHttpSceneResponseDTO>> getNonSmartSceneList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 定时场景获取
     */
    @PostMapping("/timing/scene/list")
    Response<List<ScreenHttpTimingSceneResponseDTO>> getTimingSceneList(AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 定时场景 新增/修改
     */
    @PostMapping("/timing/scene/save-update")
    Response<List<ScreenHttpTimingSceneResponseDTO>> saveOrUpdateTimingScene(@RequestBody List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos);

    /**
     * 定时场景 删除
     */
    @PostMapping("/timing/scene/delete")
    Response<List<ScreenHttpTimingSceneResponseDTO>> deleteTimingScene(@RequestBody AdapterHttpDeleteTimingSceneDTO adapterMessageHttpDTO);

    /**
     * 节假日判定
     */
    @PostMapping("/holidays/check")
    Response<ScreenHttpHolidaysCheckResponseDTO> holidayCheck(@RequestBody AdapterHttpHolidaysCheckDTO holidaysCheckDTO);

    /**
     * 天气请求
     */
    @PostMapping("/weather")
    Response<ScreenHttpWeatherResponseDTO> getWeather(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);
}
