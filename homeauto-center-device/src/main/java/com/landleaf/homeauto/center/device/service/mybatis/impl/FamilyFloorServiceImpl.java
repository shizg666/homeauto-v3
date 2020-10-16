package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyFloorMapper;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyFloorConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyFloorDTO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateFloorDetailVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
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
 * 家庭楼层表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyFloorServiceImpl extends ServiceImpl<FamilyFloorMapper, FamilyFloorDO> implements IFamilyFloorService {

    @Autowired
    private IFamilyRoomService iFamilyRoomService;
    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;


    @Override
    public List<FamilyFloorDO> getFloorByFamilyId(String familyId) {
        QueryWrapper<FamilyFloorDO> queryWrapper = new QueryWrapper<FamilyFloorDO>();
        queryWrapper.eq("family_id", familyId);
        return list(queryWrapper);
    }

    @Override
    public void add(FamilyFloorDTO request) {
        addCheck(request);
        FamilyFloorDO floorDO = BeanUtil.mapperBean(request,FamilyFloorDO.class);
        floorDO.setName(floorDO.getFloor().concat("F"));
        save(floorDO);
    }

    private void addCheck(FamilyFloorDTO request) {
        int count = count(new LambdaQueryWrapper<FamilyFloorDO>().eq(FamilyFloorDO::getFloor,request.getFloor()).eq(FamilyFloorDO::getFamilyId,request.getFamilyId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "楼层已存在");
        }
    }

    @Override
    public void update(FamilyFloorDTO request) {
        updateCheck(request);
        FamilyFloorDO floorDO = BeanUtil.mapperBean(request,FamilyFloorDO.class);
        floorDO.setName(floorDO.getFloor().concat("F"));
        updateById(floorDO);
    }

    private void updateCheck(FamilyFloorDTO request) {
        FamilyFloorDO floorDO = getById(request.getId());
        if (request.getFloor().equals(floorDO.getFloor())){
            return;
        }
        addCheck(request);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iFamilyRoomService.count(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFloorId,request.getId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "楼层尚有房间已存在");
        }
        removeById(request.getId());
    }

    @Override
    public List<FamilyFloorConfigVO> getListFloorDetail(String familyId) {
        List<FamilyFloorConfigVO> floorDetailVOS = this.baseMapper.getListFloorDetail(familyId);
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
        List<CountBO> countBOS = iFamilyDeviceService.countDeviceByRoomIds(roomIds);
        Map<String,Integer> countMap = countBOS.stream().collect(Collectors.toMap(CountBO::getId,CountBO::getCount));
        floorDetailVOS.forEach(floor->{
            if (!CollectionUtils.isEmpty(floor.getRooms())){
                floor.getRooms().forEach(room->{
                    if(countMap.containsKey(room.getId())){
                        room.setCount(countMap.get(room.getId()));
                    }
                });
            }
        });
        return floorDetailVOS;
    }

    @Override
    public List<String> getListNameByFamilyId(String familyId) {
        return this.baseMapper.getListNameByFamilyId(familyId);
    }
}
