package com.landleaf.homeauto.center.device.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.vo.DeviceInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.RoomDeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateRoomService;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
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
    @Autowired
    private IHomeAutoProductService productService;

    @Override
    public List<FloorRoomVO> getFloorAndRoomDevices(Long templateId) {
        List<FloorRoomVO> result = Lists.newArrayList();
        List<TemplateRoomDO> rooms = templateRoomService.getRoomsByTemplateId(templateId);
        List<TemplateDeviceDO> devices = templateDeviceService.listByTemplateId(templateId);

        Map<Long, List<TemplateDeviceDO>> ROOM_MAP = Maps.newHashMap();
        Map<Long, HomeAutoProduct> PRODUCT_MAP = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(devices)) {
            ROOM_MAP = devices.stream().filter(device -> !CategoryTypeEnum.HOST.getType().equals(device.getCategoryCode())).collect(Collectors.groupingBy(i -> {
                return i.getRoomId();
            }));
            //app和小程序过滤不展示主机
            PRODUCT_MAP = productService.listByIds(devices.stream().filter(device -> !CategoryTypeEnum.HOST.getType().equals(device.getCategoryCode()))
                    .map(TemplateDeviceDO::getProductId).collect(Collectors.toList())).stream().collect(Collectors.toMap(BaseEntity2::getId, p -> p, (o, n) -> n));
        }
        if (!CollectionUtils.isEmpty(rooms)) {
            Map<String, List<TemplateRoomDO>> FLOOR_MAP = rooms.stream().collect(Collectors.groupingBy(TemplateRoomDO::getFloor));
            Map<Long, List<TemplateDeviceDO>> finalROOM_MAP = ROOM_MAP;
            Map<Long, HomeAutoProduct> finalPRODUCT_MAP = PRODUCT_MAP;
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
                            deviceInfoVO.setId(d.getId());
                            deviceInfoVO.setName(d.getName());
                            HomeAutoProduct product = finalPRODUCT_MAP.get(d.getProductId());
                            if (product != null) {
                                deviceInfoVO.setIcon(product.getIcon2());
                            }
                            deviceInfoVO.setProductCode(d.getProductCode());
                            deviceInfoVO.setCategoryCode(d.getCategoryCode());
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
