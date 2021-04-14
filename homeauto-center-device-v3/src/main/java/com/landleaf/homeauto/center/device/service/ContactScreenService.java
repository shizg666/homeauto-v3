package com.landleaf.homeauto.center.device.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateSceneActionConfig;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.remote.WeatherRemote;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyDeviceInfoDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyRoomDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneActionDTO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneDTO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.mqtt.MqttClientInfo;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ContactScreenService
 * @Author wyl
 * @Date 2020/8/31
 * @Version V1.0
 **/
@Service
public class ContactScreenService implements IContactScreenService {
    @Autowired
    private IHomeAutoFamilyService homeAutoFamilyService;
    @Autowired
    private WeatherRemote weatherRemote;
    @Autowired
    private IFamilySceneTimingService familySceneTimingService;

    @Autowired
    private IMsgNoticeService msgNoticeService;

    @Autowired
    private IVacationSettingService vacationSettingService;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ConfigCacheProvider configCacheProvider;
    @Autowired
    private IAppService appService;
    @Autowired
    private IHouseTemplateRoomService templateRoomService;
    @Autowired
    private IHouseTemplateDeviceService templateDeviceService;
    @Autowired
    private IHouseTemplateSceneService templateSceneService;
    @Autowired
    private ITemplateSceneActionConfigService sceneActionConfigService;


    @Override
    public ScreenHttpWeatherResponseDTO getWeather(String familyId) {
        ScreenHttpWeatherResponseDTO data = new ScreenHttpWeatherResponseDTO();
        String weatherCode = homeAutoFamilyService.getWeatherCodeByFamilyId(familyId);
        Response<WeatherBO> response = weatherRemote.getWeatherByCode(weatherCode);
        if (response != null && response.isSuccess()) {
            WeatherBO result = response.getResult();
            BeanUtils.copyProperties(result, data);
        }
        return data;
    }

    @Override
    public List<ScreenHttpTimingSceneResponseDTO> getTimingSceneList(Long familyId) {
        List<ScreenHttpTimingSceneResponseDTO> result = Lists.newArrayList();

        List<ScreenFamilySceneTimingBO> sceneTimingBOS = familySceneTimingService.getTimingScenesByFamilyId(familyId);
        if (!CollectionUtils.isEmpty(sceneTimingBOS)) {
            result = sceneTimingBOS.stream().map(s -> {
                ScreenHttpTimingSceneResponseDTO data = new ScreenHttpTimingSceneResponseDTO();
                data.setSkipHoliday(s.getSkipHoliday());
                data.setEnabled(s.getEnabled());
                data.setSceneId(s.getSceneId());
                data.setSceneName(s.getSceneName());
                data.setWeekday(s.getWeekday());
                data.setType(s.getType());
                data.setTimingId(s.getTimingId());
                if (s.getExecuteTime() != null) {
                    data.setExecuteTime(DateUtils.toTimeString(s.getExecuteTime(), "HH:mm"));
                }
                if (s.getStartDate() != null) {
                    data.setStartDate(DateUtils.toTimeString(s.getStartDate(), "yyyy.MM.dd"));
                }
                if (s.getEndDate() != null) {
                    data.setEndDate(DateUtils.toTimeString(s.getEndDate(), "yyyy.MM.dd"));
                }
                return data;
            }).collect(Collectors.toList());
        }
        return result;

    }

