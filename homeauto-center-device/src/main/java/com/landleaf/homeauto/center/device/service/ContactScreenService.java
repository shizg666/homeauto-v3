package com.landleaf.homeauto.center.device.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.bo.WeatherBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.remote.WeatherRemote;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyDeviceInfoDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyRoomDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpApkVersionCheckResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpWeatherResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
