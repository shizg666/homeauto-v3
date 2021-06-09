package com.landleaf.homeauto.contact.screen.client.controller;


import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.client.dto.payload.http.request.*;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.DeviceStatusUpdateRequestPayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.FamilyEventAlarmPayload;
import com.landleaf.homeauto.contact.screen.client.dto.payload.mqtt.upload.ScreenSceneSetRequestPayload;
import com.landleaf.homeauto.contact.screen.client.service.HttpRequestService;
import com.landleaf.homeauto.contact.screen.client.service.MqttRequestService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 大屏http/https请求入口
 *
 * @author wenyilu
 */
@RequestMapping("/client")
@RestController
@Slf4j
@Api(value = "/client", tags = {"大屏请求云端http入口"})
public class ContactScreenOuterHttpEntrance {

    @Autowired
    private HttpRequestService httpRequestService;
    @Autowired
    private MqttRequestService mqttRequestService;


    /**
     * 绑定家庭
     */
    @RequestMapping(value = "/family/bind", method = {RequestMethod.POST})
    public ContactScreenHttpResponse familyBind(@RequestParam String screenMac, @RequestBody FamilyBindRequestPayload requestDTO) {

        return httpRequestService.familyBind(requestDTO, screenMac);

    }

    /**
     * 楼层房间设备配置信息请求
     */
    @RequestMapping(value = "/floor-room-device/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse floorRoomDeviceList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return httpRequestService.floorRoomDeviceList(requestDTO);

    }

    /**
     * 场景信息请求
     */
    @RequestMapping(value = "/scene/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse smartSceneList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return httpRequestService.smartSceneList(requestDTO);

    }

    /**
     * 4.0第一期暂不考虑
     * 定时场景信息请求
     */
    @RequestMapping(value = "/timing/scene/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse smartSceneTimingList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return httpRequestService.smartSceneTimingList(requestDTO);

    }

    /**
     * 只测试联通性
     * 消息公告信息请求
     */
    @RequestMapping(value = "/news/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse newsList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return httpRequestService.newsList(requestDTO);

    }

    /**
     * 查询天气
     */
    @RequestMapping(value = "/weather", method = {RequestMethod.POST})
    public ContactScreenHttpResponse weahter(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return httpRequestService.weahter(requestDTO);

    }
    /**
     * 查询天气
     */
    @RequestMapping(value = "/city/weather", method = {RequestMethod.POST})
    public ContactScreenHttpResponse cityWeather(@RequestParam String screenMac, @RequestBody CityWeatherRequestPayload requestPayload) {

        return httpRequestService.cityWeather(requestPayload, screenMac);

    }

    /**
     * 判断是否是节假日
     */
    @RequestMapping(value = "/holidays/check", method = {RequestMethod.POST})
    public ContactScreenHttpResponse holidaysCheck(@RequestParam String screenMac, @RequestBody HolidaysCheckRequestPayload requestPayload) throws Exception {

        return httpRequestService.holidaysCheck(requestPayload, screenMac);


    }

    /**
     * 上传控制场景
     */
    @RequestMapping(value = "/upload/control/scene", method = {RequestMethod.POST})
    public void uploadControlScene(@RequestParam String screenMac, @RequestBody ScreenSceneSetRequestPayload payload) throws Exception {

        mqttRequestService.uploadControlScene(screenMac, payload);


    }

    /**
     * 上传设备状态数据
     */
    @RequestMapping(value = "/upload/device/status", method = {RequestMethod.POST})
    public void uploadDeviceStatus(@RequestParam String screenMac, @RequestBody DeviceStatusUpdateRequestPayload payload) throws Exception {

        mqttRequestService.uploadDeviceStatus(screenMac, payload);

    }

    /**
     * 上传安防报警事件
     */
    @RequestMapping(value = "/upload/alarm/event", method = {RequestMethod.POST})
    public void uploadAlarmEvent(@RequestParam String screenMac, @RequestBody FamilyEventAlarmPayload payload) throws Exception {

        mqttRequestService.uploadAlarmEvent(screenMac, payload);

    }

    /**
     * The logic needs to be redefined by the decision-makers,
     * and it has changed over and over again. It’s all big brother.
     * 获取家庭码---接口已实现
     */
    @RequestMapping(value = "/familyCode", method = {RequestMethod.POST})
    public ContactScreenHttpResponse familyCode(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return httpRequestService.familyCode(requestDTO);

    }


    /**
     * 4.0第一期暂不考虑
     * 场景修改/新增
     */
    @RequestMapping(value = "/scene/save-update", method = {RequestMethod.POST})
    public ContactScreenHttpResponse nonSmartSceneSaveOrUpdate(@RequestBody FamilySceneRequestSaveOrUpdateRequestPayload requestDTO,
                                                               @RequestParam String screenMac) {
        return httpRequestService.nonSmartSceneSaveOrUpdate(requestDTO, screenMac);

    }


    /**
     * 4.0第一期暂不考虑
     * 场景删除
     */
    @RequestMapping(value = "/scene/delete", method = {RequestMethod.POST})
    public ContactScreenHttpResponse nonSmartSceneDelete(@RequestBody FamilySceneDeleteRequestPayload requestDTO,
                                                         @RequestParam String screenMac) {
        return httpRequestService.nonSmartSceneDelete(requestDTO, screenMac);
    }

    /**
     * 4.0第一期暂不考虑
     * 定时配置场景修改/新增
     */
    @RequestMapping(value = "/timing/scene/save-update", method = {RequestMethod.POST})
    public ContactScreenHttpResponse timingSceneSaveOrUpdate(@RequestBody FamilyTimingSceneSaveOrUpdateRequestPayload requestDTO,
                                                             @RequestParam String screenMac) {
        return httpRequestService.timingSceneSaveOrUpdate(requestDTO, screenMac);

    }


    /**
     * 4.0第一期暂不考虑
     * 定时配置场景 删除
     */
    @RequestMapping(value = "/timing/scene/delete", method = {RequestMethod.POST})
    public ContactScreenHttpResponse timingSceneDelete(@RequestBody FamilyTimingSceneDeleteRequestPayload requestDTO,
                                                       @RequestParam String screenMac) {
        return httpRequestService.timingSceneDelete(requestDTO, screenMac);

    }


    /**
     * 无
     * apk更新检测
     */
    @RequestMapping(value = "/apk-version/check", method = {RequestMethod.POST})
    public ContactScreenHttpResponse apkVersionCheck(@RequestBody ApkVersionCheckRequestPayload requestDTO,
                                                     @RequestParam String screenMac) throws Exception {

        return httpRequestService.apkVersionCheck(requestDTO, screenMac);

    }


}
