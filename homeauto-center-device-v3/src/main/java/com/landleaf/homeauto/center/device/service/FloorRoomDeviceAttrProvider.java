package com.landleaf.homeauto.center.device.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.filter.sys.SysProductRelatedFilter;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrValueBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrValueBO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoDO;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateRoomService;
import com.landleaf.homeauto.common.domain.dto.screen.*;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @className: FloorRoomDeviceAttrProvider
 * @description: 楼层房间获取提供都
 * @author: wenyilu
 * @date: 2021/6/3
 **/
@Component
public class FloorRoomDeviceAttrProvider {
    @Autowired
    private SysProductRelatedFilter sysProductRelatedFilter;
    @Autowired
    private IHouseTemplateRoomService templateRoomService;
    @Autowired
    private IHouseTemplateDeviceService templateDeviceService;
    @Autowired
    private IHomeAutoProductService productService;
    @Autowired
    @Lazy
    private ConfigCacheProvider configCacheProvider;

    /**
     * @param: templateId
     * @description: 获取楼层房间设备缓存
     * @return: java.util.List<com.landleaf.homeauto.common.domain.dto.screen.http.response.ScreenHttpFloorRoomDeviceResponseDTO>
     * @author: wyl
     * @date: 2021/6/3
     */
    public List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(String templateId) {

        List<ScreenHttpFloorRoomDeviceResponseDTO> result = Lists.newArrayList();
        List<TemplateRoomDO> rooms = templateRoomService.getRoomsByTemplateId(BeanUtil.convertString2Long(templateId));
        List<TemplateDeviceDO> devices = templateDeviceService.listByTemplateId(BeanUtil.convertString2Long(templateId));


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
            Optional<TemplateDeviceDO> first = devices.stream().filter(i -> i.getSystemFlag() == FamilySystemFlagEnum.SYS_DEVICE.getType()).findFirst();
            if(first.isPresent()){
                systemDevice =first.get();
            }
        }
        Map<String, List<TemplateRoomDO>> finalFloor_room_group = floor_room_group;
        Map<Long, List<TemplateDeviceDO>> finalRoom_device_map = room_device_map;

        TemplateDeviceDO finalSystemDevice = systemDevice;
        floor_room_group.forEach((k, v) -> {
            ScreenHttpFloorRoomDeviceResponseDTO data = new ScreenHttpFloorRoomDeviceResponseDTO();
            data.setFloor(k);
            List<ScreenFamilyRoomDTO> roomData = buildRoomData(k, finalFloor_room_group, finalRoom_device_map, finalSystemDevice);
            data.setRooms(roomData);
            result.add(data);
        });
        result.sort(Comparator.comparing(ScreenHttpFloorRoomDeviceResponseDTO::getFloor));
        return result;
    }



    private List<ScreenFamilyRoomDTO> buildRoomData(String floor, Map<String, List<TemplateRoomDO>> finalFloor_room_group, Map<Long, List<TemplateDeviceDO>> finalRoom_device_map, TemplateDeviceDO systemDevice) {
        List<TemplateRoomDO> tmpRooms = finalFloor_room_group.get(floor);
        if (CollectionUtils.isEmpty(tmpRooms)) {
            return Lists.newArrayList();
        }
        return tmpRooms.stream().map(r -> {
            ScreenFamilyRoomDTO roomDTO = new ScreenFamilyRoomDTO();
            roomDTO.setRoomType(r.getType());
            roomDTO.setRoomName(r.getName());
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
            deviceInfoDTO.setDeviceSn(d.getSn());
            deviceInfoDTO.setDeviceName(d.getName());
            // 设备属性
            deviceInfoDTO.setProductCode(d.getProductCode());
            deviceInfoDTO.setCategoryCode(d.getCategoryCode());
            deviceInfoDTO.setSystemFlag(d.getSystemFlag());
            ScreenFamilyDeviceInfoProtocolDTO deviceProtocol = new ScreenFamilyDeviceInfoProtocolDTO();
            deviceProtocol.setAddressCode(d.getAddressCode());
            if(d.getSystemFlag()!=FamilySystemFlagEnum.SYS_DEVICE.getType()){
                HomeAutoProduct product = product_map.get(d.getProductCode()).get(0);
                deviceProtocol.setProtocol(product.getProtocol());
            }
            if(d.getSystemFlag()!=null&&d.getSystemFlag()==FamilySystemFlagEnum.SYS_SUB_DEVICE.getType()&&systemDevice!=null){
                deviceInfoDTO.setRelatedDeviceSn(systemDevice.getSn());
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

    private ContactScreenFamilyDeviceAttrInfoDTO buildAttrInfo(Object data, Long houseTemplateId, int systemFlag, String deviceSn, int type) {
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
        attrInfoDTO.setAttrConstraint(sysProductRelatedFilter.checkAttrConstraint(houseTemplateId, attrCode, systemFlag,deviceSn));
        attrInfoDTO.setAttrValueType(attrValueType);
        attrInfoDTO.setSelectValues(selects);
        attrInfoDTO.setNumValue(scopeDTO);
        return attrInfoDTO;

    }


}
