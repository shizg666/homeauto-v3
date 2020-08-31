package com.landleaf.homeauto.center.adapter.controller;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.adapter.remote.DeviceRemote;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterFamilyDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.*;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyDeviceInfoDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyRoomDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.RandomUtil;
import com.landleaf.homeauto.common.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

//
//        ScreenHttpApkVersionCheckResponseDTO data = new ScreenHttpApkVersionCheckResponseDTO();
//        data.setUpdateFlag(false);
//        data.setUrl("http://www.baidu.com");
//        data.setVersion("1.0.0.1");
//        return returnSuccess(data);
    }


    /**
     * 获取家庭码
     */
    @PostMapping("/familyCode")
    public Response<ScreenHttpFamilyCodeResponseDTO> getFamilyCode(@RequestBody ScreenHttpRequestDTO screenRequestDTO) {
//        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
//        buildCommonMsg(screenRequestDTO, adapterMessageHttpDTO);
//
//        return deviceRemote.getFamilyCode(adapterMessageHttpDTO);


        ScreenHttpFamilyCodeResponseDTO data = new ScreenHttpFamilyCodeResponseDTO();
        data.setFamilyCode("123");
        return returnSuccess(data);
    }

    /**
     * 楼层房间设备信息请求
     */
    @PostMapping("/floor-room-device/list")
    public Response<List<ScreenHttpFloorRoomDeviceResponseDTO>> getFloorRoomDeviceList(@RequestBody ScreenHttpRequestDTO screenRequestDTO) {


//        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
//        buildCommonMsg(screenRequestDTO, adapterMessageHttpDTO);
//
//        return deviceRemote.getFloorRoomDeviceList(adapterMessageHttpDTO);



        List<ScreenHttpFloorRoomDeviceResponseDTO> data = Lists.newArrayList();
        for (int i = 1; i < 2; i++) {
            ScreenHttpFloorRoomDeviceResponseDTO dto = new ScreenHttpFloorRoomDeviceResponseDTO();
            dto.setName("楼层".concat(String.valueOf(i)));
            dto.setOrder(i);
            List<ScreenFamilyRoomDTO> roomDTOS = Lists.newArrayList();
            for (int j = 1; j < 2; j++) {
                ScreenFamilyRoomDTO roomDTO = new ScreenFamilyRoomDTO();
                roomDTO.setRoomName("房间".concat(String.valueOf(j)));
                roomDTO.setRoomType(String.valueOf(j));
                List<ScreenFamilyDeviceInfoDTO> deviceInfoDTOS = Lists.newArrayList();
                for (int k = 1; k < 2; k++) {
                    ScreenFamilyDeviceInfoDTO deviceInfoDTO = new ScreenFamilyDeviceInfoDTO();
                    deviceInfoDTO.setDeviceId(RandomUtil.generateString(10));
                    deviceInfoDTO.setDeviceName("设备".concat(String.valueOf(k)));
                    deviceInfoDTO.setDeviceSn(String.valueOf(k));
                    deviceInfoDTO.setProductCode(RandomUtil.generateNumberString(5));
                    List<ScreenDeviceAttributeDTO> attributes = Lists.newArrayList();
                    ScreenDeviceAttributeDTO attributeDTO1 = new ScreenDeviceAttributeDTO();
                    attributeDTO1.setCode("switch");
                    attributeDTO1.setValue("on");
                    ScreenDeviceAttributeDTO attributeDTO2 = new ScreenDeviceAttributeDTO();
                    attributeDTO2.setCode("temperature");
                    attributeDTO2.setValue("22.2");
                    attributes.add(attributeDTO1);
                    attributes.add(attributeDTO2);
                    deviceInfoDTO.setAttributes(attributes);
                    deviceInfoDTOS.add(deviceInfoDTO);
                }
                roomDTO.setDevices(deviceInfoDTOS);
                roomDTOS.add(roomDTO);
            }
            dto.setRooms(roomDTOS);
            data.add(dto);
        }
        return returnSuccess(data);
    }

    /**
     * 消息公告
     */
    @PostMapping("/news/list")
    public Response<List<ScreenHttpNewsResponseDTO>> getNews(@RequestBody ScreenHttpRequestDTO screenRequestDTO) {

//                AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
//        buildCommonMsg(screenRequestDTO, adapterMessageHttpDTO);
//
//        return deviceRemote.getNews(adapterMessageHttpDTO);

        List<ScreenHttpNewsResponseDTO> data = Lists.newArrayList();
        for (int i = 1; i < 2; i++) {
            ScreenHttpNewsResponseDTO dto = new ScreenHttpNewsResponseDTO();
            dto.setContent("消息公告".concat(String.valueOf(i)));
            dto.setId(RandomUtil.generateString(10));
            dto.setSender("系统管理员");
            dto.setTitle("消息公告");
            dto.setTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            data.add(dto);
        }
        return returnSuccess(data);
    }

    /**
     * 场景(自由方舟)删除
     */
    @PostMapping("/non-smart/scene/delete")
    public Response deleteNonSmartScene(@RequestBody ScreenHttpDeleteNonSmartSceneDTO screenRequestDTO) {

//        AdapterHttpDeleteNonSmartSceneDTO adapterMessageHttpDTO = new AdapterHttpDeleteNonSmartSceneDTO();
//        buildCommonMsg(screenRequestDTO, adapterMessageHttpDTO);
//        adapterMessageHttpDTO.setSceneIds(screenRequestDTO.getSceneIds());
//
//
//        return deviceRemote.deleteNonSmartScene(adapterMessageHttpDTO);

        List<ScreenHttpSceneResponseDTO> data = buildSmartSceneData();


        return returnSuccess(data);


    }

    private List<ScreenHttpSceneResponseDTO> buildSmartSceneData() {
        List<ScreenHttpSceneResponseDTO> data = Lists.newArrayList();
        for (int i = 1; i < 2; i++) {
            ScreenHttpSceneResponseDTO dto = new ScreenHttpSceneResponseDTO();
            dto.setDefaultFlag(1);
            dto.setDefaultFlagScreen(1);
            dto.setFamilyCode(RandomUtil.generateString(10));
            dto.setSceneIcon("http://www.baidu.com");
            dto.setSceneName("测试场景".concat(String.valueOf(i)));
            dto.setSceneId(RandomUtil.generateString(10));
            dto.setSceneType(1);
            dto.setUpdateFlagApp(1);
            List<ScreenHttpSceneActionDTO> actions = Lists.newArrayList();
            for (int j = 1; j < 3; j++) {
                ScreenHttpSceneActionDTO actionDTO = new ScreenHttpSceneActionDTO();
                actionDTO.setDeviceSn(String.valueOf(j));
                actionDTO.setOperator(1);
                actionDTO.setAttributeCode(RandomUtil.generateString(5));
                actionDTO.setAttributeVal(RandomUtil.generateNumberString(5));
                actions.add(actionDTO);
            }
            dto.setActions(actions);
            data.add(dto);
        }
        return data;
    }

    /**
     * 场景(自由方舟)修改/新增
     */
    @PostMapping("/non-smart/scene/save-update")
    public Response<List<ScreenHttpSceneResponseDTO>> saveOrUpdateNonSmartScene(@RequestBody List<ScreenHttpSaveOrUpdateNonSmartSceneDTO> requestBody) {
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


        List<ScreenHttpSceneResponseDTO> data = buildSmartSceneData();
        return returnSuccess(data);
    }

    /**
     * 场景(户式化)获取
     */
    @PostMapping("/scene/list")
    public Response<List<ScreenHttpSceneResponseDTO>> getSceneList(@RequestBody ScreenHttpRequestDTO requestBody) {
//        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
//        buildCommonMsg(requestBody, adapterMessageHttpDTO);
//
//        return deviceRemote.getSceneList(adapterMessageHttpDTO);

        List<ScreenHttpSceneResponseDTO> data = buildSmartSceneData();
        return returnSuccess(data);
    }

    /**
     * 场景(自由方舟)获取
     */
    @PostMapping("/non-smart/scene/list")
    public Response<List<ScreenHttpSceneResponseDTO>> getNonSmartSceneList(@RequestBody ScreenHttpRequestDTO requestBody) {
//        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
//        buildCommonMsg(requestBody, adapterMessageHttpDTO);
//
//        return deviceRemote.getNonSmartSceneList(adapterMessageHttpDTO);
        List<ScreenHttpSceneResponseDTO> data = buildSmartSceneData();
        return returnSuccess(data);
    }

    /**
     * 定时场景(户式化)获取
     */
    @PostMapping("/timing/scene/list")
    public Response<List<ScreenHttpTimingSceneResponseDTO>> getTimingSceneList(@RequestBody ScreenHttpRequestDTO requestBody) {

//        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
//        buildCommonMsg(requestBody, adapterMessageHttpDTO);
//
//        return deviceRemote.getTimingSceneList(adapterMessageHttpDTO);

        List<ScreenHttpTimingSceneResponseDTO> data = buildTimingSceneData();

        return returnSuccess(data);

    }

    private List<ScreenHttpTimingSceneResponseDTO> buildTimingSceneData() {
        List<ScreenHttpTimingSceneResponseDTO> data = Lists.newArrayList();
        for (int i = 1; i < 2; i++) {
            ScreenHttpTimingSceneResponseDTO dto = new ScreenHttpTimingSceneResponseDTO();
            dto.setEnabled(1);
            dto.setEndDate("2020-08-18 14:03:02");
            dto.setExecuteTime("2020-08-18 14:03:02");
            dto.setSceneName("测试场景".concat(String.valueOf(i)));
            dto.setSkipHoliday(1);
            dto.setStartDate("2020-08-18 14:03:02");
            dto.setTimingId(RandomUtil.generateString(10));
            dto.setType(1);
            dto.setWeekday("星期一");
            dto.setSceneId(RandomUtil.generateNumberString(10));
            data.add(dto);
        }
        return data;
    }

    /**
     * 定时场景 新增/修改
     */
    @PostMapping("/timing/scene/save-update")
    public Response<List<ScreenHttpTimingSceneResponseDTO>> saveOrUpdateTimingScene(@RequestBody List<ScreenHttpSaveOrUpdateTimingSceneRequestDTO> requestBody) {
//        AdapterHttpSaveOrUpdateTimingSceneDTO adapterMessageHttpDTO = new AdapterHttpSaveOrUpdateTimingSceneDTO();
//        buildCommonMsg(requestBody.get(0), adapterMessageHttpDTO);
//        String familyCode = adapterMessageHttpDTO.getFamilyCode();
//        String familyId = adapterMessageHttpDTO.getFamilyId();
//        String terminalMac = adapterMessageHttpDTO.getTerminalMac();
//        Integer terminalType = adapterMessageHttpDTO.getTerminalType();
//        List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos = requestBody.stream().map(i -> {
//            AdapterHttpSaveOrUpdateTimingSceneDTO dto = new AdapterHttpSaveOrUpdateTimingSceneDTO();
//            BeanUtils.copyProperties(i, dto);
//            dto.setFamilyId(familyId);
//            dto.setFamilyCode(familyCode);
//            dto.setTerminalMac(terminalMac);
//            dto.setTerminalType(terminalType);
//            return dto;
//
//        }).collect(Collectors.toList());
//
//        return deviceRemote.saveOrUpdateTimingScene(dtos);


        List<ScreenHttpTimingSceneResponseDTO> data = buildTimingSceneData();

        return returnSuccess(data);

    }

    /**
     * 定时场景 删除
     */
    @PostMapping("/timing/scene/delete")
    public Response<List<ScreenHttpTimingSceneResponseDTO>> deleteTimingScene(@RequestBody ScreenHttpDeleteTimingSceneDTO requestBody) {
//        AdapterHttpDeleteTimingSceneDTO adapterMessageHttpDTO = new AdapterHttpDeleteTimingSceneDTO();
//        buildCommonMsg(requestBody, adapterMessageHttpDTO);
//        adapterMessageHttpDTO.setIds(requestBody.getIds());
//
//
//        return deviceRemote.deleteTimingScene(adapterMessageHttpDTO);

        List<ScreenHttpTimingSceneResponseDTO> data = buildTimingSceneData();

        return returnSuccess(data);

    }

    /**
     * 节假日判定
     */
    @PostMapping("/holidays/check")
    public Response<ScreenHttpHolidaysCheckResponseDTO> holidayCheck(@RequestBody ScreenHttpHolidaysCheckDTO requestBody) {

//        AdapterHttpHolidaysCheckDTO holidaysCheckDTO = new AdapterHttpHolidaysCheckDTO();
//        buildCommonMsg(requestBody, holidaysCheckDTO);
//
//        holidaysCheckDTO.setDate(requestBody.getDate());
//
//        return deviceRemote.holidayCheck(holidaysCheckDTO);



        ScreenHttpHolidaysCheckResponseDTO data = new ScreenHttpHolidaysCheckResponseDTO();
        data.setResult(true);
        return returnSuccess(data);
    }


    /**
     * 天气请求
     */
    @PostMapping("/weather")
    public Response<ScreenHttpWeatherResponseDTO> getWeather(@RequestBody ScreenHttpRequestDTO requestBody) {
//
//        AdapterMessageHttpDTO adapterMessageHttpDTO = new AdapterMessageHttpDTO();
//        buildCommonMsg(requestBody, adapterMessageHttpDTO);
//
//        return deviceRemote.getWeather(adapterMessageHttpDTO);

        ScreenHttpWeatherResponseDTO data = new ScreenHttpWeatherResponseDTO();
        data.setCalender("四月十三");
        data.setCityName("上海");
        data.setCityUrl(null);
        data.setColdLevel("少发");
        data.setDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        data.setDressLevel("热");
        data.setHumidity("86");
        data.setNewPicUrl("http://47.100.3.98:30002/images/new_ico/02阴.png");
        data.setPicUrl("http://47.100.3.98:30002/images/new_ico/02阴.png");
        data.setPm25("35");
        data.setWindLevel("1级");
        data.setWindDirection("西北风");
        data.setWeek("星期四");
        data.setWeatherStatus("阴");
        data.setUvLevel("最弱");
        data.setUpdateTime("2020.06.04 08:00 发布");
        return returnSuccess(data);
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
