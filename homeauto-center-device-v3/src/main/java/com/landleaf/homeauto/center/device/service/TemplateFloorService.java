package com.landleaf.homeauto.center.device.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.vo.DeviceInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.RoomDeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateRoomService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName TemplateFloorService
 * @Description: TODO
 * @Author wyl
 * @Date 2021/4/6
 * @Version V1.0
 **/
@Service
public class TemplateFloorService implements ITemplateFloorService {
    @Autowired
    private IHouseTemplateRoomService templateRoomService;
    @Autowired
    private IHouseTemplateDeviceService templateDeviceService;

    @Override
    public List<FloorRoomVO> getFloorAndRoomDevices(String templateId, Integer deviceShowApp) {
        List<FloorRoomVO> result = Lists.newArrayList();
        List<TemplateRoomDO> rooms = templateRoomService.getRoomsByTemplateId(Long.parseLong(templateId));
        List<TemplateDeviceDO> devices = templateDeviceService.listByTemplateId(Long.parseLong(templateId));
        Map<String, List<TemplateDeviceDO>> ROOM_MAP = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(devices)) {
            ROOM_MAP = devices.stream().collect(Collectors.groupingBy(i->{return String.valueOf(i.getId());}));
        }
        if (!CollectionUtils.isEmpty(rooms)) {
            Map<String, List<TemplateRoomDO>> FLOOR_MAP = rooms.stream().collect(Collectors.groupingBy(TemplateRoomDO::getFloor));
            Map<String, List<TemplateDeviceDO>> finalROOM_MAP = ROOM_MAP;
            FLOOR_MAP.forEach((k, v) -> {
                FloorRoomVO floorRoomVO = new FloorRoomVO();
                floorRoomVO.setFloor(k);
                floorRoomVO.setRooms(v.stream().map(r -> {
                    RoomDeviceVO roomDeviceVO = new RoomDeviceVO();
                    roomDeviceVO.setId(r.getId());
                    roomDeviceVO.setImgIcon(r.getImgIcon());
                    roomDeviceVO.setName(r.getName());
                    List<TemplateDeviceDO> templateDeviceDOS = finalROOM_MAP.get(r.getId());
                    if (!CollectionUtils.isEmpty(templateDeviceDOS)) {
                        roomDeviceVO.setDevices(templateDeviceDOS.stream().map(d -> {
                            DeviceInfoVO deviceInfoVO = new DeviceInfoVO();
                            deviceInfoVO.setId(String.valueOf(d.getId()));
                            deviceInfoVO.setIcon(d.getImageIcon());
                            return deviceInfoVO;
                        }).collect(Collectors.toList()));
                    }
                    return roomDeviceVO;
                }).collect(Collectors.toList()));
                result.add(floorRoomVO);
            });
        }
        return result;
    }

    /**
     * 根据户型获取楼层信息
     *
     * @param templateId 户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO>
     * @author wenyilu
     * @date 2021/1/5 17:17
     */
    @Override
    public List<TemplateFloorDO> getFloorByTemplateId(Long templateId) {
        List<TemplateFloorDO> result = null;
        List<TemplateRoomDO> rooms = templateRoomService.getRoomsByTemplateId(templateId);
        if (!CollectionUtils.isEmpty(rooms)) {
            result = Lists.newArrayList();
            Map<String, List<TemplateRoomDO>> FLOOR_MAP = rooms.stream().collect(Collectors.groupingBy(TemplateRoomDO::getFloor));
            List<TemplateFloorDO> finalResult = result;
            FLOOR_MAP.forEach((k, v) -> {
                TemplateFloorDO floorDO = new TemplateFloorDO();
                floorDO.setFloor(k);
                floorDO.setId(k);
                floorDO.setHouseTemplateId(BeanUtil.convertLong2String(templateId));
                floorDO.setName(k);
                finalResult.add(floorDO);
            });

        }
        return result;
    }
}
