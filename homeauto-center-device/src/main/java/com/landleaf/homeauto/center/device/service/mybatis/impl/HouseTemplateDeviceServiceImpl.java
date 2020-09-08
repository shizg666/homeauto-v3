package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacPanelAction;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 户型设备表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Service
public class HouseTemplateDeviceServiceImpl extends ServiceImpl<TemplateDeviceMapper, TemplateDeviceDO> implements IHouseTemplateDeviceService {

    @Override
    public void add(TemplateDeviceDTO request) {
        addCheck(request);
        TemplateDeviceDO deviceDO = BeanUtil.mapperBean(request,TemplateDeviceDO.class);
        int count = count(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getRoomId,request.getRoomId()));
        deviceDO.setSortNo(count+1);
        save(deviceDO);
    }

    private void addCheck(TemplateDeviceDTO request) {
       int count = this.baseMapper.existParam(request.getName(),null,request.getRoomId());
       if (count >0){
           throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
       }
        int countSn = this.baseMapper.existParam(null,request.getSn(),request.getRoomId());
        if (countSn >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备号已存在");
        }
    }

    @Override
    public void update(TemplateDeviceUpDTO request) {
        updateCheck(request);
        TemplateDeviceDO deviceDO = BeanUtil.mapperBean(request,TemplateDeviceDO.class);
        updateById(deviceDO);
    }

    private void updateCheck(TemplateDeviceUpDTO request) {
        TemplateDeviceDO deviceDO = getById(request.getId());
        if (request.getName().equals(deviceDO.getName())){
            return;
        }
        int count = this.baseMapper.existParam(request.getName(),null,request.getRoomId());
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        //todo 删除场景逻辑
        TemplateDeviceDO roomDO = getById(request.getId());
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(roomDO.getRoomId(),roomDO.getSortNo());
        if (!CollectionUtils.isEmpty(sortNoBOS)){
            sortNoBOS.forEach(obj->{
                obj.setSortNo(obj.getSortNo()-1);
            });
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
        removeById(request.getId());
    }

    @Override
    public List<CountBO> countDeviceByRoomIds(List<String> roomIds) {
        if (CollectionUtils.isEmpty(roomIds)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<CountBO> data = this.baseMapper.countDeviceByRoomIds(roomIds);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public List<TemplateDevicePageVO> getListByRoomId(String roomId) {
        return this.baseMapper.getListByRoomId(roomId);
    }

    @Override
    public void moveUp(String deviceId) {
        TemplateDeviceDO deviceDO = getById(deviceId);
        int sortNo = deviceDO.getSortNo();
        if (sortNo == 1){
            return;
        }
        String updateId = this.getBaseMapper().getIdBySort(sortNo-1,deviceDO.getRoomId());
        if (StringUtil.isBlank(updateId)){
            return;
        }
        List<SortNoBO> sortNoBOS = Lists.newArrayListWithCapacity(2);
        sortNoBOS.add(SortNoBO.builder().id(deviceId).sortNo(sortNo-1).build());
        sortNoBOS.add(SortNoBO.builder().id(updateId).sortNo(sortNo).build());
        this.baseMapper.updateBatchSort(sortNoBOS);
    }

    @Override
    public void moveDown(String deviceId) {
        TemplateDeviceDO deviceDO = getById(deviceId);
        int sortNo = deviceDO.getSortNo();
        String updateId = this.getBaseMapper().getIdBySort(sortNo+1,deviceDO.getRoomId());
        if (StringUtil.isBlank(updateId)){
            return;
        }
        List<SortNoBO> sortNoBOS = Lists.newArrayListWithCapacity(2);
        sortNoBOS.add(SortNoBO.builder().id(deviceId).sortNo(sortNo+1).build());
        sortNoBOS.add(SortNoBO.builder().id(updateId).sortNo(sortNo).build());
        this.baseMapper.updateBatchSort(sortNoBOS);
    }

    @Override
    public void moveTop(String deviceId) {
        TemplateDeviceDO deviceDO = getById(deviceId);
        int sortNo = deviceDO.getSortNo();
        if (sortNo == 1){
            return;
        }
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoLT(deviceDO.getRoomId(),sortNo);
        if (!CollectionUtils.isEmpty(sortNoBOS)){
            sortNoBOS.forEach(obj->{
                obj.setSortNo(obj.getSortNo()+1);
            });
            SortNoBO sortNoBO = SortNoBO.builder().id(deviceDO.getId()).sortNo(1).build();
            sortNoBOS.add(sortNoBO);
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
    }

    @Override
    public void moveEnd(String deviceId) {
        TemplateDeviceDO deviceDO = getById(deviceId);
        int sortNo = deviceDO.getSortNo();
        int count = count(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getRoomId,deviceDO.getRoomId()));
        if (count == sortNo){
            return;
        }
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(deviceDO.getRoomId(),sortNo);
        if (!CollectionUtils.isEmpty(sortNoBOS)){
            sortNoBOS.forEach(obj->{
                obj.setSortNo(obj.getSortNo()-1);
            });
            SortNoBO sortNoBO = SortNoBO.builder().id(deviceDO.getId()).sortNo(count).build();
            sortNoBOS.add(sortNoBO);
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
    }

    @Override
    public List<HvacPanelAction> getListPanel(String templateId) {
        return this.baseMapper.getListPanel(templateId);
    }
}
