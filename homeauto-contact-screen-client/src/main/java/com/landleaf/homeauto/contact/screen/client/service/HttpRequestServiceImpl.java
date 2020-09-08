package com.landleaf.homeauto.contact.screen.client.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpHolidaysCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpRequestDTO;
import com.landleaf.homeauto.common.mqtt.SyncSendUtil;
import com.landleaf.homeauto.common.web.configuration.restful.RestTemplateClient;
import com.landleaf.homeauto.contact.screen.client.dto.ContactScreenHttpResponse;
import com.landleaf.homeauto.contact.screen.client.dto.payload.http.request.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

/**
 * @ClassName HttpRequestServiceImpl
 * @Description: TODO
 * @Author wyl
 * @Date 2020/9/8
 * @Version V1.0
 **/
@Service
@Slf4j
public class HttpRequestServiceImpl implements HttpRequestService {

    @Autowired
    private RestTemplateClient restTemplateClient;


    private static String URL_PRE = "http://40.73.119.101:10013/homeauto-contact-screen/contact-screen/screen/";


    /**
     * 请求通用处理方法
     *
     * @param url        地址
     * @param requestDTO 入参
     * @return
     */
    private ContactScreenHttpResponse handleRequest(String url, ScreenHttpRequestDTO requestDTO) {
        return handleRequest(url, requestDTO, requestDTO.getScreenMac());
    }

    /**
     * 请求通用处理方法
     *
     * @param url        地址
     * @param requestDTO 入参
     * @return
     */
    private ContactScreenHttpResponse handleRequest(String url, Object requestDTO, String screenMac) {
        restTemplateClient.setInterceptors(Arrays.asList(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
                HttpMethod method = httpRequest.getMethod();

                HttpHeaders headers = httpRequest.getHeaders();
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                headers.add(CommonConst.HEADER_MAC, screenMac);
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


    @Override
    public ContactScreenHttpResponse floorRoomDeviceList(ScreenHttpRequestDTO requestDTO) {
        return handleRequest("/floor-room-device/list", requestDTO);
    }

    @Override
    public ContactScreenHttpResponse smartSceneList(ScreenHttpRequestDTO requestDTO) {
        return handleRequest("/scene/list", requestDTO);
    }

    @Override
    public ContactScreenHttpResponse smartSceneTimingList(ScreenHttpRequestDTO requestDTO) {
        return handleRequest("/timing/scene/list", requestDTO);
    }

    @Override
    public ContactScreenHttpResponse newsList(ScreenHttpRequestDTO requestDTO) {
        return handleRequest("/news/list", requestDTO);
    }

    @Override
    public ContactScreenHttpResponse weahter(ScreenHttpRequestDTO requestDTO) {
        return handleRequest("/weather", requestDTO);
    }

    @Override
    public ContactScreenHttpResponse familyCode(ScreenHttpRequestDTO requestDTO) {
        return handleRequest("/familyCode", requestDTO);
    }

    @Override
    public ContactScreenHttpResponse nonSmartSceneSaveOrUpdate(FamilySceneRequestSaveOrUpdateRequestPayload requestDTO, String screenMac) {
        return handleRequest("/scene/save-update", requestDTO, screenMac);
    }

    @Override
    public ContactScreenHttpResponse nonSmartSceneDelete(FamilySceneDeleteRequestPayload requestDTO, String screenMac) {
        return handleRequest("/scene/delete", requestDTO, screenMac);
    }

    @Override
    public ContactScreenHttpResponse timingSceneSaveOrUpdate(FamilyTimingSceneSaveOrUpdateRequestPayload requestDTO, String screenMac) {
        return handleRequest("/timing/scene/save-update", requestDTO, screenMac);
    }

    @Override
    public ContactScreenHttpResponse timingSceneDelete(FamilyTimingSceneDeleteRequestPayload requestDTO, String screenMac) {
        return handleRequest("/timing/scene/delete", requestDTO, screenMac);
    }

    @Override
    public ContactScreenHttpResponse holidaysCheck(ScreenHttpHolidaysCheckDTO requestDTO) {
        return handleRequest("/holidays/check", requestDTO);
    }

    @Override
    public ContactScreenHttpResponse apkVersionCheck(ApkVersionCheckRequestPayload requestDTO, String screenMac) {
        return handleRequest("/apk-version/check", requestDTO,screenMac);
    }
}
