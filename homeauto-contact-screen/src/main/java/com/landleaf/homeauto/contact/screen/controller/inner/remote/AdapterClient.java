package com.landleaf.homeauto.contact.screen.controller.inner.remote;

import com.landleaf.homeauto.common.constance.ServerNameConst;
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
     * apk更新结果回调通知
     *
     * @param resultDTO 入参
     */
    @PostMapping("/adapter/contact-screen/apk-update/result/call-back")
    Response apkUpdateResultCallBack(@RequestBody ScreenHttpApkUpdateResultDTO resultDTO);

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
     * 预约获取
     */
    @PostMapping("/adapter/contact-screen/reservation/list")
    Response<List<ScreenHttpNonSmartReservationResponseDTO>> getReservationList(@RequestBody ScreenHttpRequestDTO screenRequestDTO);

    /**
     * 预约（自由方舟）删除
     */
    @PostMapping("/adapter/contact-screen/non-smart/reservation/delete")
    Response deleteNonSmartReservation(@RequestBody ScreenHttpDeleteNonSmartReservationDTO screenRequestDTO);

    /**
     * 预约（自由方舟）修改/新增
     */
    @PostMapping("/adapter/contact-screen/non-smart/reservation/save-update")
    Response<List<ScreenHttpNonSmartSaveOrUpdateReservationResponseDTO>> saveOrUpdateNonSmartReservation(@RequestBody ScreenHttpSaveOrUpdateNonSmartReservationDTO requestBody);

    /**
     * 场景(自由方舟)删除
     */
    @PostMapping("/adapter/contact-screen/non-smart/scene/delete")
    Response deleteNonSmartScene(@RequestBody ScreenHttpDeleteNonSmartSceneDTO screenRequestDTO);

    /**
     * 场景(自由方舟)修改/新增
     */
    @PostMapping("/adapter/contact-screen/non-smart/scene/save-update")
    Response<List<ScreenHttpNonSmartSaveOrUpdateSceneResponseDTO>> saveOrUpdateNonSmartScene(@RequestBody List<ScreenHttpSaveOrUpdateNonSmartSceneDTO> requestBody);

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
     * 节假日判定
     */
    @PostMapping("/adapter/contact-screen/holidays/check")
    Response<ScreenHttpHolidaysCheckResponseDTO> holidayCheck(@RequestBody ScreenHttpHolidaysCheckDTO requestBody);

    /**
     * 产品请求
     */
    @PostMapping("/adapter/contact-screen/product/list")
    Response<List<ScreenHttpProductResponseDTO>> getProductList(@RequestBody ScreenHttpRequestDTO requestBody);

    /**
     * 产品类型请求
     */
    @PostMapping("/adapter/contact-screen/product-type/list")
    Response<List<ScreenHttpProductTypeResponseDTO>> getProductTypeList(@RequestBody ScreenHttpRequestDTO requestBody);

    /**
     * 天气请求
     */
    @PostMapping("/adapter/contact-screen/weather")
    Response<ScreenHttpWeatherResponseDTO> getWeather(@RequestBody ScreenHttpRequestDTO requestBody);

}
