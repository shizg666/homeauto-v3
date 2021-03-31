package com.landleaf.homeauto.center.device.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.remote.WeatherRemote;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.common.mqtt.MqttClientInfo;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private IHomeAutoScreenApkUpdateDetailService homeAutoScreenApkUpdateDetailService;
    @Autowired
    private IHomeAutoFamilyService homeAutoFamilyService;
    @Autowired
    private WeatherRemote weatherRemote;
    @Autowired
    private IFamilySceneTimingService familySceneTimingService;

    private IMsgNoticeService msgNoticeService;

    @Autowired
    private IVacationSettingService vacationSettingService;

    @Autowired
    private IHouseTemplateSceneService iHouseTemplateSceneService;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public ScreenHttpApkVersionCheckResponseDTO apkVersionCheck(AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO) {
        return homeAutoScreenApkUpdateDetailService.apkVersionCheck(adapterHttpApkVersionCheckDTO);
    }


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
    public List<ScreenHttpTimingSceneResponseDTO> getTimingSceneList(String familyId) {
        List<ScreenHttpTimingSceneResponseDTO> result = Lists.newArrayList();

        List<FamilySceneTimingBO> sceneTimingBOS = familySceneTimingService.getTimingScenesByFamilyId(familyId);
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
                HouseTemplateScene sceneDO = iHouseTemplateSceneService.getById(s.getSceneId());
                data.setSceneNo(StringUtils.isEmpty(sceneDO.getSceneNo()) ? s.getSceneId() : sceneDO.getSceneNo());
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
    public List<ScreenHttpTimingSceneResponseDTO> deleteTimingScene(List<String> timingIds, String familyId) {
        familySceneTimingService.deleteTimingScene(timingIds, familyId);
        return getTimingSceneList(familyId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ScreenHttpTimingSceneResponseDTO> saveOrUpdateTimingScene(List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos, String familyId) {
        List<ScreenHttpTimingSceneResponseDTO> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(dtos) || StringUtils.isEmpty(familyId)) {
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
            timingDO.setSceneId(i.getSceneId());
            timingDO.setType(i.getType());
            timingDO.setWeekday(i.getWeekday());
            timingDO.setId(i.getTimingId());
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
            String timingId = i.getId();
            if (StringUtils.isEmpty(timingId) || finalTimingSceneMap.get(timingId) == null) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<FamilySceneTimingDO> updateData = timingDOList.stream().filter(i -> {
            String timingId = i.getId();
            if (StringUtils.isEmpty(timingId) || finalTimingSceneMap.get(timingId) == null) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
        familySceneTimingService.saveBatch(saveData);
        familySceneTimingService.updateBatchById(updateData);

        return getTimingSceneList(familyId);
    }

    @Override
    public void updateTerminalOnLineStatus(String familyId, String terminalMac, Integer status) {

    }

    @Override
    public List<ScreenHttpNewsResponseDTO> getNews(String familyId) {
        List<ScreenHttpNewsResponseDTO> result = Lists.newArrayList();
        HomeAutoFamilyDO familyDO = homeAutoFamilyService.getById(familyId);
        if (familyDO == null) {
            return result;
        }
        List<MsgNoticeDO> msgNoticeDOS = msgNoticeService.queryMsgNoticeByProjectIdForScreen(familyDO.getProjectId());
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
    public List<SyncSceneInfoDTO> getSceneList(String familyId) {
        List<SyncSceneInfoDTO> listSyncScene = Lists.newArrayList();
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

        for (String screen_mac:macList) {

            if (redisUtils.hasKey(RedisCacheConst.CONTACT_SCREEN_MQTT_CLIENT_STATUS)) {
                Object hget = redisUtils.hgetEx(RedisCacheConst.CONTACT_SCREEN_MQTT_CLIENT_STATUS, screen_mac);
                if (hget != null) {
                    String mqtt_info = (String) hget;
                    MqttClientInfo mqttClientInfo = JSON.parseObject(mqtt_info,MqttClientInfo.class);
                    if ( (mqttClientInfo)!=null &&
                    mqttClientInfo.isConnected() &&
                    mqttClientInfo.getProto_name().equals("MQTT")) {
                        count=count+1;
                    }
                }
            }

        }
        return count;
    }
}
