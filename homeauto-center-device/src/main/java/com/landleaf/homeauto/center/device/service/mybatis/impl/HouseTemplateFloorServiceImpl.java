package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateFloorMapper;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateFloorDetailVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateRoomService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 户型楼层表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Service
public class HouseTemplateFloorServiceImpl extends ServiceImpl<TemplateFloorMapper, TemplateFloorDO> implements IHouseTemplateFloorService {

    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Override
    public void add(TemplateFloorDTO request) {
        addCheck(request);
        TemplateFloorDO floorDO = BeanUtil.mapperBean(request,TemplateFloorDO.class);
        floorDO.setName(floorDO.getFloor().concat("F"));
        save(floorDO);
    }

    private void addCheck(TemplateFloorDTO request) {
        int count = count(new LambdaQueryWrapper<TemplateFloorDO>().eq(TemplateFloorDO::getFloor,request.getFloor()).eq(TemplateFloorDO::getHouseTemplateId,request.getHouseTemplateId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "楼层已存在");
        }
    }

    @Override
    public void update(TemplateFloorDTO request) {
        updateCheck(request);
        TemplateFloorDO floorDO = BeanUtil.mapperBean(request,TemplateFloorDO.class);
        floorDO.setName(floorDO.getFloor().concat("F"));
        updateById(floorDO);
    }

    private void updateCheck(TemplateFloorDTO request) {
        TemplateFloorDO floorDO = getById(request.getId());
        if (floorDO.getFloor().equals(request.getFloor())){
            return;
        }
        addCheck(request);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iHouseTemplateRoomService.count(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getFloorId,request.getId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "楼层下已有房间已存在");
        }
        removeById(request.getId());
    }

    @Override
    public List<TemplateFloorDetailVO> getListFloorDetail(String templateId) {
        List<TemplateFloorDetailVO> floorDetailVOS = this.baseMapper.getListFloorDetail(templateId);
        if (CollectionUtils.isEmpty(floorDetailVOS)){
            return floorDetailVOS;
        }
        List<String> roomIds = Lists.newArrayList();
        floorDetailVOS.forEach(floor->{
            if (!CollectionUtils.isEmpty(floor.getRooms())){
                floor.getRooms().forEach(room->{
                    roomIds.add(room.getId());
                });
            }
        });
        if (CollectionUtils.isEmpty(roomIds)){
            return floorDetailVOS;
        }
//        List<CountBO> countBOS = iHouseTemplateDeviceService.countDeviceByRoomIds(roomIds);
//        Map<String,Integer> countMap = countBOS.stream().collect(Collectors.toMap(CountBO::getId,CountBO::getCount));
//        floorDetailVOS.forEach(floor->{
//            if (!CollectionUtils.isEmpty(floor.getRooms())){
//                floor.getRooms().forEach(room->{
//                    if(countMap.containsKey(room.getId())){
//                        room.setCount(countMap.get(room.getId()));
//                    }
//                });
//            }
//        });
        return floorDetailVOS;
    }

    @Override
    public List<String> getListNameByTemplateId(String templateId) {
        return this.baseMapper.getListNameByTemplateId(templateId);
    }

    /**
     * 根据户型获取楼层信息
     * @param templateId  户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO>
     * @author wenyilu
     * @date  2021/1/5 17:17
     */
    @Override
    public List<TemplateFloorDO> getFloorByTemplateId(String templateId) {
        QueryWrapper<TemplateFloorDO> queryWrapper = new QueryWrapper<TemplateFloorDO>();
        queryWrapper.eq("house_template_id", templateId);
        return list(queryWrapper);
    }
    /**
     * 根据户型获取楼层房间设备信息
     * @param templateId   户型ID
     * @param deviceShowApp  设备是否在app展示（1：是，0：否）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.FloorRoomVO>
     * @author wenyilu
     * @date  2021/1/6 10:11
     */
    @Override
    public List<FloorRoomVO> getFloorAndRoomDevices(String templateId,Integer deviceShowApp) {
        return this.baseMapper.getFloorAndRoomDevices(templateId,deviceShowApp);
    }

    @Override
    public List<TemplateFloorDO> getListFloorDOByFamilyId(String familyId) {
        String templateId = iHomeAutoFamilyService.getTemplateIdById(familyId);
        return list(new LambdaQueryWrapper<TemplateFloorDO>().eq(TemplateFloorDO::getHouseTemplateId,templateId));
    }
}
