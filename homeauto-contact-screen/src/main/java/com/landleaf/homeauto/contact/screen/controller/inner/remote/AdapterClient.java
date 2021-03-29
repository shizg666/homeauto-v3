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
     * 修改大屏在线离线状态
     * @param requestBody
     */
    @PostMapping("/adapter/contact-screen/update/screen/status")
    Response updateScreenOnLineStatus(@RequestBody ScreenHttpMqttCallBackDTO requestBody);
}
