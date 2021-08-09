package com.landleaf.homeauto.contact.screen.controller.inner.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.screen.callback.ScreenMqttCallBackOnLineDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
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
     * 获取家庭模板信息
     * @param requestDTO 大屏模板请求体
     * @return 大屏项目模板
     */
    @PostMapping("/adapter/contact-screen/project/templates")
    Response<List<ScreenFamilyModelResponseDTO>> getProjectTemplate(@RequestBody ScreenHttpProjectDTO requestDTO);

    /**
     * 获取模板配置和场景
     * @param requestDTO 大屏请求
     * @return 模板配置
     */
    @PostMapping("/adapter/contact-screen/template/config")
    Response<ScreenHttpFloorRoomDeviceSceneResponseDTO> getTemplateConfig(@RequestBody ScreenHttpProjectHouseTypeDTO requestDTO);

    /**
     * apk版本检测
     *
     * @param resultDTO 入参
     */
    @PostMapping("/adapter/contact-screen/apk-version/check")
    Response<ScreenHttpApkVersionCheckResponseDTO> apkVersionCheck(@RequestBody ScreenHttpApkVersionCheckDTO resultDTO);
    /**
     * 大屏主动绑定信息请求
     */
    @PostMapping("/adapter/contact-screen/family/bind")
    Response familyBind(@RequestBody ScreenHttpFamilyBindDTO screenRequestDTO);
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
     * 场景获取
     */
    @PostMapping("/adapter/contact-screen/scene/list")
    Response<List<SyncSceneInfoDTO>> getSceneList(@RequestBody ScreenHttpRequestDTO requestBody);

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
    /**
     * 天气请求
     */
    @PostMapping("/adapter/contact-screen/city/weather")
    Response<ScreenHttpWeatherResponseDTO> getCityWeather(@RequestBody ScreenHttpCityWeatherDTO requestBody);
    /**
     * 定时场景获取
     */
    @PostMapping("/adapter/contact-screen/timing/scene/list")
    Response<List<ScreenHttpTimingSceneResponseDTO>> getTimingSceneList(@RequestBody ScreenHttpRequestDTO requestBody);
    /**
     * 定时场景新增/修改
     */
    @PostMapping("/adapter/contact-screen/timing/scene/save-update")
    Response<List<ScreenHttpTimingSceneResponseDTO>> saveOrUpdateTimingScene(@RequestBody List<ScreenHttpSaveOrUpdateTimingSceneRequestDTO> requestBody);
    /**
     * 定时场景删除
     */
    @PostMapping("/adapter/contact-screen/timing/scene/delete")
    Response<List<ScreenHttpTimingSceneResponseDTO>> deleteTimingScene(@RequestBody ScreenHttpDeleteTimingSceneDTO requestBody);
    /**
     * 修改大屏在线离线状态
     * @param requestBody
     */
    @PostMapping("/adapter/contact-screen/update/screen/status")
    Response updateScreenOnLineStatus(@RequestBody ScreenHttpMqttCallBackDTO requestBody);



}
