package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.ProjectHouseTemplateMapper;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateRoomService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目户型表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectHouseTemplateServiceImpl extends ServiceImpl<ProjectHouseTemplateMapper, ProjectHouseTemplate> implements IProjectHouseTemplateService {

    @Autowired
    private IHouseTemplateFloorService iHouseTemplateFloorService;
    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;

    @Override
    public void add(ProjectHouseTemplateDTO request) {
        addCheck(request);
        ProjectHouseTemplate template = BeanUtil.mapperBean(request,ProjectHouseTemplate.class);
        save(template);
    }

    private void addCheck(ProjectHouseTemplateDTO request) {
        int count = count(new LambdaQueryWrapper<ProjectHouseTemplate>().eq(ProjectHouseTemplate::getName,request.getName()).eq(ProjectHouseTemplate::getProjectId,request.getProjectId()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户型已存在");
        }
    }

    @Override
    public void update(ProjectHouseTemplateDTO request) {
        updateCheck(request);
        ProjectHouseTemplate template = BeanUtil.mapperBean(request,ProjectHouseTemplate.class);
        updateById(template);
    }

    private void updateCheck(ProjectHouseTemplateDTO request) {
        ProjectHouseTemplate template = getById(request.getId());
        if (template.getName().equals(template.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        //todo
        removeById(request.getId());
    }

    @Override
    public List<HouseTemplatePageVO> getListByProjectId(String id) {
        List<HouseTemplatePageVO> data = this.baseMapper.getListByProjectId(id);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public HouseTemplateDetailVO getDeatil(String id) {
        List<TemplateFloorDetailVO> floors = iHouseTemplateFloorService.getListFloorDetail(id);
        //todo 获取场景信息
        HouseTemplateDetailVO detailVO = new HouseTemplateDetailVO();
        detailVO.setFloors(floors);
        return detailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(ProjectHouseTemplateDTO request) {
        ProjectHouseTemplate template = getById(request.getId());

        if (template == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户型id不存在");
        }
        //生成新的户型
        template.setId(IdGeneratorUtil.getUUID32());
        addCheck(request);
        template.setName(request.getName());

        List<TemplateFloorDO> floorDOS = iHouseTemplateFloorService.list(new LambdaQueryWrapper<TemplateFloorDO>().eq(TemplateFloorDO::getHouseTemplateId,request.getId()).select(TemplateFloorDO::getFloor,TemplateFloorDO::getName,TemplateFloorDO::getId));
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId,request.getId()).select(TemplateRoomDO::getName,TemplateRoomDO::getFloorId,TemplateRoomDO::getHouseTemplateId,TemplateRoomDO::getType,TemplateRoomDO::getSortNo,TemplateRoomDO::getIcon,TemplateRoomDO::getId));
        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,request.getId()));

        Map<String, String> floorMap = copyFloor(floorDOS,template.getId());
        Map<String, String> roomMap = copyRoom(roomDOS,floorMap,template.getId());
        Map<String, String> deviceMap = copyDevice(deviceDOS,roomMap,template.getId());
        //todo 保存场景
        save(template);
    }

    private Map<String, String> copyDevice(List<TemplateDeviceDO> deviceDOS, Map<String, String> roomMap, String houseTemplateId) {
        Map<String, String> deviceMap = Maps.newHashMapWithExpectedSize(deviceDOS.size());
        List<TemplateDeviceDO> data = Lists.newArrayListWithCapacity(deviceDOS.size());
        deviceDOS.forEach(device->{
            String deviceId = IdGeneratorUtil.getUUID32();
            deviceMap.put(device.getId(),deviceId);
            device.setId(deviceId);
            device.setHouseTemplateId(houseTemplateId);
            device.setRoomId(roomMap.get(device.getRoomId()));
            data.add(device);
        });
        iHouseTemplateDeviceService.saveBatch(data);
        return deviceMap;
    }

    private Map<String, String> copyRoom(List<TemplateRoomDO> roomDOS, Map<String, String> floorMap, String houseTemplateId) {
        Map<String, String> roomMap = Maps.newHashMapWithExpectedSize(roomDOS.size());
        List<TemplateRoomDO> data = Lists.newArrayListWithCapacity(roomDOS.size());
        roomDOS.forEach(room->{
            String roomId = IdGeneratorUtil.getUUID32();
            roomMap.put(room.getId(),roomId);
            room.setId(roomId);
            room.setHouseTemplateId(houseTemplateId);
            room.setFloorId(floorMap.get(room.getFloorId()));
            data.add(room);
        });
        iHouseTemplateRoomService.saveBatch(data);
        return roomMap;
    }

    private Map<String, String> copyFloor(List<TemplateFloorDO> floorDOS, String houseTemplateId) {
        Map<String, String> floorMap = Maps.newHashMapWithExpectedSize(floorDOS.size());
        List<TemplateFloorDO> data = Lists.newArrayListWithCapacity(floorDOS.size());
        floorDOS.forEach(floor->{
            String floorId = IdGeneratorUtil.getUUID32();
            floorMap.put(floor.getId(),floorId);
            floor.setId(floorId);
            floor.setHouseTemplateId(houseTemplateId);
            data.add(floor);
        });
        iHouseTemplateFloorService.saveBatch(data);
        return floorMap;
    }

}
