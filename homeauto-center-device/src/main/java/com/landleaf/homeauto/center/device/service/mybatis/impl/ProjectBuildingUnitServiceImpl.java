package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectBuildingUnit;
import com.landleaf.homeauto.center.device.model.mapper.ProjectBuildingUnitMapper;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectBuildingUnitService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingUnitDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingUnitVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 楼栋单元表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectBuildingUnitServiceImpl extends ServiceImpl<ProjectBuildingUnitMapper, ProjectBuildingUnit> implements IProjectBuildingUnitService {

    @Override
    public void add(ProjectBuildingUnitDTO request) {
        addCheck(request);
        ProjectBuildingUnit unit = BeanUtil.mapperBean(request, ProjectBuildingUnit.class);
        unit.setName(unit.getCode().concat("单元"));
        save(unit);
    }

    private void addCheck(ProjectBuildingUnitDTO request) {
        int count = count(new LambdaQueryWrapper<ProjectBuildingUnit>().eq(ProjectBuildingUnit::getCode, request.getCode()).eq(ProjectBuildingUnit::getBuildingId, request.getBuildingId()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "单元号号已存在");
        }
    }

    private void updateCheck(ProjectBuildingUnitDTO request) {
        ProjectBuildingUnit unit = getById(request.getId());
        if (request.getCode().equals(unit.getCode())) {
            return;
        }
        addCheck(request);
    }

    @Override
    public void update(ProjectBuildingUnitDTO request) {
        updateCheck(request);
        ProjectBuildingUnit unit = BeanUtil.mapperBean(request, ProjectBuildingUnit.class);
        updateById(unit);
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        //todo 判断家庭
        removeById(request.getId());
    }

    @Override
    public List<ProjectBuildingUnitVO> getListByProjectId(String id) {
        return this.baseMapper.getListByProjectId(id);
    }

    @Override
    public String getUnitNoById(String unitId) {
        return this.baseMapper.getUnitNoById(unitId);
    }

    @Override
    public PathBO getUnitPathInfoById(String unitId) {
        return this.baseMapper.getUnitPathInfoById(unitId);
    }
}
