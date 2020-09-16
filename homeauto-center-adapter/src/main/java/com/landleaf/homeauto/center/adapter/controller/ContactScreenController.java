package com.landleaf.homeauto.center.adapter.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.adapter.remote.DeviceRemote;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.enums.screen.MqttCallBackTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.RandomUtil;
import com.landleaf.homeauto.common.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 大屏通讯模块控制器
 *
 * @author wenyilu
 */
@RestController
@RequestMapping("/adapter/contact-screen")
@Slf4j
public class ContactScreenController extends BaseController {

    @Autowired
    private DeviceRemote deviceRemote;

    /**
     * 大屏apk版本检测
     *
     * @param requestDTO 入参
     */
    @PostMapping("/apk-version/check")
    public Response<ScreenHttpApkVersionCheckResponseDTO> apkVersionCheck(@RequestBody ScreenHttpApkVersionCheckDTO requestDTO) {

        AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO = new AdapterHttpApkVersionCheckDTO();
        buildCommonMsg(requestDTO, adapterHttpApkVersionCheckDTO);

        adapterHttpApkVersionCheckDTO.setVersion(requestDTO.getVersion());

        return deviceRemote.apkVersionCheck(adapterHttpApkVersionCheckDTO);
    }


    /**
     * 获取家庭码
     */
    @PostMapping("/familyCode")
    public Response<ScreenHttpFamilyCodeResponseDTO> getFamilyCode(@RequestBody ScreenHttpRequestDTO screenRequestDTO) {
        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
        buildCommonMsg(screenRequestDTO, adapterMessageHttpDTO);
        ScreenHttpFamilyCodeResponseDTO result = new ScreenHttpFamilyCodeResponseDTO();
        result.setFamilyCode(adapterMessageHttpDTO.getFamilyCode());
        return returnSuccess(result);
    }

    /**
     * 楼层房间设备信息请求
     */
    @PostMapping("/floor-room-device/list")
    public Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> getFloorRoomDeviceList(@RequestBody ScreenHttpRequestDTO screenRequestDTO) {


        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
        buildCommonMsg(screenRequestDTO, adapterMessageHttpDTO);

        return deviceRemote.getFloorRoomDeviceList(adapterMessageHttpDTO);

    }

    /**
     * 场景删除(一期无)
     */
    @PostMapping("/scene/delete")
    public Response deleteSmartScene(@RequestBody ScreenHttpDeleteSceneDTO screenRequestDTO) {

//        AdapterHttpDeleteNonSmartSceneDTO adapterMessageHttpDTO = new AdapterHttpDeleteNonSmartSceneDTO();
//        buildCommonMsg(screenRequestDTO, adapterMessageHttpDTO);
//        adapterMessageHttpDTO.setSceneIds(screenRequestDTO.getSceneIds());
//
//
//        return deviceRemote.deleteNonSmartScene(adapterMessageHttpDTO);



        return returnSuccess(null);


    }

    /**
     * 消息公告
     */
    @PostMapping("/news/list")
    public Response<List<ScreenHttpNewsResponseDTO>> getNews(@RequestBody ScreenHttpRequestDTO screenRequestDTO) {

        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
        buildCommonMsg(screenRequestDTO, adapterMessageHttpDTO);

        return deviceRemote.getNews(adapterMessageHttpDTO);
    }


