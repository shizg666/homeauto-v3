package com.landleaf.homeauto.center.device.service.mybatis.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.bo.FamilySimpleRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.RoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家庭房间表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyRoomServiceImpl extends ServiceImpl<FamilyRoomMapper, FamilyRoomDO> implements IFamilyRoomService {

    @Autowired
    private FamilyRoomMapper familyRoomMapper;

    @Autowired
    private IFamilyFloorService familyFloorService;

    @Override
    public List<CountBO> getCountByFamilyIds(List<String> familyIds) {
        List<CountBO> countBOS = this.baseMapper.getCountByFamilyIds(familyIds);
        if (CollectionUtils.isEmpty(countBOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Override
    public List<FamilyRoomDO> getRoom(String familyId) {
        QueryWrapper<FamilyRoomDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        return list(queryWrapper);
    }

    @Override
    public String getPosition(String roomId) {
        FamilyRoomDO familyRoomDO = getById(roomId);
        FamilyFloorDO familyFloorDO = familyFloorService.getById(familyRoomDO.getFloorId());
        return String.format("%s-%s", familyFloorDO.getName(), familyRoomDO.getName());
    }

    @Override
    public List<FamilyRoomBO> getRoomListByFamilyId(String familyId) {
        return familyRoomMapper.getRoomListByFamilyId(familyId);
    }

    @Override
    public void updateRoomName(FamilyUpdateVO request) {
        FamilyRoomDO roomDO = BeanUtil.mapperBean(request,FamilyRoomDO.class);
        updateById(roomDO);
    }

    @Override
    public void add(FamilyRoomDTO request) {
        addCheck(request);
        FamilyRoomDO roomDO = BeanUtil.mapperBean(request,FamilyRoomDO.class);
        int count = count(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFloorId,request.getFloorId()));
        roomDO.setSortNo(count+1);
        save(roomDO);
    }

    private void addCheck(FamilyRoomDTO request) {
    }

    @Override
    public void update(FamilyRoomDTO request) {

    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {

    }

    @Override
    public void moveUp(String roomId) {

    }

    @Override
    public void moveDown(String roomId) {

    }

    @Override
    public void moveTop(String roomId) {

    }

    @Override
    public void moveEnd(String roomId) {

    }

}
