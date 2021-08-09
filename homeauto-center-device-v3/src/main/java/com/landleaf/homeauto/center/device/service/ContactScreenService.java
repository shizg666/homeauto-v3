package com.landleaf.homeauto.center.device.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.cache.DeviceCacheProvider;
import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.filter.sys.SysProductRelatedFilter;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenProjectBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrValueBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrValueBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.*;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfoScope;
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
import com.landleaf.homeauto.common.domain.dto.screen.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpProjectHouseTypeDTO;
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

import java.util.*;
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

    @Autowired
    private IProjectHouseTemplateService projectHouseTemplateService;

    @Autowired
    private SysProductRelatedFilter sysProductRelatedFilter;

    @Autowired
    private IHomeAutoProductService productService;


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
    public List<SyncSceneInfoDTO> getSceneList(Long houseTemplateId, Long familyId) {
        List<SyncSceneInfoDTO> listSyncScene = Lists.newArrayList();
        //获取默认场景 户型下的
        List<SyncSceneInfoDTO> listTemplateScene = getListSceneTemplate(houseTemplateId);
        List<SyncSceneInfoDTO> listSceneFamily = getListSceneFamily(familyId);
        listSyncScene.addAll(listTemplateScene);
        listSyncScene.addAll(listSceneFamily);
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
                Object hget = redisUtils.hget(RedisCacheConst.CONTACT_SCREEN_MQTT_CLIENT_STATUS, screen_mac);
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
    public List<ScreenFamilyModelResponseDTO> getProjectTemplates(Long projectId) {
        //获取模板
        List<ProjectHouseTemplate> templates = projectHouseTemplateService.list(new LambdaQueryWrapper<ProjectHouseTemplate>().eq(ProjectHouseTemplate::getProjectId, projectId));
        Map<Long, ProjectHouseTemplate> templateMap = templates.stream().collect(Collectors.toMap(ProjectHouseTemplate::getId, t -> t));
        //查询所有家庭
        List<HomeAutoFamilyDO> homeAutoFamilyDOList = homeAutoFamilyService.list(new LambdaQueryWrapper<HomeAutoFamilyDO>().eq(HomeAutoFamilyDO::getProjectId, projectId));
        return homeAutoFamilyDOList.stream().map(f -> {
            ProjectHouseTemplate template = templateMap.get(f.getTemplateId());
            if (Objects.isNull(template)) {
                return null;
            }
            return new ScreenFamilyModelResponseDTO(
                    String.valueOf(projectId),
                    template.getName(),
                    Integer.valueOf(f.getBuildingCode()),
                    Integer.valueOf(f.getUnitCode()),
                    f.getRoomNo(),
                    Integer.valueOf(f.getFloor()));
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public ScreenHttpFloorRoomDeviceSceneResponseDTO getTemplateConfig(ScreenHttpProjectHouseTypeDTO dto) {
        //获取模板id
        Long templateId = null;
        List<SyncSceneInfoDTO> scenes = getListSceneTemplate(templateId);
        List<ScreenHttpFloorRoomDeviceResponseDTO> floors =  getTemplateFloorRoomDeviceList(templateId);
        return new ScreenHttpFloorRoomDeviceSceneResponseDTO(floors, scenes);
    }


    public List<ScreenHttpFloorRoomDeviceResponseDTO> getTemplateFloorRoomDeviceList(Long templateId) {

        List<ScreenHttpFloorRoomDeviceResponseDTO> result = Lists.newArrayList();
        List<TemplateRoomDO> rooms = templateRoomService.getRoomsByTemplateId(templateId);
        List<TemplateDeviceDO> devices = templateDeviceService.listByTemplateId(templateId);

        Map<Long, String> familyRoomName = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(rooms)) {
            familyRoomName = rooms.stream().collect(Collectors.toMap(TemplateRoomDO::getId,TemplateRoomDO::getName));
        }

        Map<String, List<TemplateRoomDO>> floor_room_group = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(rooms)) {
            floor_room_group = rooms.stream().collect(Collectors.groupingBy(TemplateRoomDO::getFloor));
        }
        Map<Long, List<TemplateDeviceDO>> room_device_map = Maps.newHashMap();
        TemplateDeviceDO systemDevice=null;
        if (!CollectionUtils.isEmpty(devices)) {
            room_device_map = devices.stream().collect(Collectors.groupingBy(i -> {
                return i.getRoomId();
            }));
            Optional<TemplateDeviceDO> first = devices.stream().filter(i -> i.getSystemFlag()!=null&&i.getSystemFlag() == FamilySystemFlagEnum.SYS_DEVICE.getType()).findFirst();
            if(first.isPresent()){
                systemDevice =first.get();
            }
        }
        Map<String, List<TemplateRoomDO>> finalFloor_room_group = floor_room_group;
        Map<Long, List<TemplateDeviceDO>> finalRoom_device_map = room_device_map;

        TemplateDeviceDO finalSystemDevice = systemDevice;
        Map<Long, String> finalFamilyRoomName = familyRoomName;
        floor_room_group.forEach((k, v) -> {
            ScreenHttpFloorRoomDeviceResponseDTO data = new ScreenHttpFloorRoomDeviceResponseDTO();
            data.setFloor(k);
            List<ScreenFamilyRoomDTO> roomData = buildRoomData(k, finalFloor_room_group, finalRoom_device_map, finalSystemDevice, finalFamilyRoomName);
            roomData.sort(Comparator.comparing(ScreenFamilyRoomDTO::getCreateTime));
            data.setRooms(roomData);
            result.add(data);
        });
        result.sort(Comparator.comparing(ScreenHttpFloorRoomDeviceResponseDTO::getFloor));
        return result;
    }

    private List<ScreenFamilyRoomDTO> buildRoomData(String floor, Map<String, List<TemplateRoomDO>> finalFloor_room_group, Map<Long, List<TemplateDeviceDO>> finalRoom_device_map, TemplateDeviceDO systemDevice,Map<Long, String> familyRoomName) {
        List<TemplateRoomDO> tmpRooms = finalFloor_room_group.get(floor);
        if (CollectionUtils.isEmpty(tmpRooms)) {
            return Lists.newArrayList();
        }
        return tmpRooms.stream().map(r -> {
            ScreenFamilyRoomDTO roomDTO = new ScreenFamilyRoomDTO();
            roomDTO.setRoomType(r.getType());
            roomDTO.setRoomName(familyRoomName.get(r.getId()));
            roomDTO.setCreateTime(r.getCreateTime());
            List<ScreenFamilyDeviceInfoDTO> deviceData = buildDeviceData(r, finalRoom_device_map,systemDevice);

            roomDTO.setDevices(deviceData);
            return roomDTO;
        }).collect(Collectors.toList());
    }

    private List<ScreenFamilyDeviceInfoDTO> buildDeviceData(TemplateRoomDO roomDO, Map<Long, List<TemplateDeviceDO>> finalRoom_device_map, TemplateDeviceDO systemDevice) {
        List<TemplateDeviceDO> tmpDevices = finalRoom_device_map.get(roomDO.getId());
        if (CollectionUtils.isEmpty(tmpDevices)) {
            return Lists.newArrayList();
        }
        List<HomeAutoProduct> products = productService.getAllProducts();
        Map<String, List<HomeAutoProduct>> product_map = products.stream().collect(Collectors.groupingBy(HomeAutoProduct::getCode));

        return tmpDevices.stream().map(d -> {
            ScreenFamilyDeviceInfoDTO deviceInfoDTO = new ScreenFamilyDeviceInfoDTO();
            deviceInfoDTO.setDeviceId(d.getId());
            deviceInfoDTO.setDeviceSn(Integer.parseInt(d.getSn()));
            deviceInfoDTO.setDeviceName(d.getName());
            // 设备属性
            deviceInfoDTO.setProductCode(d.getProductCode());
            deviceInfoDTO.setCategoryCode(Integer.parseInt(d.getCategoryCode()));
            deviceInfoDTO.setSystemFlag(d.getSystemFlag());
            ScreenFamilyDeviceInfoProtocolDTO deviceProtocol = new ScreenFamilyDeviceInfoProtocolDTO();
            deviceProtocol.setAddressCode(d.getAddressCode());
            if(d.getSystemFlag()!=FamilySystemFlagEnum.SYS_DEVICE.getType()){
                HomeAutoProduct product = product_map.get(d.getProductCode()).get(0);
                deviceProtocol.setProtocol(product.getProtocol());
            }
            if(d.getSystemFlag()!=null&&d.getSystemFlag()==FamilySystemFlagEnum.SYS_SUB_DEVICE.getType()&&systemDevice!=null){
                deviceInfoDTO.setRelatedDeviceSn(Integer.parseInt(systemDevice.getSn()));
            }
            deviceInfoDTO.setDeviceProtocol(deviceProtocol);
            buildAttrs(deviceInfoDTO, d.getHouseTemplateId());
            return deviceInfoDTO;
        }).collect(Collectors.toList());

    }

    private void buildAttrs(ScreenFamilyDeviceInfoDTO deviceInfoDTO, Long houseTemplateId) {
        List<ContactScreenFamilyDeviceAttrInfoDTO> attrs = Lists.newArrayList();
        Integer systemFlag = deviceInfoDTO.getSystemFlag();
        List<ScreenProductAttrCategoryBO> deviceAttrs = configCacheProvider.getDeviceAttrsByProductCode(deviceInfoDTO.getProductCode(), systemFlag);
        List<ScreenProductAttrCategoryBO> filterErrorAttrs = deviceAttrs.stream().filter(i -> i.getFunctionType() != AttrFunctionEnum.ERROR_ATTR.getType()).collect(Collectors.toList());
        if (systemFlag == null || systemFlag.intValue() != FamilySystemFlagEnum.SYS_DEVICE.getType()) {
            List<ScreenProductAttrBO> nonSysAttrs = filterErrorAttrs.stream().map(i -> i.getAttrBO()).collect(Collectors.toList());

            //非系统设备--系统子设备+普通设备
            attrs= nonSysAttrs.stream().map(i -> {
                ContactScreenFamilyDeviceAttrInfoDTO attrInfoDTO =buildAttrInfo(i,houseTemplateId,systemFlag,deviceInfoDTO.getDeviceSn(),1);
                return attrInfoDTO;
            }).collect(Collectors.toList());

        } else {
            // 系统属性
            List<ScreenSysProductAttrBO> sysAttrs = filterErrorAttrs.stream().map(i -> i.getSysAttrBO()).collect(Collectors.toList());
            attrs= sysAttrs.stream().map(i -> {
                ContactScreenFamilyDeviceAttrInfoDTO attrInfoDTO =buildAttrInfo(i,houseTemplateId,systemFlag,deviceInfoDTO.getDeviceSn(),2);
                return attrInfoDTO;
            }).collect(Collectors.toList());

        }
        deviceInfoDTO.setAttrs(attrs);
    }
    private ContactScreenFamilyDeviceAttrInfoDTO buildAttrInfo(Object data, Long houseTemplateId, int systemFlag, Integer deviceSn, int type) {
        String attrCode = null;
        Integer attrValueType = null;
        List<ContactScreenDeviceAttributeInfoDTO> selects = Lists.newArrayList();
        ContactScreenDeviceAttributeInfoScopeDTO scopeDTO = null;
        if(type==1){
            ScreenProductAttrBO productAttrBO = (ScreenProductAttrBO) data;
            attrCode= productAttrBO.getAttrCode();
            ScreenProductAttrValueBO attrValue = productAttrBO.getAttrValue();
            attrValueType=attrValue.getType();
            List<ProductAttributeInfoDO> selectValues = attrValue.getSelectValues();
            ProductAttributeInfoScope numValue = attrValue.getNumValue();
            if (!CollectionUtils.isEmpty(selectValues)) {
                selects= selectValues.stream().map(j -> {
                    ContactScreenDeviceAttributeInfoDTO attributeInfoDTO = new ContactScreenDeviceAttributeInfoDTO();
                    BeanUtils.copyProperties(j, attributeInfoDTO);
                    return attributeInfoDTO;
                }).collect(Collectors.toList());
            }
            if (!Objects.isNull(numValue)) {
                scopeDTO=new ContactScreenDeviceAttributeInfoScopeDTO();
                BeanUtils.copyProperties(numValue, scopeDTO);
            }
        }else {
            ScreenSysProductAttrBO productAttrBO = (ScreenSysProductAttrBO) data;
            attrCode= productAttrBO.getAttrCode();
            ScreenSysProductAttrValueBO attrValue = productAttrBO.getAttrValue();
            attrValueType=attrValue.getType();
            List<SysProductAttributeInfo> selectValues = attrValue.getSelectValues();
            SysProductAttributeInfoScope numValue = attrValue.getNumValue();
            if (!CollectionUtils.isEmpty(selectValues)) {
                selects= selectValues.stream().map(j -> {
                    ContactScreenDeviceAttributeInfoDTO attributeInfoDTO = new ContactScreenDeviceAttributeInfoDTO();
                    BeanUtils.copyProperties(productAttrBO, attributeInfoDTO);
                    return attributeInfoDTO;
                }).collect(Collectors.toList());
            }
            if (!Objects.isNull(numValue)) {
                scopeDTO=new ContactScreenDeviceAttributeInfoScopeDTO();
                BeanUtils.copyProperties(numValue, scopeDTO);
            }
        }
        ContactScreenFamilyDeviceAttrInfoDTO attrInfoDTO = new ContactScreenFamilyDeviceAttrInfoDTO();
        attrInfoDTO.setAttrCode(attrCode);
        attrInfoDTO.setAttrConstraint(sysProductRelatedFilter.checkAttrConstraint(houseTemplateId, attrCode, systemFlag,String.valueOf(deviceSn)));
        attrInfoDTO.setAttrValueType(attrValueType);
        attrInfoDTO.setSelectValues(selects);
        attrInfoDTO.setNumValue(scopeDTO);
        return attrInfoDTO;

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