    /**
     * 场景修改/新增（一期无）
     */
    @PostMapping("/scene/save-update")
    public Response<List<SyncSceneInfoDTO>> saveOrUpdateNonSmartScene(@RequestBody List<ScreenHttpSaveOrUpdateSceneDTO> requestBody) {
//        AdapterHttpSaveOrUpdateNonSmartSceneDTO adapterMessageHttpDTO = new AdapterHttpSaveOrUpdateNonSmartSceneDTO();
//        buildCommonMsg(requestBody.get(0), adapterMessageHttpDTO);
//        String familyCode = adapterMessageHttpDTO.getFamilyCode();
//        String familyId = adapterMessageHttpDTO.getFamilyId();
//        String terminalMac = adapterMessageHttpDTO.getTerminalMac();
//        Integer terminalType = adapterMessageHttpDTO.getTerminalType();
//        List<AdapterHttpSaveOrUpdateNonSmartSceneDTO> dtos = requestBody.stream().map(i -> {
//            AdapterHttpSaveOrUpdateNonSmartSceneDTO dto = new AdapterHttpSaveOrUpdateNonSmartSceneDTO();
//            BeanUtils.copyProperties(i, dto);
//            dto.setFamilyId(familyId);
//            dto.setFamilyCode(familyCode);
//            dto.setTerminalMac(terminalMac);
//            dto.setTerminalType(terminalType);
//            return dto;
//
//        }).collect(Collectors.toList());
//
//        return deviceRemote.saveOrUpdateNonSmartScene(dtos);


        return returnSuccess(null);
    }

    /**
     * 场景获取
     */
    @PostMapping("/scene/list")
    public Response<List<SyncSceneInfoDTO>> getSceneList(@RequestBody ScreenHttpRequestDTO requestBody) {
        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
        buildCommonMsg(requestBody, adapterMessageHttpDTO);

        return deviceRemote.getSceneList(adapterMessageHttpDTO);

    }


    /**
     * 定时场景获取
     */
    @PostMapping("/timing/scene/list")
    public Response<List<ScreenHttpTimingSceneResponseDTO>> getTimingSceneList(@RequestBody ScreenHttpRequestDTO requestBody) {

        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
        buildCommonMsg(requestBody, adapterMessageHttpDTO);

        return deviceRemote.getTimingSceneList(adapterMessageHttpDTO);

    }

    /**
     * 定时场景 新增/修改
     */
    @PostMapping("/timing/scene/save-update")
    public Response<List<ScreenHttpTimingSceneResponseDTO>> saveOrUpdateTimingScene(@RequestBody List<ScreenHttpSaveOrUpdateTimingSceneRequestDTO> requestBody) {
        AdapterHttpSaveOrUpdateTimingSceneDTO adapterMessageHttpDTO = new AdapterHttpSaveOrUpdateTimingSceneDTO();

        buildCommonMsg(requestBody.get(0), adapterMessageHttpDTO);

        String familyCode = adapterMessageHttpDTO.getFamilyCode();
        String familyId = adapterMessageHttpDTO.getFamilyId();
        String terminalMac = adapterMessageHttpDTO.getTerminalMac();
        Integer terminalType = adapterMessageHttpDTO.getTerminalType();
        List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos = requestBody.stream().map(i -> {
            AdapterHttpSaveOrUpdateTimingSceneDTO dto = new AdapterHttpSaveOrUpdateTimingSceneDTO();
            BeanUtils.copyProperties(i, dto);
            dto.setFamilyId(familyId);
            dto.setFamilyCode(familyCode);
            dto.setTerminalMac(terminalMac);
            dto.setTerminalType(terminalType);
            return dto;

        }).collect(Collectors.toList());

        return deviceRemote.saveOrUpdateTimingScene(dtos, familyId);


    }

    /**
     * 定时场景 删除
     */
    @PostMapping("/timing/scene/delete")
    public Response<List<ScreenHttpTimingSceneResponseDTO>> deleteTimingScene(@RequestBody ScreenHttpDeleteTimingSceneDTO requestBody) {
        AdapterHttpDeleteTimingSceneDTO adapterMessageHttpDTO = new AdapterHttpDeleteTimingSceneDTO();
        buildCommonMsg(requestBody, adapterMessageHttpDTO);
        adapterMessageHttpDTO.setIds(requestBody.getIds());

        return deviceRemote.deleteTimingScene(adapterMessageHttpDTO);

    }

