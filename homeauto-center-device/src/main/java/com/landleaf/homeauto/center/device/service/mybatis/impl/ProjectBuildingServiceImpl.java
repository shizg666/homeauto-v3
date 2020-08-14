package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProjectBuildingMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectBuildingService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectBuildingUnitService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectBuilding;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectBuildingUnit;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 楼栋表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectBuildingServiceImpl extends ServiceImpl<ProjectBuildingMapper, ProjectBuilding> implements IProjectBuildingService {

    @Autowired
    private IProjectBuildingUnitService iProjectBuildingUnitService;

    @Override
    public void addConfig(ProjectBuildingDTO request) {
        addcCheck(request);
        ProjectBuilding building = BeanUtil.mapperBean(request,ProjectBuilding.class);
        save(building);
    }

    private void addcCheck(ProjectBuildingDTO request) {
        int count = count(new LambdaQueryWrapper<ProjectBuilding>().eq(ProjectBuilding::getCode,request.getCode()).eq(ProjectBuilding::getProjectId,request.getProjectId()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "楼栋号已存在");
        }
    }

    @Override
    public void updateConfig(ProjectBuildingDTO request) {
        updateCheck(request);
        ProjectBuilding building = BeanUtil.mapperBean(request,ProjectBuilding.class);
        updateById(building);
    }

    private void updateCheck(ProjectBuildingDTO request) {
        ProjectBuilding building = getById(request.getId());
        if (request.getCode().equals(building.getCode())){
            return;
        }
        addcCheck(request);
    }

    @Override
    public List<ProjectBuildingVO> getListByProjectId(String id) {
        return this.getBaseMapper().getListByProjectId(id);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        int count = iProjectBuildingUnitService.count(new LambdaQueryWrapper<ProjectBuildingUnit>().eq(ProjectBuildingUnit::getBuildingId,request.getId()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "楼盘下有单元不可删除");
        }
        removeById(request.getId());
    }


}
