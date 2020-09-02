package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyFloorMapper;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyFloorDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        floorDO.setName(floorDO.getFloor().concat("楼"));
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
        floorDO.setName(floorDO.getFloor().concat("楼"));
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
}
