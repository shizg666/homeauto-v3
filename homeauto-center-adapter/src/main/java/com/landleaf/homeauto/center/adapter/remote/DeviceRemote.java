package com.landleaf.homeauto.center.adapter.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
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
    @PostMapping("/device/contact-screen/apk-version/check")
    Response<ScreenHttpApkVersionCheckResponseDTO> apkVersionCheck(@RequestBody AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO);


    /**
     * 获取楼层房间设备信息
     *
     * @param adapterMessageHttpDTO
     * @return
     */
    @PostMapping("/device/contact-screen/floor-room-device/list")
    Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> getFloorRoomDeviceList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);


    /**
     * 天气请求
     */
    @PostMapping("/device/contact-screen/weather")
    Response<ScreenHttpWeatherResponseDTO> getWeather(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 定时场景获取
     */
    @PostMapping("/device/contact-screen/timing/scene/list")
    Response<List<ScreenHttpTimingSceneResponseDTO>> getTimingSceneList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 定时场景 删除
     */
    @PostMapping("/device/contact-screen/timing/scene/delete")
    Response<List<ScreenHttpTimingSceneResponseDTO>> deleteTimingScene(@RequestBody AdapterHttpDeleteTimingSceneDTO adapterMessageHttpDTO);


    /**
     * 定时场景 新增/修改
     */
    @PostMapping("/device/contact-screen/timing/scene/save-update")
    Response<List<ScreenHttpTimingSceneResponseDTO>> saveOrUpdateTimingScene(@RequestBody List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos,
                                                                             @RequestParam("familyId") String familyId);


    /**
     * 更新终端状态
     * @param adapterMessageHttpDTO
     * @return
     */
    @PostMapping("/device/contact-screen/update/terminal/status")
    Response updateTerminalOnLineStatus(@RequestBody AdapterHttpMqttCallBackDTO adapterMessageHttpDTO);

    /**
     * 获取消息公告信息
     *
     * @param adapterMessageHttpDTO
     * @return
     */
    @PostMapping("/device/contact-screen/news/list")
    Response<List<ScreenHttpNewsResponseDTO>> getNews(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 场景获取
     */
    @PostMapping("/device/contact-screen/scene/list")
    Response<List<SyncSceneInfoDTO>> getSceneList(@RequestBody AdapterMessageHttpDTO adapterMessageHttpDTO);

    /**
     * 节假日判定
     */
    @PostMapping("/device/contact-screen/holidays/check")
    Response<ScreenHttpHolidaysCheckResponseDTO> holidayCheck(@RequestBody AdapterHttpHolidaysCheckDTO holidaysCheckDTO);

    /**
     * 场景删除(暂无)
     */
    @PostMapping("/scene/delete")
    Response<List<SyncSceneInfoDTO>> deleteScene(@RequestBody AdapterHttpDeleteSceneDTO adapterMessageHttpDTO);

    /**
     * 场景修改/新增（暂无）
     */
    @PostMapping("/scene/save-update")
    Response<List<SyncSceneInfoDTO>> saveOrUpdateScene(@RequestBody List<AdapterHttpSaveOrUpdateSceneDTO> dtos);






}
