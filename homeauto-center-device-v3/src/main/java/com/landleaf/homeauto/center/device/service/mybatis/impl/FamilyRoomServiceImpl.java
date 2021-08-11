package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.config.ImagePathConfig;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.*;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateRoomPageVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.RedisKeyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 户型房间表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Service
public class FamilyRoomServiceImpl extends ServiceImpl<FamilyRoomMapper, FamilyRoomDO> implements IFamilyRoomService {

    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;

    @Override
    public List<FamilyRoomDO> getListRooms(Long familyId) {
         List<FamilyRoomDO> rooms = list(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFamilyId,familyId).select(FamilyRoomDO::getId,FamilyRoomDO::getName,FamilyRoomDO::getTemplateRoomId));
        return rooms;
    }

    @Override
    public void addTemplateRoomByTid(Long templateId, Long familyId) {
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.getRoomsByTemplateId(templateId);
        if (CollectionUtils.isEmpty(roomDOS)){
            return;
        }
        List<FamilyRoomDO> roomDOList = roomDOS.stream().map(room->{
            FamilyRoomDO familyRoomDO = new FamilyRoomDO();
            familyRoomDO.setName(room.getName());
            familyRoomDO.setFamilyId(familyId);
            familyRoomDO.setTemplateRoomId(room.getId());
            return familyRoomDO;
        }).collect(Collectors.toList());
        saveBatch(roomDOList);
    }

    @Override
    public void addRoomOnImportFamily(Map<Long, Long> familyTempalte) {
        if(CollectionUtils.isEmpty(familyTempalte)){
            return;
        }
        List<Long> templateIds = familyTempalte.values().stream().distinct().collect(Collectors.toList());
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().in(TemplateRoomDO::getHouseTemplateId,templateIds).select(TemplateRoomDO::getId,TemplateRoomDO::getName,TemplateRoomDO::getFloor,TemplateRoomDO::getHouseTemplateId));
        Map<Long,List<TemplateRoomDO>> roomMap = roomDOS.stream().collect(Collectors.groupingBy(TemplateRoomDO::getHouseTemplateId));
        List<FamilyRoomDO> roomDOList = Lists.newArrayList();
        familyTempalte.forEach((familyId,templateId)->{
            List<TemplateRoomDO> templateRoomDOS = roomMap.get(templateId);
            if (!CollectionUtils.isEmpty(templateRoomDOS)){
                List<FamilyRoomDO> data = templateRoomDOS.stream().map(room->{
                    FamilyRoomDO familyRoomDO = new FamilyRoomDO();
                    familyRoomDO.setName(room.getName());
                    familyRoomDO.setFamilyId(familyId);
                    familyRoomDO.setTemplateRoomId(room.getId());
                    return familyRoomDO;
                }).collect(Collectors.toList());
                roomDOList.addAll(data);
            }
        });
        saveBatch(roomDOList);
    }

    @Override
    public void removeByTemplateRoomId(Long tRoomId) {
        remove(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getTemplateRoomId,tRoomId));
    }

    @Override
    public JZSceneConfigDataVO getRoomDeviceAttr(Long familyId,Long templateId) {
        JZSceneConfigDataVO result = new JZSceneConfigDataVO();
        List<FamilyRoomDeviceAttrBO> roomDeviceAttrBOS = this.baseMapper.getRoomDeviceAttr(familyId,templateId);
        if (CollectionUtils.isEmpty(roomDeviceAttrBOS)) {
            return result;
        }

        List<Long> productIds = roomDeviceAttrBOS.stream().map(FamilyRoomDeviceAttrBO::getProductId).collect(Collectors.toList());
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceControlAttributeInfo(Lists.newArrayList(productIds));
        if (CollectionUtils.isEmpty(attributes)) {
            log.error("getRoomDeviceAttr --------- 设备属性信息获取失败");
            return null;
        }
        Map<Long, List<SceneDeviceAttributeVO>> attrMap = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));
        Map<String, List<FamilyRoomDeviceAttrBO>> roomDeviceMap = roomDeviceAttrBOS.stream().collect(Collectors.groupingBy(FamilyRoomDeviceAttrBO::getRoomName));
        List<JZSceneConfigDataVO.JZSceneConfigRoomDataVO> rooms = Lists.newArrayListWithExpectedSize(roomDeviceAttrBOS.size());
        JZSceneConfigDataVO.JZSceneConfigDeviceDataVO systemDevice = null;
        for (Map.Entry<String, List<FamilyRoomDeviceAttrBO>> entry : roomDeviceMap.entrySet()) {
            String roomName = entry.getKey();
            List<FamilyRoomDeviceAttrBO> devices = entry.getValue();
            if (CollectionUtils.isEmpty(devices)) {
                continue;
            }
            JZSceneConfigDataVO.JZSceneConfigRoomDataVO roomDataVO = new JZSceneConfigDataVO.JZSceneConfigRoomDataVO();
            FamilyRoomDeviceAttrBO room = devices.get(0);
            roomDataVO.setFloor(room.getFloor());
            roomDataVO.setName(room.getRoomName());
            roomDataVO.setType(room.getRoomType());
            //设备信息
            List<JZSceneConfigDataVO.JZSceneConfigDeviceDataVO> deviceList = Lists.newArrayListWithExpectedSize(devices.size());
            roomDataVO.setDevices(deviceList);

            for (FamilyRoomDeviceAttrBO device : devices) {
                JZSceneConfigDataVO.JZSceneConfigDeviceDataVO deviceDataVO = BeanUtil.mapperBean(device, JZSceneConfigDataVO.JZSceneConfigDeviceDataVO.class);
                List<SceneDeviceAttributeVO> attributeVOS = attrMap.get(device.getProductId());
                if (CollectionUtils.isEmpty(attributeVOS)) {
                    continue;
                }
                List<JZSceneConfigDataVO.JZSceneConfigDeviceAttrDataVO> deviceAttrDataVOs = BeanUtil.mapperList(attributeVOS, JZSceneConfigDataVO.JZSceneConfigDeviceAttrDataVO.class);
                deviceDataVO.setAttributes(deviceAttrDataVOs);
                if (CategoryTypeEnum.HVAC.getType().equals(device.getCategoryCode())) {
                    systemDevice = deviceDataVO;
                }else {
                    deviceList.add(deviceDataVO);
                }
            }
            rooms.add(roomDataVO);
        }
        result.setRooms(rooms);
        result.setSystemDevice(systemDevice);
        return result;
    }

    @Override
    public JZDeviceStatusCategoryVO getRoomDeviceAttrByCategoryCode(String familyCode,Long familyId, Long templateId, String categoryCode) {
        JZDeviceStatusCategoryVO result = new JZDeviceStatusCategoryVO();
        List<JZFamilyRoom> rooms = this.baseMapper.getListRoomByFamilyId(familyId);
        if(!CollectionUtils.isEmpty(rooms)){
            rooms = rooms.stream().filter(i -> !i.getType().equals(RoomTypeEnum.WHOLE.getType())).collect(Collectors.toList());
        }
        
        if(CollectionUtils.isEmpty(rooms)){
            return result;
        }
        //第一个房间的设备状态获取
        Long roomId = rooms.get(0).getRoomId();
        JZRoomDeviceStatusCategoryVO categoryVO = getDeviceStatusByRIdAndCategory(familyCode,familyId,templateId,roomId,categoryCode);
        if(!Objects.isNull(categoryVO)){
        	rooms.get(0).setDevices(categoryVO.getDevices());
        	result.setSystemDevice(categoryVO.getSystemDevice());
        }
        
        result.setRooms(rooms);
        result.setCategoryCode(categoryCode);
        return result;
    }

    @Override
    public JZRoomDeviceStatusCategoryVO getDeviceStatusByRIdAndCategory(String familyCode,Long familyId, Long templateId, Long roomId, String categoryCode) {

        JZRoomDeviceStatusCategoryVO result = new JZRoomDeviceStatusCategoryVO();
        List<FamilyRoomDeviceAttrBO> roomDeviceAttrBOS = this.baseMapper.getRoomCategoryDeviceAttr(familyId,templateId,roomId,categoryCode);
        if (CollectionUtils.isEmpty(roomDeviceAttrBOS)) {
            return null;
        }
        //设备信息
        List<DeviceInfo> deviceList = Lists.newArrayListWithExpectedSize(roomDeviceAttrBOS.size());
        List<Long> productIds = roomDeviceAttrBOS.stream().map(FamilyRoomDeviceAttrBO::getProductId).collect(Collectors.toList());
        //面板类型的要返回暖通设备
        DeviceInfo systemDevice = null;
        TemplateDeviceDO templateDeviceDO = null;
        if (CategoryTypeEnum.TEMPERATURE_PANEL.getType().equals(categoryCode)) {
            templateDeviceDO =  iHouseTemplateDeviceService.getHvacByTtemplateId(templateId);
            if (Objects.nonNull(templateDeviceDO)){
                productIds.add(templateDeviceDO.getProductId());
                systemDevice = new DeviceInfo();
                systemDevice.setDeviceId(templateDeviceDO.getId());
                systemDevice.setDeviceName(templateDeviceDO.getName());
            }
        }
        //获取产品属性
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceFunAttributeInfo(Lists.newArrayList(productIds));
        if (CollectionUtils.isEmpty(attributes)) {
            log.error("getRoomDeviceAttr --------- 设备属性信息获取失败");
            return null;
        }
        Map<Long, List<SceneDeviceAttributeVO>> attrMap = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));

        for (FamilyRoomDeviceAttrBO device : roomDeviceAttrBOS) {
            DeviceInfo deviceDataVO = BeanUtil.mapperBean(device,DeviceInfo.class);
            //属性列表
            List<SceneDeviceAttributeVO> attributeVOS = attrMap.get(device.getProductId());
            if (CollectionUtils.isEmpty(attributeVOS)) {
                continue;
            }
            //获取设备属性的当前状态
            List<JZDeviceAttrDataVO> attrs = buildAttrs(familyCode,device.getDeviceSn(),attributeVOS);
            deviceDataVO.setAttrs(attrs);
            deviceList.add(deviceDataVO);
        }
        //获取暖通设备的
        if (Objects.nonNull(systemDevice)){
            //属性列表
            List<SceneDeviceAttributeVO> attributeVOS = attrMap.get(templateDeviceDO.getProductId());
            if (!CollectionUtils.isEmpty(attributeVOS)) {
                //获取设备属性的当前状态
                List<JZDeviceAttrDataVO> attrs = buildAttrs(familyCode,templateDeviceDO.getSn(),attributeVOS);
                systemDevice.setAttrs(attrs);
            }
        }
        result.setDevices(deviceList);
        result.setSystemDevice(systemDevice);
        return result;
    }

    /**
     * 获取设备属性的当前状态
     * @param familyCode
     * @param deviceSn
     * @param attributeVOS
     * @return
     */
    private List<JZDeviceAttrDataVO> buildAttrs(String familyCode,String deviceSn, List<SceneDeviceAttributeVO> attributeVOS) {
        List<JZDeviceAttrDataVO> attrs = Lists.newArrayListWithExpectedSize(attributeVOS.size());
        attributeVOS.forEach(attr->{
            JZDeviceAttrDataVO attrDataVO = BeanUtil.mapperBean(attr,JZDeviceAttrDataVO.class);
            // 获取设备属性名以及状态值
            Object attributeValue = redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyCode, deviceSn, attr.getCode()));
            attrDataVO.setCurrentVal((String) attributeValue);
            attrs.add(attrDataVO);
        });
        return attrs;
    }

}
