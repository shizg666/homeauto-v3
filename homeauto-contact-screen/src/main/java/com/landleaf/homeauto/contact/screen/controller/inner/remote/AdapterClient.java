package com.landleaf.homeauto.contact.screen.controller.inner.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 内部服务调用
 *
 * @author wenyilu
 */
@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_ADAPTER)
public interface AdapterClient {

    /**
     * apk版本检测
     *
     * @param resultDTO 入参
     */
    @PostMapping("/adapter/contact-screen/apk-version/check")
    Response<ScreenHttpApkVersionCheckResponseDTO> apkVersionCheck(@RequestBody ScreenHttpApkVersionCheckDTO resultDTO);

    /**
     * 获取家庭码
     */
    @PostMapping("/adapter/contact-screen/familyCode")
    Response<ScreenHttpFamilyCodeResponseDTO> getFamilyCode(@RequestBody ScreenHttpRequestDTO screenRequestDTO);

    /**
     * 楼层房间设备信息请求
     */
    @PostMapping("/adapter/contact-screen/floor-room-device/list")
    Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> getFloorRoomDeviceList(@RequestBody ScreenHttpRequestDTO screenRequestDTO);

    /**
     * 消息公告
     */
    @PostMapping("/adapter/contact-screen/news/list")
    Response<List<ScreenHttpNewsResponseDTO>> getNews(@RequestBody ScreenHttpRequestDTO screenRequestDTO);


    /**
     * 场景(自由方舟)删除
     */
    @PostMapping("/adapter/contact-screen/non-smart/scene/delete")
    Response<List<ScreenHttpSceneResponseDTO>> deleteNonSmartScene(@RequestBody ScreenHttpDeleteNonSmartSceneDTO screenRequestDTO);

    /**
     * 场景(自由方舟)修改/新增
     */
    @PostMapping("/adapter/contact-screen/non-smart/scene/save-update")
    Response<List<ScreenHttpSceneResponseDTO>> saveOrUpdateNonSmartScene(@RequestBody List<ScreenHttpSaveOrUpdateNonSmartSceneDTO> requestBody);

    /**
     * 场景(自由方舟)获取
     */
    @PostMapping("/adapter/contact-screen/non-smart/scene/list")
    Response<List<ScreenHttpSceneResponseDTO>> getNonSmartSceneList(@RequestBody ScreenHttpRequestDTO requestBody);

    /**
     * 场景(户式化)获取
     */
    @PostMapping("/adapter/contact-screen/scene/list")
    Response<List<ScreenHttpSceneResponseDTO>> getSceneList(@RequestBody ScreenHttpRequestDTO requestBody);

    /**
     * 定时场景(户式化)获取
     */
    @PostMapping("/adapter/contact-screen/timing/scene/list")
    Response<List<ScreenHttpTimingSceneResponseDTO>> getTimingSceneList(@RequestBody ScreenHttpRequestDTO requestBody);
    /**
     * 定时场景(户式化)新增/修改
     */
    @PostMapping("/adapter/contact-screen/timing/scene/save-update")
    Response<List<ScreenHttpTimingSceneResponseDTO>> saveOrUpdateTimingScene(@RequestBody List<ScreenHttpSaveOrUpdateTimingSceneRequestDTO> requestBody);
    /**
     * 定时场景(户式化)删除
     */
    @PostMapping("/adapter/contact-screen/timing/scene/delete")
    Response<List<ScreenHttpTimingSceneResponseDTO>> deleteTimingScene(@RequestBody ScreenHttpDeleteTimingSceneDTO requestBody);

    /**
     * 节假日判定
     */
    @PostMapping("/adapter/contact-screen/holidays/check")
    Response<ScreenHttpHolidaysCheckResponseDTO> holidayCheck(@RequestBody ScreenHttpHolidaysCheckDTO requestBody);


    /**
     * 天气请求
     */
    @PostMapping("/adapter/contact-screen/weather")
    Response<ScreenHttpWeatherResponseDTO> getWeather(@RequestBody ScreenHttpRequestDTO requestBody);

}
