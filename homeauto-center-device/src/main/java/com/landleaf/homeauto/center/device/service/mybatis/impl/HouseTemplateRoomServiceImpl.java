package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateRoomService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 户型房间表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Service
public class HouseTemplateRoomServiceImpl extends ServiceImpl<TemplateRoomMapper, TemplateRoomDO> implements IHouseTemplateRoomService {

    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Override
    public void add(TemplateRoomDTO request) {
        addCheck(request);
        TemplateRoomDO roomDO = BeanUtil.mapperBean(request,TemplateRoomDO.class);
        int count = count(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getFloorId,request.getFloorId()));
        roomDO.setSortNo(count+1);
        RoomTypeEnum roomTypeEnum = RoomTypeEnum.getInstByType(request.getType());
        if (roomTypeEnum != null){
            roomDO.setIcon(roomTypeEnum.getIcon());
            roomDO.setImgIcon(roomTypeEnum.getImgIcon());
        }
        save(roomDO);
    }

    private void addCheck(TemplateRoomDTO request) {
        int count = count(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getName,request.getName()).eq(TemplateRoomDO::getFloorId,request.getFloorId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "房间名称已存在");
        }
    }

    @Override
    public void update(TemplateRoomDTO request) {
        updateCheck(request);
        TemplateRoomDO roomDO = BeanUtil.mapperBean(request,TemplateRoomDO.class);
        RoomTypeEnum roomTypeEnum = RoomTypeEnum.getInstByType(request.getType());
        if (roomTypeEnum != null){
            roomDO.setIcon(roomTypeEnum.getIcon());
            roomDO.setImgIcon(roomTypeEnum.getImgIcon());
        }
        updateById(roomDO);
    }

    private void updateCheck(TemplateRoomDTO request) {
        TemplateRoomDO roomDO = getById(request.getId());
        if (roomDO.getName().equals(request.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iHouseTemplateDeviceService.count(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getRoomId,request.getId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "房间下已有设备已存在");
        }
        TemplateRoomDO roomDO = getById(request.getId());
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
    public List<SelectedIntegerVO> getRoomTypeSelects() {
        List<SelectedIntegerVO> selectedVOS = Lists.newArrayList();
        for (RoomTypeEnum value : RoomTypeEnum.values()) {
            SelectedIntegerVO cascadeVo = new SelectedIntegerVO(value.getName(), value.getType());
            selectedVOS.add(cascadeVo);
        }
        return selectedVOS;
    }

    @Override
    public void moveUp(String roomId) {
        TemplateRoomDO roomDO = getById(roomId);
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
        TemplateRoomDO roomDO = getById(roomId);
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
        TemplateRoomDO roomDO = getById(roomId);
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
        TemplateRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        int count = count(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getFloorId,roomDO.getFloorId()));
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

    @Override
    public List<String> getListNameByTemplateId(String templateId) {
        return this.baseMapper.getListNameByTemplateId(templateId);
    }


}
