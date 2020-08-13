package com.landleaf.homeauto.contact.screen.client.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.*;
import com.landleaf.homeauto.common.web.configuration.restful.RestTemplateClient;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHttpResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private RestTemplateClient restTemplateClient;

    private static String URL_PRE = "http://127.0.0.1:10011//homeauto-contact-screen/contact-screen/screen/";


    /**
     * 楼层房间设备配置信息请求
     */
    @RequestMapping(value = "/floor-room-device/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse floorRoomDeviceList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return handleRequest("/floor-room-device/list", requestDTO);
    }

    /**
     * 场景（自由方舟）信息请求
     */
    @RequestMapping(value = "/non-smart/scene/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse nonSmartSceneList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return handleRequest("/non-smart/scene/list", requestDTO);

    }

    /**
     * 场景(户式化)信息请求
     */
    @RequestMapping(value = "/scene/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse smartSceneList(@RequestBody ScreenHttpRequestDTO requestDTO) {


        return handleRequest("/non-smart/scene/list", requestDTO);

    }

    /**
     * 定时场景信息请求
     */
    @RequestMapping(value = "/scene/timing/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse smartSceneTimingList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return handleRequest("/scene/timing/list", requestDTO);

    }

    /**
     * 产品信息请求
     */
    @RequestMapping(value = "/product/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse productList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return handleRequest("/product/list", requestDTO);

    }

    /**
     * 消息公告信息请求
     */
    @RequestMapping(value = "/news/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse newsList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return handleRequest("/news/list", requestDTO);

    }

    /**
     * 预约（自由方舟）信息请求
     */
    @RequestMapping(value = "/non-smart/reservation/list", method = {RequestMethod.POST})
    public ContactScreenHttpResponse nonSmartReservationList(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return handleRequest("/non-smart/reservation/list", requestDTO);
    }

    /**
     * 查询天气
     */
    @RequestMapping(value = "/weahter", method = {RequestMethod.POST})
    public ContactScreenHttpResponse weahter(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return handleRequest("/weahter", requestDTO);

    }

    /**
     * 获取家庭码
     */
    @RequestMapping(value = "/familyCode", method = {RequestMethod.POST})
    public ContactScreenHttpResponse familyCode(@RequestBody ScreenHttpRequestDTO requestDTO) {

        return handleRequest("/familyCode", requestDTO);

    }

    /**
     * 预约（自由方舟）修改/新增
     */
    @RequestMapping(value = "/non-smart/reservation/save-update", method = {RequestMethod.POST})
    public ContactScreenHttpResponse nonSmartReservationSaveOrUpdate(@RequestBody ScreenHttpSaveOrUpdateNonSmartReservationDTO requestDTO) {

        return handleRequest("/non-smart/reservation/save-update", requestDTO);
    }

    /**
     * 预约（自由方舟）删除
     */
    @RequestMapping(value = "/non-smart/reservation/delete", method = {RequestMethod.POST})
    public ContactScreenHttpResponse nonSmartReservationDelete(@RequestBody ScreenHttpDeleteNonSmartSceneDTO requestDTO) {

        return handleRequest("/non-smart/reservation/delete", requestDTO);
    }

    /**
     * 场景（自由方舟）修改/新增
     */
    @RequestMapping(value = "/non-smart/scene/save-update", method = {RequestMethod.POST})
    public ContactScreenHttpResponse nonSmartSceneSaveOrUpdate(@RequestBody ScreenHttpSaveOrUpdateNonSmartSceneDTO requestDTO) {

        return handleRequest("/non-smart/scene/save-update", requestDTO);
    }

    /**
     * 场景（自由方舟）删除
     */
    @RequestMapping(value = "/non-smart/scene/delete", method = {RequestMethod.POST})
    public ContactScreenHttpResponse nonSmartSceneDelete(@RequestBody ScreenHttpDeleteNonSmartReservationDTO requestDTO) {

        return handleRequest("/non-smart/scene/delete", requestDTO);
    }

    /**
     * 判断是否是节假日
     */
    @RequestMapping(value = "/holidays/check", method = {RequestMethod.POST})
    public ContactScreenHttpResponse holidaysCheck(@RequestBody ScreenHttpHolidaysCheckDTO requestDTO) throws Exception {

        return handleRequest("/holidays/check", requestDTO);
    }

    /**
     * 更新结果通知回调
     */
    @RequestMapping(value = "/apk-update/result", method = {RequestMethod.POST})
    public ContactScreenHttpResponse holidaysCheck(@RequestBody ScreenHttpApkUpdateResultDTO requestDTO) throws Exception {

        return handleRequest("/apk-update/result", requestDTO);
    }

    /**
     * 上传设备状态数据
     */
    @RequestMapping(value = "/upload/device/status", method = {RequestMethod.POST})
    public void uploadDeviceStatus() throws Exception {


    }

    /**
     * 上传安防报警事件
     */
    @RequestMapping(value = "/upload/alarm/event", method = {RequestMethod.POST})
    public void uploadAlarmEvent() throws Exception {

    }


    /**
     * 请求通用处理方法
     *
     * @param url        地址
     * @param requestDTO 入参
     * @return
     */
    private ContactScreenHttpResponse handleRequest(String url, ScreenHttpRequestDTO requestDTO) {
        restTemplateClient.setInterceptors(Arrays.asList(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                HttpMethod method = httpRequest.getMethod();

                HttpHeaders headers = httpRequest.getHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                List<String> cookies = new ArrayList<>();
                cookies.add("JSESSIONID=" + Strings.nullToEmpty("FA2BB1083498166037FE97E33F329645"));
                headers.put(HttpHeaders.COOKIE, cookies);
                ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
                return response;
            }
        }));

        log.info("请求地址:{},入参:{}", url, JSON.toJSONString(requestDTO));
        ContactScreenHttpResponse contactScreenHttpResponse = restTemplateClient.postForObject(URL_PRE.concat(url), requestDTO, new TypeReference<ContactScreenHttpResponse>() {
        });
        log.info("返回结果:{}", JSON.toJSONString(contactScreenHttpResponse));
        return contactScreenHttpResponse;
    }


}
