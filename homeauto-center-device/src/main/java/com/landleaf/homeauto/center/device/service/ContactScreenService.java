package com.landleaf.homeauto.center.device.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.remote.WeatherRemote;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyDeviceInfoDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyRoomDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
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
    private IFamilyFloorService familyFloorService;
    @Autowired
    private IFamilyRoomService familyRoomService;
    @Autowired
    private IFamilyDeviceService familyDeviceService;
    @Autowired
    private IHomeAutoProductService homeAutoProductService;
    @Autowired
    private WeatherRemote weatherRemote;
    @Autowired
    private IFamilySceneTimingService familySceneTimingService;

    @Autowired
    private IFamilyTerminalOnlineStatusService familyTerminalOnlineStatusService;

    @Autowired
    private IMsgNoticeService msgNoticeService;

    @Autowired
    private IFamilySceneService familySceneService;
    @Autowired
    private IVacationSettingService vacationSettingService;

    @Override
    public ScreenHttpApkVersionCheckResponseDTO apkVersionCheck(AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO) {
        return homeAutoScreenApkUpdateDetailService.apkVersionCheck(adapterHttpApkVersionCheckDTO);
    }

    @Override
    public List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(String familyId) {

        List<FamilyFloorDO> floors = familyFloorService.getFloorByFamilyId(familyId);
        List<FamilyRoomDO> rooms = familyRoomService.getRoom(familyId);
        List<FamilyDeviceDO> devices = familyDeviceService.getDevicesByFamilyId(familyId);


        Map<String, List<FamilyRoomDO>> floor_room_group = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(rooms)) {
            floor_room_group = rooms.stream().collect(Collectors.groupingBy(FamilyRoomDO::getFloorId));
        }
        Map<String, List<FamilyDeviceDO>> room_device_map = Maps.newHashMap();
        Map<String, HomeAutoProduct> productMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(devices)) {
            room_device_map = devices.stream().collect(Collectors.groupingBy(FamilyDeviceDO::getRoomId));
            List<String> productIds = devices.stream().map(d -> {
                return d.getProductId();
            }).collect(Collectors.toList());
            Collection<HomeAutoProduct> homeAutoProducts = homeAutoProductService.listByIds(productIds);
            productMap = homeAutoProducts.stream().collect(Collectors.toMap(HomeAutoProduct::getId, p -> p));
        }
        if (!CollectionUtils.isEmpty(floors)) {
            Map<String, List<FamilyRoomDO>> finalFloor_room_group = floor_room_group;
            Map<String, List<FamilyDeviceDO>> finalRoom_device_map = room_device_map;
            Map<String, HomeAutoProduct> finalProductMap = productMap;
            List<ScreenHttpFloorRoomDeviceResponseDTO> result = floors.stream().map(f -> {
                ScreenHttpFloorRoomDeviceResponseDTO data = new ScreenHttpFloorRoomDeviceResponseDTO();
                data.setFloor(f.getFloor());
                data.setName(f.getName());
                List<FamilyRoomDO> tmpRooms = finalFloor_room_group.get(f.getId());
                if (!CollectionUtils.isEmpty(tmpRooms)) {
                    data.setRooms(tmpRooms.stream().map(r -> {
                        ScreenFamilyRoomDTO roomDTO = new ScreenFamilyRoomDTO();
                        roomDTO.setRoomType(r.getType());
                        roomDTO.setRoomName(r.getName());
                        roomDTO.setSortNo(r.getSortNo());
                        List<FamilyDeviceDO> tmpDevices = finalRoom_device_map.get(r.getId());
                        if (!CollectionUtils.isEmpty(tmpDevices)) {
                            roomDTO.setDevices(tmpDevices.stream().map(d -> {
                                ScreenFamilyDeviceInfoDTO deviceInfoDTO = new ScreenFamilyDeviceInfoDTO();
                                deviceInfoDTO.setDeviceSn(d.getSn());
                                deviceInfoDTO.setDeviceName(d.getName());
                                deviceInfoDTO.setPort(d.getPort());
                                deviceInfoDTO.setSortNo(d.getSortNo());

                                // 设备属性
                                HomeAutoProduct homeAutoProduct = finalProductMap.get(d.getProductId());
                                deviceInfoDTO.setProtocol(homeAutoProduct == null ? null : homeAutoProduct.getProtocol());
                                deviceInfoDTO.setProductCode(homeAutoProduct == null ? null : homeAutoProduct.getCode());
                                return deviceInfoDTO;
                            }).collect(Collectors.toList()));
                        }
                        return roomDTO;
                    }).collect(Collectors.toList()));
                }
                return data;
            }).collect(Collectors.toList());
            return result;
        }
        return Lists.newArrayList();
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

        familyTerminalOnlineStatusService.updateTerminalOnLineStatus(familyId, terminalMac, status);


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
        List<SyncSceneInfoDTO> listSyncScene = familySceneService.getListSyncScene(familyId);
        return listSyncScene;
    }

    @Override
    public ScreenHttpHolidaysCheckResponseDTO holidayCheck(String date) {
        ScreenHttpHolidaysCheckResponseDTO data = new ScreenHttpHolidaysCheckResponseDTO();

        Integer someDayType = vacationSettingService.getSomeDayType(date);

        data.setResult((someDayType != null && someDayType.intValue() == 1) ? true : false);
        return data;
    }
}