    @Override
    public List<ScreenHttpTimingSceneResponseDTO> deleteTimingScene(List<Long> timingIds, Long familyId) {
        familySceneTimingService.deleteTimingScene(timingIds, familyId);
        return getTimingSceneList(familyId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ScreenHttpTimingSceneResponseDTO> saveOrUpdateTimingScene(List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos, Long familyId) {
        List<ScreenHttpTimingSceneResponseDTO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(dtos) || familyId==null) {
            return result;
        }
        List<FamilySceneTimingDO> timingDOList = dtos.stream().map(i -> {
            FamilySceneTimingDO timingDO = new FamilySceneTimingDO();
            timingDO.setEnableFlag(i.getEnabled());
            if (!StringUtils.isEmpty(i.getEndDate())) {
                timingDO.setEndDate(DateUtils.parseLocalDate(i.getEndDate(), "yyyy.MM.dd"));
            }
            if (!StringUtils.isEmpty(i.getStartDate())) {
                timingDO.setStartDate(DateUtils.parseLocalDate(i.getStartDate(), "yyyy.MM.dd"));
            }
            if (!StringUtils.isEmpty(i.getExecuteTime())) {
                timingDO.setExecuteTime(DateUtils.parseLocalTime(i.getExecuteTime(), "HH:mm"));
            }
            timingDO.setHolidaySkipFlag(i.getSkipHoliday());
            timingDO.setSceneId(Long.parseLong(i.getSceneId()));
            timingDO.setType(i.getType());
            timingDO.setWeekday(i.getWeekday());
            if(!StringUtils.isEmpty(i.getTimingId())&& NumberUtils.isDigits(i.getTimingId())){
                timingDO.setId(BeanUtil.convertString2Long(i.getTimingId()));
            }
            timingDO.setFamilyId(familyId);
            return timingDO;
        }).collect(Collectors.toList());

        Map<String, ScreenHttpTimingSceneResponseDTO> timingSceneMap = Maps.newHashMap();
        List<ScreenHttpTimingSceneResponseDTO> existTimingScenes = getTimingSceneList(familyId);
        if (!CollectionUtils.isEmpty(existTimingScenes)) {
            timingSceneMap = existTimingScenes.stream().collect(Collectors.toMap(ScreenHttpTimingSceneResponseDTO::getTimingId, s -> s));
        }
        Map<String, ScreenHttpTimingSceneResponseDTO> finalTimingSceneMap = timingSceneMap;
        List<FamilySceneTimingDO> saveData = timingDOList.stream().filter(i -> {
            String timingId = String.valueOf(i.getId());
            if (StringUtils.isEmpty(timingId)|| finalTimingSceneMap.get(timingId) == null) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<FamilySceneTimingDO> updateData = timingDOList.stream().filter(i -> {
            String timingId = String.valueOf(i.getId());
            if (StringUtils.isEmpty(timingId)|| finalTimingSceneMap.get(timingId) == null) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(saveData)){
            familySceneTimingService.saveBatch(saveData);
        }
        if(!CollectionUtils.isEmpty(updateData)){
            familySceneTimingService.updateBatchById(updateData);
        }

        return getTimingSceneList(familyId);
    }


    @Override
    public List<ScreenHttpNewsResponseDTO> getNews(Long familyId) {
        List<ScreenHttpNewsResponseDTO> result = Lists.newArrayList();
        ScreenFamilyBO familyInfo = configCacheProvider.getFamilyInfo(familyId);
        List<MsgNoticeDO> msgNoticeDOS = msgNoticeService.queryMsgNoticeByProjectIdForScreen(familyInfo.getProjectId());
        if (!CollectionUtils.isEmpty(msgNoticeDOS)) {
            result.addAll(msgNoticeDOS.stream().map(i -> {
                ScreenHttpNewsResponseDTO dto = new ScreenHttpNewsResponseDTO();
                dto.setTime(i.getSendTime() != null ? LocalDateTimeUtil.formatTime(i.getSendTime(), "yyyy-MM-dd HH:mm:ss") : "");
                dto.setTitle(i.getName());
                dto.setId(i.getId());
                dto.setSender(i.getReleaseUser());
                dto.setContent(i.getContent());
                return dto;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public List<SyncSceneInfoDTO> getSceneList(String houseTemplateId) {
        List<SyncSceneInfoDTO> listSyncScene = Lists.newArrayList();
        List<HouseTemplateScene> scenes = templateSceneService.getScenesByTemplate(Long.parseLong(houseTemplateId));
        if(CollectionUtils.isEmpty(scenes)){
            return listSyncScene;
        }
        Map<Long,Map<String, List<TemplateSceneActionConfig>>> ACTION_MAP = Maps.newHashMap();
        List<TemplateSceneActionConfig> actions = sceneActionConfigService.getActionsByTemplateId(Long.parseLong(houseTemplateId));
        if(!CollectionUtils.isEmpty(actions)){
            actions.stream().collect(Collectors.groupingBy(TemplateSceneActionConfig::getSceneId)).forEach((k,v)->{
                ACTION_MAP.put(k,v.stream().collect(Collectors.groupingBy(TemplateSceneActionConfig::getDeviceSn)));
            });
        }
        listSyncScene.addAll(scenes.stream().map(s -> {
            SyncSceneInfoDTO dto = new SyncSceneInfoDTO();
            dto.setSceneId(String.valueOf(s.getId()));
            dto.setSceneName(s.getName());
            List<SyncSceneDTO> tmpActions = Lists.newArrayList();
            Map<String, List<TemplateSceneActionConfig>> deviceMap = ACTION_MAP.get(s.getId());
            if (deviceMap != null && deviceMap.size() > 0) {
                deviceMap.forEach((d, v) -> {
                    SyncSceneDTO sceneDTO = new SyncSceneDTO();
                    sceneDTO.setDeviceSn(d);
                    sceneDTO.setProductCode(v.get(0).getProductCode());
                    sceneDTO.setAttrs(v.stream().map(i -> {
                        SyncSceneActionDTO actionDTO = new SyncSceneActionDTO();
                        actionDTO.setAttrTag(i.getAttributeCode());
                        actionDTO.setAttrValue(i.getAttributeVal());
                        return actionDTO;
                    }).collect(Collectors.toList()));
                    tmpActions.add(sceneDTO);
                });
            }
            dto.setActions(tmpActions);
            return dto;
        }).collect(Collectors.toList()));
        return listSyncScene;
    }

    @Override
    public ScreenHttpHolidaysCheckResponseDTO holidayCheck(String date) {
        ScreenHttpHolidaysCheckResponseDTO data = new ScreenHttpHolidaysCheckResponseDTO();

        Integer someDayType = vacationSettingService.getSomeDayType(date);

        data.setResult((someDayType != null && someDayType.intValue() == 1) ? true : false);
        return data;
    }

    @Override
    public int getOnlineScreenNum() {
        int count = 0;

        List<String> macList = homeAutoFamilyService.getScreenMacList();

        for (String screen_mac : macList) {

            if (redisUtils.hasKey(RedisCacheConst.CONTACT_SCREEN_MQTT_CLIENT_STATUS)) {
                Object hget = redisUtils.hgetEx(RedisCacheConst.CONTACT_SCREEN_MQTT_CLIENT_STATUS, screen_mac);
                if (hget != null) {
                    String mqtt_info = (String) hget;
                    MqttClientInfo mqttClientInfo = JSON.parseObject(mqtt_info, MqttClientInfo.class);
                    if ((mqttClientInfo) != null &&
                            mqttClientInfo.isConnected() &&
                            mqttClientInfo.getProto_name().equals("MQTT")) {
                        count = count + 1;
                    }
                }
            }

        }
        return count;
    }

    @Override
    public ScreenFamilyBO getFamilyInfoByTerminalMac(String mac) {
        return configCacheProvider.getFamilyInfoByMac(mac);
    }

    @Override
    public ScreenTemplateDeviceBO getFamilyDeviceBySn(Long houseTemplateId, Long familyId, String deviceSn) {


        return configCacheProvider.getFamilyDeviceBySn(houseTemplateId, deviceSn);
    }

    @Override
    public List<ScreenProductAttrCategoryBO> getDeviceAttrsByProductCode(String productCode) {
        return configCacheProvider.getDeviceAttrsByProductCode(productCode);
    }

    /**
     * 通知大屏定时场景配置更新
     *
     * @param familyId
     * @param typeEnum
     * @return void
     * @author wenyilu
     * @date 2021/1/7 9:31
     */
    @Override
    public void notifySceneTimingConfigUpdate(Long familyId, ContactScreenConfigUpdateTypeEnum typeEnum) {
        ScreenFamilyBO familyInfo = configCacheProvider.getFamilyInfo(familyId);

        AdapterConfigUpdateDTO adapterConfigUpdateDTO = new AdapterConfigUpdateDTO();
        adapterConfigUpdateDTO.buildBaseInfo(BeanUtil.convertLong2String(familyId), familyInfo.getCode(),
                BeanUtil.convertLong2String(familyInfo.getTemplateId()), familyInfo.getScreenMac(),
                System.currentTimeMillis());
        adapterConfigUpdateDTO.setUpdateType(typeEnum.code);
        appService.configUpdateConfig(adapterConfigUpdateDTO);
    }

    @Override
    public List<ScreenProductAttrBO> getDeviceFunctionAttrsByProductCode(String productCode) {
        List<ScreenProductAttrCategoryBO> deviceAttrs = getDeviceAttrsByProductCode(productCode);
        return deviceAttrs.stream().filter(i -> {
            return i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType();
        }).collect(Collectors.toList()).stream()
                .map(i -> i.getAttrBO()).collect(Collectors.toList()).stream().collect(Collectors.toList());

    }

    @Override
    public List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(String templateId) {
        List<ScreenHttpFloorRoomDeviceResponseDTO> result = Lists.newArrayList();
        List<TemplateRoomDO> rooms = templateRoomService.getRoomsByTemplateId(BeanUtil.convertString2Long(templateId));
        List<TemplateDeviceDO> devices = templateDeviceService.listByTemplateId(BeanUtil.convertString2Long(templateId));

        Map<String, List<TemplateRoomDO>> floor_room_group = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(rooms)) {
            floor_room_group = rooms.stream().collect(Collectors.groupingBy(TemplateRoomDO::getFloor));
        }
        Map<String, List<TemplateDeviceDO>> room_device_map = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(devices)) {
            room_device_map = devices.stream().collect(Collectors.groupingBy(i->{
                return String.valueOf(i.getRoomId());
            }));
        }
        Map<String, List<TemplateRoomDO>> finalFloor_room_group = floor_room_group;
        Map<String, List<TemplateDeviceDO>> finalRoom_device_map = room_device_map;
        floor_room_group.forEach((k, v) -> {
            ScreenHttpFloorRoomDeviceResponseDTO data = new ScreenHttpFloorRoomDeviceResponseDTO();
            data.setFloor(k);
            List<TemplateRoomDO> tmpRooms = finalFloor_room_group.get(k);
            if (!CollectionUtils.isEmpty(tmpRooms)) {
                data.setRooms(tmpRooms.stream().map(r -> {
                    ScreenFamilyRoomDTO roomDTO = new ScreenFamilyRoomDTO();
                    roomDTO.setRoomType(r.getType());
                    roomDTO.setRoomName(r.getName());
                    List<TemplateDeviceDO> tmpDevices = finalRoom_device_map.get(r.getId());
                    if (!CollectionUtils.isEmpty(tmpDevices)) {
                        roomDTO.setDevices(tmpDevices.stream().map(d -> {
                            ScreenFamilyDeviceInfoDTO deviceInfoDTO = new ScreenFamilyDeviceInfoDTO();
                            deviceInfoDTO.setDeviceSn(d.getSn());
                            deviceInfoDTO.setDeviceName(d.getName());
                            // 设备属性
                            deviceInfoDTO.setProductCode(d.getProductCode());
                            deviceInfoDTO.setCategoryCode(d.getCategoryCode());
                            return deviceInfoDTO;
                        }).collect(Collectors.toList()));
                    }
                    return roomDTO;
                }).collect(Collectors.toList()));
            }
            result.add(data);
        });
        result.sort(Comparator.comparing(ScreenHttpFloorRoomDeviceResponseDTO::getFloor));
        return result;
    }


}
