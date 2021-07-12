package com.landleaf.homeauto.center.device.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.cache.DeviceCacheProvider;
import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenProjectBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilySceneActionConfig;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateSceneActionConfig;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;
import com.landleaf.homeauto.center.device.remote.WeatherRemote;
import com.landleaf.homeauto.center.device.service.bridge.IBridgeAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpFamilyBindDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.HomeAutoFaultDeviceCurrentDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneActionDTO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneDTO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.common.enums.FamilyFaultEnum;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private DeviceCacheProvider deviceCacheProvider;
    @Autowired
    private IBridgeAppService bridgeAppService;
    @Autowired
    private IHouseTemplateRoomService templateRoomService;
    @Autowired
    private IHouseTemplateDeviceService templateDeviceService;
    @Autowired
    private IHouseTemplateSceneService templateSceneService;
    @Autowired
    private ITemplateSceneActionConfigService sceneActionConfigService;
    @Autowired
    private IHomeAutoFaultDeviceCurrentService faultDeviceCurrentService;
    @Autowired
    private IFamilyDeviceInfoStatusService familyDeviceInfoStatusService;
    @Autowired
    private IProjectScreenUpgradeService projectScreenUpgradeService;

    @Autowired
    private IFamilySceneService iFamilySceneService;
    @Autowired
    private IFamilySceneActionConfigService iFamilySceneActionConfigService;


    @Override
    public ScreenHttpApkVersionCheckResponseDTO apkVersionCheck(AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO) {
        return projectScreenUpgradeService.apkVersionCheck(adapterHttpApkVersionCheckDTO);
    }

    @Override
    public ScreenHttpWeatherResponseDTO getWeather(Long familyId) {
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
    public ScreenHttpWeatherResponseDTO getCityWeather(String city) {
        ScreenHttpWeatherResponseDTO data = new ScreenHttpWeatherResponseDTO();
        Response<WeatherBO> response = weatherRemote.getWeatherByName4Screen(city);
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
            timingDO.setSceneId(i.getSceneId());
            timingDO.setType(i.getType());
            timingDO.setWeekday(i.getWeekday());
            if(!Objects.isNull(i.getTimingId())){
                timingDO.setId(i.getTimingId());
            }
            timingDO.setFamilyId(familyId);
            return timingDO;
        }).collect(Collectors.toList());

        Map<Long, ScreenHttpTimingSceneResponseDTO> timingSceneMap = Maps.newHashMap();
        List<ScreenHttpTimingSceneResponseDTO> existTimingScenes = getTimingSceneList(familyId);
        if (!CollectionUtils.isEmpty(existTimingScenes)) {
            timingSceneMap = existTimingScenes.stream().collect(Collectors.toMap(ScreenHttpTimingSceneResponseDTO::getTimingId, s -> s));
        }
        Map<Long, ScreenHttpTimingSceneResponseDTO> finalTimingSceneMap = timingSceneMap;
        List<FamilySceneTimingDO> saveData = timingDOList.stream().filter(i -> {
            Long timingId = i.getId();
            if (Objects.isNull(timingId)|| finalTimingSceneMap.get(timingId) == null) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<FamilySceneTimingDO> updateData = timingDOList.stream().filter(i -> {
            Long timingId = i.getId();
            if (Objects.isNull(timingId)|| finalTimingSceneMap.get(timingId) == null) {
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
    public List<SyncSceneInfoDTO> getSceneList(Long houseTemplateId) {
        List<SyncSceneInfoDTO> listSyncScene = Lists.newArrayList();
        //获取默认场景 户型下的
        List<SyncSceneInfoDTO> listTemplateScene = getListSceneTemplate(houseTemplateId);
        return listSyncScene;
    }

    private List<SyncSceneInfoDTO> getListSceneFamily(Long familyId) {
        List<SyncSceneInfoDTO> scenes = iFamilySceneService.getListSyncSceneByfId(familyId);
        return scenes;
    }

    /**
     * 户型下场景获取
     * @param houseTemplateId
     * @return
     */
    private List<SyncSceneInfoDTO> getListSceneTemplate(Long houseTemplateId) {
        List<SyncSceneInfoDTO> listSyncScene = Lists.newArrayList();
        List<HouseTemplateScene> scenes = templateSceneService.getScenesByTemplate(houseTemplateId);
        if(CollectionUtils.isEmpty(scenes)){
            return listSyncScene;
        }
        Map<Long,Map<String, List<TemplateSceneActionConfig>>> ACTION_MAP = Maps.newHashMap();
        List<TemplateSceneActionConfig> actions = sceneActionConfigService.getActionsByTemplateId(houseTemplateId);
        if(!CollectionUtils.isEmpty(actions)){
            actions.stream().collect(Collectors.groupingBy(TemplateSceneActionConfig::getSceneId)).forEach((k,v)->{
                ACTION_MAP.put(k,v.stream().collect(Collectors.groupingBy(TemplateSceneActionConfig::getDeviceSn)));
            });
        }
        listSyncScene.addAll(scenes.stream().map(s -> {
            SyncSceneInfoDTO dto = new SyncSceneInfoDTO();
            dto.setSceneId(s.getId());
            dto.setSceneName(s.getName());
            List<SyncSceneDTO> tmpActions = Lists.newArrayList();
            Map<String, List<TemplateSceneActionConfig>> deviceMap = ACTION_MAP.get(s.getId());
            if (deviceMap != null && deviceMap.size() > 0) {
                deviceMap.forEach((d, v) -> {
                    SyncSceneDTO sceneDTO = new SyncSceneDTO();
                    sceneDTO.setDeviceSn(Integer.parseInt(d));
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
    @Override
    public List<ScreenProductAttrCategoryBO> getDeviceAttrsByProductCode(String productCode,Integer systemFlag) {
        return configCacheProvider.getDeviceAttrsByProductCode(productCode,systemFlag);
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
        adapterConfigUpdateDTO.buildBaseInfo(familyId, familyInfo.getCode(),
                familyInfo.getTemplateId(), familyInfo.getScreenMac(),
                System.currentTimeMillis());
        adapterConfigUpdateDTO.setUpdateType(typeEnum.code);
        bridgeAppService.configUpdateConfig(adapterConfigUpdateDTO);
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
    public List<ScreenSysProductAttrBO> getSysDeviceFunctionAttrsByProductCode(String productCode) {
        List<ScreenProductAttrCategoryBO> deviceAttrs = getDeviceAttrsByProductCode(productCode, FamilySystemFlagEnum.SYS_DEVICE.getType());
        return deviceAttrs.stream().filter(i -> {
            return i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType();
        }).collect(Collectors.toList()).stream()
                .map(i -> i.getSysAttrBO()).collect(Collectors.toList()).stream().collect(Collectors.toList());
    }


    @Override
    public void bindFamily(AdapterHttpFamilyBindDTO adapterHttpFamilyBindDTO) {
        ScreenProjectBO project = configCacheProvider.getProject(adapterHttpFamilyBindDTO.getProjectCode());
        homeAutoFamilyService.bindMac(project.getProjectId(),adapterHttpFamilyBindDTO.getBuildingCode(),
                adapterHttpFamilyBindDTO.getUnitCode(),adapterHttpFamilyBindDTO.getDoorplate(),
                adapterHttpFamilyBindDTO.getTerminalMac());
    }

    @Override
    public ScreenDeviceInfoStatusDTO getFamilyDeviceInfoStatus(Long familyId, Long deviceId) {

        return deviceCacheProvider.getFamilyDeviceInfoStatus(familyId,deviceId);
    }

    @Override
    public void delFamilyDeviceInfoStatusCache(Long familyId) {
        deviceCacheProvider.delFamilyDeviceInfoStatusCache(familyId);
    }

    @Override
    public void storeOrUpdateCurrentFaultValue(HomeAutoFaultDeviceCurrentDTO param) {
        String key = String.format(RedisCacheConst.FAMILY_DEVICE_INFO_STATUS_CACHE,param.getFamilyId(),param.getDeviceId());
        if(redisUtils.hasKey(key)){
            redisUtils.del(key);
        }
        HomeAutoFaultDeviceCurrent data = new HomeAutoFaultDeviceCurrent();
        BeanUtils.copyProperties(param,data);
       faultDeviceCurrentService.storeOrUpdateCurrentFaultValue(data);
    }
    @Override
    public void storeOrUpdateDeviceInfoStatus(ScreenDeviceInfoStatusUpdateDTO param) {
        String key = String.format(RedisCacheConst.FAMILY_DEVICE_INFO_STATUS_CACHE,param.getFamilyId(),param.getDeviceId());
        if(redisUtils.hasKey(key)){
            redisUtils.del(key);
        }
        FamilyDeviceInfoStatus familyDeviceInfoStatus = new FamilyDeviceInfoStatus();
        BeanUtils.copyProperties(param,familyDeviceInfoStatus);
        familyDeviceInfoStatusService.storeOrUpdateDeviceInfoStatus(familyDeviceInfoStatus,param.getType());
    }

    @Override
    public void removeCurrentFaultValue(Long familyId, Long deviceId, String code, int type) {
        faultDeviceCurrentService.removeCurrentFaultValue(familyId,deviceId,code,type);
    }

    @Override
    public long countCurrentFault(Long familyId, Long deviceId, int type) {
        return faultDeviceCurrentService.countCurrentFault(familyId,deviceId,type);
    }


    @Override
    public List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(Long templateId,Long familyId) {
       return configCacheProvider.getFloorRoomDeviceList(templateId,familyId);
    }




}
