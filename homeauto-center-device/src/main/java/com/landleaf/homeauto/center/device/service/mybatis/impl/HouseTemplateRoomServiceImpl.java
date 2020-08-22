package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateRoomService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    }

    private void updateCheck(TemplateRoomDTO request) {
        TemplateRoomDO roomDO = getById(request.getId());
        if (roomDO.getName().equals(roomDO.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iHouseTemplateDeviceService.count(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getRoomId,request.getId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "房间下已有设备已存在");
        }
    }


}
