package com.landleaf.homeauto.center.device.service.mybatis.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.bo.FamilySimpleRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.RoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;

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
    public List<FamilyRoomDO> getHvacSceneRoomList(String sceneId) {
        return null;
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
        int count = count(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getName,request.getName()).eq(FamilyRoomDO::getFloorId,request.getFloorId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "房间名称已存在");
        }
    }

    @Override
    public void update(FamilyRoomDTO request) {
        updateCheck(request);
        FamilyRoomDO roomDO = BeanUtil.mapperBean(request,FamilyRoomDO.class);
        updateById(roomDO);
    }

    private void updateCheck(FamilyRoomDTO request) {
        FamilyRoomDO roomDO = getById(request.getId());
        if (roomDO.getName().equals(roomDO.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iFamilyDeviceService.count(new LambdaQueryWrapper<FamilyDeviceDO>().eq(FamilyDeviceDO::getRoomId,request.getId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "房间下有设备已存在");
        }
        FamilyRoomDO roomDO = getById(request.getId());
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(roomDO.getFloorId(),roomDO.getSortNo());
        if (!CollectionUtils.isEmpty(sortNoBOS)){
            sortNoBOS.forEach(obj->{
                obj.setSortNo(obj.getSortNo()-1);
            });
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
        removeById(request.getId());
    }

    @Override
    public void moveUp(String roomId) {
        FamilyRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        if (sortNo == 1){
            return;
        }
        String updateId = this.getBaseMapper().getIdBySort(sortNo-1,roomDO.getFloorId());
        if (StringUtil.isBlank(updateId)){
            return;
        }
        List<SortNoBO> sortNoBOS = Lists.newArrayListWithCapacity(2);
        sortNoBOS.add(SortNoBO.builder().id(roomId).sortNo(sortNo-1).build());
        sortNoBOS.add(SortNoBO.builder().id(updateId).sortNo(sortNo).build());
        this.baseMapper.updateBatchSort(sortNoBOS);
    }

    @Override
    public void moveDown(String roomId) {
        FamilyRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        String updateId = this.getBaseMapper().getIdBySort(sortNo+1,roomDO.getFloorId());
        if (StringUtil.isBlank(updateId)){
            return;
        }
        List<SortNoBO> sortNoBOS = Lists.newArrayListWithCapacity(2);
        sortNoBOS.add(SortNoBO.builder().id(roomId).sortNo(sortNo+1).build());
        sortNoBOS.add(SortNoBO.builder().id(updateId).sortNo(sortNo).build());
        this.baseMapper.updateBatchSort(sortNoBOS);
    }

    @Override
    public void moveTop(String roomId) {
        FamilyRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        if (sortNo == 1){
            return;
        }
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoLT(roomDO.getFloorId(),sortNo);
        if (!CollectionUtils.isEmpty(sortNoBOS)){
            sortNoBOS.forEach(obj->{
                obj.setSortNo(obj.getSortNo()+1);
            });
            SortNoBO sortNoBO = SortNoBO.builder().id(roomDO.getId()).sortNo(1).build();
            sortNoBOS.add(sortNoBO);
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
    }

    @Override
    public void moveEnd(String roomId) {
        FamilyRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        int count = count(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFloorId,roomDO.getFloorId()));
        if (count == sortNo){
            return;
        }
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(roomDO.getFloorId(),sortNo);
        if (!CollectionUtils.isEmpty(sortNoBOS)){
            sortNoBOS.forEach(obj->{
                obj.setSortNo(obj.getSortNo()-1);
            });
            SortNoBO sortNoBO = SortNoBO.builder().id(roomDO.getId()).sortNo(count).build();
            sortNoBOS.add(sortNoBO);
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
    }

}