    /**
     * 节假日判定
     */
    @PostMapping("/holidays/check")
    public Response<ScreenHttpHolidaysCheckResponseDTO> holidayCheck(@RequestBody ScreenHttpHolidaysCheckDTO requestBody) {

        AdapterHttpHolidaysCheckDTO holidaysCheckDTO = new AdapterHttpHolidaysCheckDTO();
        buildCommonMsg(requestBody, holidaysCheckDTO);

        holidaysCheckDTO.setDate(requestBody.getDate());

        return deviceRemote.holidayCheck(holidaysCheckDTO);
    }


    /**
     * 天气请求
     */
    @PostMapping("/weather")
    public Response<ScreenHttpWeatherResponseDTO> getWeather(@RequestBody ScreenHttpRequestDTO requestBody) {

        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
        buildCommonMsg(requestBody, adapterMessageHttpDTO);

        return deviceRemote.getWeather(adapterMessageHttpDTO);

    }

    /**
     * 更改大屏在线状态
     */
    @PostMapping("/update/screen/status")
    public Response updateScreenOnLineStatus(@RequestBody ScreenHttpMqttCallBackDTO requestBody) {

        AdapterHttpMqttCallBackDTO adapterMessageHttpDTO = new AdapterHttpMqttCallBackDTO();

        String action = requestBody.getAction();
        if (StringUtils.equals(action, MqttCallBackTypeEnum.CLIENT_CONNECT.code)) {
            adapterMessageHttpDTO.setStatus(CommonConst.NumberConst.INT_TRUE);
        } else if (StringUtils.equals(action, MqttCallBackTypeEnum.CLIENT_DISCONNECTED.code)) {
            adapterMessageHttpDTO.setStatus(CommonConst.NumberConst.INT_FALSE);
        } else {
            return returnSuccess();
        }
        try {
            buildCommonMsg(requestBody, adapterMessageHttpDTO);
            log.info("调用更新大屏上下线状态接口,{}", JSON.toJSONString(adapterMessageHttpDTO));
            deviceRemote.updateTerminalOnLineStatus(adapterMessageHttpDTO);
        } catch (BusinessException e) {
            log.error("非大屏断线上线通知,忽略,{}", requestBody.getScreenMac());
        }

        return returnSuccess();

    }


    private void buildCommonMsg(ScreenHttpRequestDTO screenHttpRequestDTO, AdapterMessageHttpDTO adapterMessageHttpDTO) {

        adapterMessageHttpDTO.setTerminalMac(screenHttpRequestDTO.getScreenMac());
        adapterMessageHttpDTO.setTerminalType(TerminalTypeEnum.SCREEN.getCode());
        Response<AdapterFamilyDTO> familyDTOResponse = null;
        try {
            familyDTOResponse = deviceRemote.getFamily(adapterMessageHttpDTO.getTerminalMac(), adapterMessageHttpDTO.getTerminalType());
        } catch (Exception e) {
            log.error("[大屏http请求信息]获取家庭信息异常,[终端地址]:{}", adapterMessageHttpDTO.getTerminalMac());
            throw new BusinessException("获取家庭异常");
        }
        if (familyDTOResponse != null && familyDTOResponse.isSuccess()) {
            AdapterFamilyDTO familyDTO = familyDTOResponse.getResult();
            if (familyDTO == null) {
                log.error("[大屏http请求信息]家庭不存在,[终端地址]:{}", adapterMessageHttpDTO.getTerminalMac());
                throw new BusinessException("家庭不存在");
            }
            adapterMessageHttpDTO.setFamilyCode(familyDTO.getFamilyCode());
            adapterMessageHttpDTO.setFamilyId(familyDTO.getFamilyId());
            adapterMessageHttpDTO.setTime(System.currentTimeMillis());
            return;
        }
        log.error("[大屏http请求信息]获取家庭信息异常,[终端地址]:{}", adapterMessageHttpDTO.getTerminalMac());
        throw new BusinessException("获取家庭异常{}", familyDTOResponse == null ? null : familyDTOResponse.getErrorMsg());


    }

}
