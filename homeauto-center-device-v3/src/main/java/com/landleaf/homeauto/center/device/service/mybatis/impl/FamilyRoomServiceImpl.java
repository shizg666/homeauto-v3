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
import com.landleaf.homeauto.center.device.model.dto.jhappletes.FamilyRoomDeviceAttrBO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZSceneConfigDataVO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateRoomPageVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
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

    @Override
    public List<FamilyRoomDO> getListRooms(Long familyId) {
         List<FamilyRoomDO> rooms = list(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFamilyId,familyId).select(FamilyRoomDO::getId,FamilyRoomDO::getName));
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
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().in(TemplateRoomDO::getHouseTemplateId,templateIds).select(TemplateRoomDO::getId,TemplateRoomDO::getName,TemplateRoomDO::getFloor));
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
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceAttributeInfo(Lists.newArrayList(productIds));
        if (CollectionUtils.isEmpty(attributes)) {
            log.error("getRoomDeviceAttr --------- 设备属性信息获取失败");
            return null;
        }
        Map<Long, List<SceneDeviceAttributeVO>> attrMap = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));
        Map<String, List<FamilyRoomDeviceAttrBO>> roomDeviceMap = roomDeviceAttrBOS.stream().collect(Collectors.groupingBy(FamilyRoomDeviceAttrBO::getRoomName));
        List<JZSceneConfigDataVO.JZSceneConfigRoomDataVO> rooms = Lists.newArrayListWithExpectedSize(roomDeviceAttrBOS.size());
        JZSceneConfigDataVO.JZSceneConfigDeviceDataVO systemDevice;
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
        return null;
    }
}
